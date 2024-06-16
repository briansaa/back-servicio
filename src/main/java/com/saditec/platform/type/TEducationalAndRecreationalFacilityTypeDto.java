package com.saditec.platform.type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TEducationalAndRecreationalFacilityTypeDto {

    private String identifier;
    private String name;
    private String description;
    private String image;
    private String imagePublic;

}
