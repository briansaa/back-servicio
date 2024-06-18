package com.saditec.platform.type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class TEducationalAndRecreationalHourDto {
    private String identifier;
    private LocalTime startTime;
    private LocalTime endTime;
}
