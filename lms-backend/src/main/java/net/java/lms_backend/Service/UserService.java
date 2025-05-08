package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.UserRepository;
import net.java.lms_backend.entity.ConfirmationToken;
import net.java.lms_backend.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

//remove Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public Optional<User> getUser(long id)
    {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(identifier);

        if (user.isEmpty()) {
            user = userRepository.findByEmail(identifier);
        }

        User foundUser = user.orElseThrow(() ->
                new UsernameNotFoundException("User Not Found"));

        //Block login if user is not enabled
        if (!foundUser.isEnabled()) {
            throw new UsernameNotFoundException("Email not confirmed");
        }

        return foundUser;
    }


    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public ResponseEntity<String> signUpUser(User user)
    {
        if(userRepository.findByUsername(user.getUsername()).isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Error: Username is already taken");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Email is already in use");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//Encode the Password
        user.setEnabled(false); //to prevent login before email confirmation
        userRepository.save(user);
        //Email Confirmation
        String token=UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =new ConfirmationToken(
                user,
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5)
        );
        confirmationTokenService.SaveConfirmationToken(confirmationToken);

        return ResponseEntity.ok(token);
    }


}
