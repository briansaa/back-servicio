package com.saditec.platform.security.auth.repository;

import com.saditec.platform.security.auth.entity.TRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<TRoleEntity, String> {

    List<TRoleEntity> findByDescriptionIsIn(List<String> descriptions);
}
