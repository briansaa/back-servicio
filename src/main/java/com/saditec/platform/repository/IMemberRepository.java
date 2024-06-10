package com.saditec.platform.repository;

import com.saditec.platform.entity.TMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<TMemberEntity, String> {
}
