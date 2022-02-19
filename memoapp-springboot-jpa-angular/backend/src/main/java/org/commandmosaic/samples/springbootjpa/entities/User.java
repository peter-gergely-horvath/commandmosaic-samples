package org.commandmosaic.samples.springbootjpa.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String loginId;

    @Column
    private String encodedPassword;

    @Column
    private String firstName;

    @Column
    private String lastName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name="USER_ID"),
            inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
    private Set<Role> roles;


    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(loginId, user.loginId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId);
    }

    @Override
    public String toString() {
        return "User{" +
                "loginId='" + loginId + '\'' +
                ", passwordHash='" + encodedPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
