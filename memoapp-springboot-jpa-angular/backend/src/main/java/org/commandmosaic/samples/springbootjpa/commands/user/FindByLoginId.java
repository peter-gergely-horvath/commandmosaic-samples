package org.commandmosaic.samples.springbootjpa.commands.user;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.UserDTO;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;

@Access.RequiresAuthority(ApplicationRole.ADMIN)
public class FindByLoginId implements Command<UserDTO> {

    private final UserRepository userRepository;

    public FindByLoginId(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Parameter
    private String loginId;


    @Override
    public UserDTO execute(CommandContext context) {

        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new RuntimeException("User not found: " + loginId));


        return new UserDTO(user);
    }

}