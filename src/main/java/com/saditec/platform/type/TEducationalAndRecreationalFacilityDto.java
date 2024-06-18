package com.saditec.platform.type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TEducationalAndRecreationalFacilityDto {

    private String identifier;
    private String description;
    private List<TEducationalAndRecreationalHourDto> recreationalHours;
    private List<TReservationDto> reservations;
}
