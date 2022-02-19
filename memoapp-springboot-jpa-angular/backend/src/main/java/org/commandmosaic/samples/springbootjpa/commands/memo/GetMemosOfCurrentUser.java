package org.commandmosaic.samples.springbootjpa.commands.memo;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.MemoDTO;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.security.annotation.Access;

import java.security.Principal;
import java.util.stream.Collectors;

@Access.RequiresAuthority(ApplicationRole.USER)
public class GetMemosOfCurrentUser implements Command<Iterable<MemoDTO>> {

    private final MemoRepository memoRepository;

    public GetMemosOfCurrentUser(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public Iterable<MemoDTO> execute(CommandContext commandContext) {

        Principal principal = commandContext.getCallerPrincipal();
        String loginId = principal.getName();

        return memoRepository.findByOwner(loginId)
                .stream().map(MemoDTO::new).collect(Collectors.toList());
    }
}
