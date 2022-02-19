package org.commandmosaic.samples.springbootjpa.repositories;

import org.commandmosaic.samples.springbootjpa.entities.Role;
import org.commandmosaic.samples.springbootjpa.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query("from User")
    List<User> findAll();

    @Query("from User where loginId = :loginId and passwordHash = :passwordHash")
    Optional<User> findByLoginIdAndPasswordHash(
            @Param(value = "loginId") String loginId,
            @Param(value = "passwordHash") String passwordHash);


    @Query("from Role role where role.name in :roleNames")
    Set<Role> findRolesByRoleNameIn(@Param(value = "roleNames") Set<String> rolesNames);

}