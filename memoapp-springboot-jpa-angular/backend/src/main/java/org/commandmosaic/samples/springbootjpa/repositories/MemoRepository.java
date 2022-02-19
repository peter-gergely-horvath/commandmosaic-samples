package org.commandmosaic.samples.springbootjpa.repositories;

import org.commandmosaic.samples.springbootjpa.entities.Memo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends CrudRepository<Memo, Long> {

    @Query("from Memo memo where memo.owner.loginId = :loginId")
    List<Memo> findByOwner(@Param(value = "loginId") String loginId);

    @Query("from Memo memo where memo.id = :id and memo.owner.loginId = :loginId")
    Optional<Memo> findByIdAndOwner(@Param(value = "id") long id,
                                    @Param(value = "loginId") String loginId);

    @Transactional
    @Modifying
    @Query("delete Memo memo where memo.id = :id and memo.owner.loginId = :loginId")
    int deleteByIdAndOwner(@Param(value = "id") long id,
                           @Param(value = "loginId") String loginId);

    @Transactional
    @Modifying
    @Query("delete Memo memo where memo.owner.loginId = :loginId")
    void deleteAllByOwnerId(@Param(value = "loginId") String loginId);

}