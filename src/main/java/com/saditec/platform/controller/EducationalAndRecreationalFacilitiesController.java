package com.saditec.platform.controller;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.service.EducationalAndRecreationalFacilitiesService;
import com.saditec.platform.type.ApiResponse;
import com.saditec.platform.type.TEducationalAndRecreationalFacilityTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/educational/recreational/facilities")
@RequiredArgsConstructor
public class EducationalAndRecreationalFacilitiesController {

    private final EducationalAndRecreationalFacilitiesService educationalAndRecreationalFacilitiesService;

    @GetMapping
    public TEducationalAndRecreationalFacilityEntity add() {
        return educationalAndRecreationalFacilitiesService.add();
    }

    @GetMapping("/type")
    public ApiResponse<List<TEducationalAndRecreationalFacilityTypeDto>> getAllEducationalAndRecreationalFacility() {
        return new ApiResponse<List<TEducationalAndRecreationalFacilityTypeDto>>()
                .toSuccess(educationalAndRecreationalFacilitiesService.getAllEducationalAndRecreationalFacility());
    }

    @PostMapping("/type")
    public ApiResponse<TEducationalAndRecreationalFacilityTypeDto> addEducationalAndRecreationalFacilityType(@RequestBody TEducationalAndRecreationalFacilityTypeDto tEducationalAndRecreationalFacilityTypeDto,
                                                                                                             Principal principal) {
        return new ApiResponse<TEducationalAndRecreationalFacilityTypeDto>()
                .toSuccess(educationalAndRecreationalFacilitiesService.addEducationalAndRecreationalFacilityType(tEducationalAndRecreationalFacilityTypeDto, principal));
    }
}
