package com.saditec.platform.security.auth.repository;

import com.saditec.platform.security.auth.entity.TUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<TUserEntity, String> {

    @Query("SELECT DISTINCT u FROM TUserEntity u " +
            "JOIN FETCH u.roles " +
            "WHERE u.email = :username OR u.code = :username")
    Optional<TUserEntity> findByEmailOrCode(String username);
}
