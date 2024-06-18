package com.saditec.platform.repository;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TEducationalAndRecreationalFacilityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IEducationalAndRecreationalFacilitiesRepository extends JpaRepository<TEducationalAndRecreationalFacilityEntity, String> {

    @Query("SELECT DISTINCT u FROM TEducationalAndRecreationalFacilityEntity u " +
            "WHERE u.recreationalFacilityType.identifier = :identifier ")
    List<TEducationalAndRecreationalFacilityEntity> findByTEducationalType(String identifier);

    @Query("SELECT DISTINCT u FROM TEducationalAndRecreationalFacilityEntity u " +
            "LEFT JOIN u.recreationalFacilityHourEntities " +
            "LEFT JOIN u.tReservationEntities " +
            "WHERE u.identifier = :identifier")
    Optional<TEducationalAndRecreationalFacilityEntity> findByIdentifier(String identifier);
}
