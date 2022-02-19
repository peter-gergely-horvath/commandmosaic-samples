package org.commandmosaic.samples.springbootjpa.services;

import org.commandmosaic.samples.springbootjpa.entities.Role;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.security.core.Identity;
import org.commandmosaic.security.core.SimpleIdentity;
import org.commandmosaic.security.login.authentication.AbstractUsernamePasswordAuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractUsernamePasswordAuthenticationService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected Optional<User> loadUserByUsername(String userName) {
        return userRepository.findById(userName);
    }

    @Override
    protected String encodePassword(String clearTextPassword) {
        return passwordEncoder.encode(clearTextPassword);
    }

    @Override
    protected String getEncodedPassword(User user) {
        return user.getEncodedPassword();
    }

    @Override
    protected boolean checkPasswordMatches(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }

    @Override
    protected Identity mapToIdentity(User user) {
        Set<Role> roles = user.getRoles();
        Set<String> roleNames;
        if (roles != null) {
            roleNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
        } else {
            roleNames = null;
        }
        return new SimpleIdentity(user.getLoginId(), roleNames);
    }
}
