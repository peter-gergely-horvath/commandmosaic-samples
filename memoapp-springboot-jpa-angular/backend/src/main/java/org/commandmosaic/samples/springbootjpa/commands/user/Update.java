package org.commandmosaic.samples.springbootjpa.commands.user;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.UserDTO;
import org.commandmosaic.samples.springbootjpa.dto.UserChangeDTO;
import org.commandmosaic.samples.springbootjpa.entities.Role;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Access.RequiresAuthority(ApplicationRole.ADMIN)
public class Update implements Command<UserDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Update(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Parameter
    private UserChangeDTO user;

    @Transactional // required in order to get updates automatically flushed to the database
    @Override
    public UserDTO execute(CommandContext context) {

        String loginId = user.getLoginId();

        Set<Role> roles = userRepository.findRolesByRoleNameIn(user.getRoles());

        User userEntity = userRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("User is not found with loginId: " + loginId));

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setRoles(roles);


        String password = user.getPassword();
        if (password != null && !"".equals(password)) {
            String encodedPassword = passwordEncoder.encode(password);
            userEntity.setEncodedPassword(encodedPassword);
        }

        return new UserDTO(userEntity);
    }
}
