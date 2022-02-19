package org.commandmosaic.samples.springbootjpa.commands.memo;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;

import java.security.Principal;

@Access.RequiresAuthority(ApplicationRole.USER)
public class DeleteMemoById implements Command<Void> {

    private final MemoRepository memoRepository;

    public DeleteMemoById(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }


    @Parameter
    private Long id;

    @Override
    public Void execute(CommandContext commandContext) {

        Principal principal = commandContext.getCallerPrincipal();
        String loginId = principal.getName();

        int deleteCount = memoRepository.deleteByIdAndOwner(id, loginId);
        if (deleteCount == 0) {
            throw new RuntimeException("Could not find Memo with Id=" + id);
        }

        return null;
    }
}
