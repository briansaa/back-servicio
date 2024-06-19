package com.saditec.platform.repository;

import com.saditec.platform.entity.TReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<TReservationEntity, String> {
}
