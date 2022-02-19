package org.commandmosaic.samples.springbootjpa.commands.user;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.UserDTO;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.security.annotation.Access;

import java.util.List;
import java.util.stream.Collectors;

@Access.RequiresAuthority(ApplicationRole.ADMIN)
public class FindAll implements Command<List<UserDTO>> {

    private final UserRepository userRepository;

    public FindAll(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> execute(CommandContext context) {
        return userRepository.findAll()
                .stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
