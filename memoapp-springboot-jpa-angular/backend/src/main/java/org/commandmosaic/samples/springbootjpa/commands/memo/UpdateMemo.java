package org.commandmosaic.samples.springbootjpa.commands.memo;

import org.commandmosaic.samples.springbootjpa.ApplicationRole;
import org.commandmosaic.samples.springbootjpa.dto.MemoDTO;
import org.commandmosaic.samples.springbootjpa.entities.Memo;
import org.commandmosaic.samples.springbootjpa.repositories.MemoRepository;
import org.commandmosaic.api.Command;
import org.commandmosaic.api.CommandContext;
import org.commandmosaic.api.Parameter;
import org.commandmosaic.security.annotation.Access;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Access.RequiresAuthority(ApplicationRole.USER)
public class UpdateMemo implements Command<Void> {

    private final MemoRepository memoRepository;

    public UpdateMemo(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Parameter
    private MemoDTO memo;


    @Transactional // required in order to get updates automatically flushed to the database
    @Override
    public Void execute(CommandContext commandContext) {

        String loginId = commandContext.getCallerPrincipal().getName();

        Optional<Memo> memoResult = memoRepository.findByIdAndOwner(this.memo.getId(), loginId);
        if (!memoResult.isPresent()) {
            throw new RuntimeException("Memo not found / not owned by current user: " + memo);
        }

        Memo memoEntity = memoResult.get();

        memoEntity.setText(this.memo.getText());

        return null;
    }
}
