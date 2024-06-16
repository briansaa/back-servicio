package com.saditec.platform.repository;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEducationalAndRecreationalFacilitiesTypeRepository extends JpaRepository<TEducationalAndRecreationalFacilityTypeEntity, String> {
}
