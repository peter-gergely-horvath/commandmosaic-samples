package org.commandmosaic.samples.springbootjpa.commands.memo;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.MemoDTO;
import org.commandmosaic.samples.springbootjpa.entities.Memo;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;

import java.security.Principal;
import java.util.Optional;

@Access.RequiresAuthority(ApplicationRole.USER)
public class GetMemoById implements Command<MemoDTO> {

    private final MemoRepository memoRepository;

    public GetMemoById(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Parameter
    private Long id;

    @Override
    public MemoDTO execute(CommandContext commandContext) {

        Principal principal = commandContext.getCallerPrincipal();
        String loginId = principal.getName();

        Optional<Memo> memoResult = memoRepository.findByIdAndOwner(id, loginId);
        if (!memoResult.isPresent()) {
            throw new RuntimeException("Memo not found / not owned by current user. Id: " + id);
        }

        return new MemoDTO(memoResult.get());
    }
}
