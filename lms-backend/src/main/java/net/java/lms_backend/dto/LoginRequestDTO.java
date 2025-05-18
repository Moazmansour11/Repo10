package net.java.lms_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    private String identifier; // email or username
    private String password;
//add const.

    public String getUsername() {
        return identifier;
    } 
}
