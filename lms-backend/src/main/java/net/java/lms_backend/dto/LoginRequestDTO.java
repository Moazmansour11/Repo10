package net.java.lms_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    private String identifier; // email or username
    private String password;
//add const.

    public LoginRequestDTO(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
    public LoginRequestDTO(){

    }
    public String getPassword() {
        return password;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdentifier() {
        return identifier;
    }

    public void setUsername(String username) {
        this.identifier = username;
    }
    public void setEmail(String email) {
        this.identifier = email;
    }



    public void setPassword(char[] password) {
        this.password = new String(password);
    }
    public String getEmail() {
        return identifier;
    }

    public String getUsername() {
        return identifier;
    }
}
