package org.commandmosaic.samples.springbootjpa.dto;

import java.util.Set;

public class LoginDTO extends UserDTO {

    private String token;

    public LoginDTO() {
        // empty constructor
    }

    public LoginDTO(String token, String loginId, Set<String> roles) {
        super(loginId, roles);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
