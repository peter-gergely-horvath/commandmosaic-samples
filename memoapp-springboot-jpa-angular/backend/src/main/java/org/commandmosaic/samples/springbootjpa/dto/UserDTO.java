package org.commandmosaic.samples.springbootjpa.dto;

import org.commandmosaic.samples.springbootjpa.entities.Role;
import org.commandmosaic.samples.springbootjpa.entities.User;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private String loginId;
    private Set<String> roles;


    private String firstName;
    private String lastName;


    public UserDTO() {
        // empty constructor
    }

    public UserDTO(User user) {
        this(user.getLoginId(),
                Optional.ofNullable(user.getRoles()).orElseGet(Collections::emptySet)
                        .stream().map(Role::getName).collect(Collectors.toSet()));

        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public UserDTO(String loginId, Set<String> roles) {
        this.loginId = loginId;
        this.roles = roles;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
