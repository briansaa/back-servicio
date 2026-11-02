package com.saditec.platform.repository;

import com.saditec.platform.entity.TReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalTime;

@Repository
public interface IReservationRepository extends JpaRepository<TReservationEntity, String> {

    /**
     * Cuenta reservas activas del mismo espacio y día cuyo rango horario se cruza con [start, end).
     */
    @Query("SELECT COUNT(r) FROM TReservationEntity r " +
            "WHERE r.enabled = true " +
            "AND r.educationalAndRecreationalFacilityEntity.identifier = :facility " +
            "AND r.reservationDate >= :dayStart AND r.reservationDate < :dayEnd " +
            "AND r.startTime < :end AND r.endTime > :start")
    long countOverlapping(@Param("facility") String facility,
                          @Param("dayStart") Instant dayStart,
                          @Param("dayEnd") Instant dayEnd,
                          @Param("start") LocalTime start,
                          @Param("end") LocalTime end);
}
