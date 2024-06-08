package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "t_reservation")
public class TReservationEntity extends TBaseEntity {

    @Column(name = "t_member_reservation")
    private String member;

    @Column(name = "t_member_description")
    private String memberDescription;

    @Column(name = "d_reservation")
    private Instant reservationDate;

    @Column(name = "lt_reservation_start_time")
    private LocalTime startTime;

    @Column(name = "lt_reservation_end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "t_member_identifier")
    private TMemberEntity memberEntity;

}
