package com.saditec.platform.repository;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IEducationalAndRecreationalFacilitiesRepository extends JpaRepository<TEducationalAndRecreationalFacilityEntity, UUID> {
}
