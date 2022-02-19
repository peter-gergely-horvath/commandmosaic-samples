package org.commandmosaic.samples.springbootjpa.commands.user;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.UserChangeDTO;
import org.commandmosaic.samples.springbootjpa.dto.UserDTO;
import org.commandmosaic.samples.springbootjpa.entities.Role;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@Access.IsPublic
public class Register implements Command<UserDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Register(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Parameter
    private UserChangeDTO user;

    @Override
    public UserDTO execute(CommandContext context) {

        userRepository.findById(user.getLoginId())
                .ifPresent(u -> {
                    throw new RuntimeException("User exists already: " + user.getLoginId());
                });

        String encodedPassword;

        String password = user.getPassword();
        if (password == null || "".equals(password)) {
            throw new IllegalArgumentException("Password must be specified");
        }
        encodedPassword = passwordEncoder.encode(password);

        Set<Role> roles = userRepository.findRolesByRoleNameIn(Collections.singleton(ApplicationRole.USER));


        User userEntity = new User();

        userEntity.setLoginId(user.getLoginId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEncodedPassword(encodedPassword);
        userEntity.setRoles(roles);

        userEntity = userRepository.save(userEntity);

        return new UserDTO(userEntity);
    }
}
