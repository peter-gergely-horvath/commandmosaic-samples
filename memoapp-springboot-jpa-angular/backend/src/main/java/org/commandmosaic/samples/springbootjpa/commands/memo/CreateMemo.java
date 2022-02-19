package org.commandmosaic.samples.springbootjpa.commands.memo;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.entities.Memo;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.samples.springbootjpa.repositories.UserRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;

import java.security.Principal;

@Access.RequiresAuthority(ApplicationRole.USER)
public class CreateMemo implements Command<Void> {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    public CreateMemo(MemoRepository memoRepository, UserRepository userRepository) {
        this.memoRepository = memoRepository;
        this.userRepository = userRepository;
    }


    @Parameter
    private String text;

    @Override
    public Void execute(CommandContext commandContext) {

        Principal principal = commandContext.getCallerPrincipal();
        String loginId = principal.getName();

        User user = userRepository.findById(loginId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + loginId));

        Memo memo = new Memo();
        memo.setText(text);
        memo.setOwner(user);

        memoRepository.save(memo);

        return null;
    }
}
