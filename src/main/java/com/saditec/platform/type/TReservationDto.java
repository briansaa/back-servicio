package com.saditec.platform.type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class TReservationDto {

    private String identifier;
    private String member;
    private String memberDescription;
    private String educationalAndRecreationalFacility;
    private String reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;

}
