package org.commandmosaic.samples.springbootjpa.dto;

public class UserChangeDTO extends UserDTO {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
