package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_reservation_hour")
public class TReservationHourEntity extends TBaseEntity {

    @Column(name = "lt_start_time")
    private LocalTime startTime;

    @Column(name = "lt_end_time")
    private LocalTime endTime;

    @ManyToMany
    @JoinTable(name = "t_reservation_hour_and_educational_and_recreational_facility",
            joinColumns = @JoinColumn(name = "t_reservation_hour_identifier"),
            inverseJoinColumns = @JoinColumn(name = "t_educational_and_recreational_facilities_identifier"))
    private Set<TEducationalAndRecreationalFacilityEntity> educationalAndRecreationalFacilityEntities;
}
