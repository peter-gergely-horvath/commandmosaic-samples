package org.commandmosaic.samples.springbootjpa.commands.user;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;

@Access.RequiresAuthority(ApplicationRole.ADMIN)
public class DeleteByLoginId implements Command<Void> {

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    public DeleteByLoginId(UserRepository userRepository, MemoRepository memoRepository) {
        this.userRepository = userRepository;
        this.memoRepository = memoRepository;
    }


    @Parameter
    private String loginId;


    @Override
    public Void execute(CommandContext context) {

        memoRepository.deleteAllByOwnerId(loginId);


        userRepository.deleteById(loginId);

        return null;
    }

}