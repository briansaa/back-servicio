package com.saditec.platform.controller;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.service.EducationalAndRecreationalFacilitiesService;
import com.saditec.platform.type.ApiResponse;
import com.saditec.platform.type.TEducationalAndRecreationalFacilityDto;
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

    @GetMapping("/{identifier}")
    public ApiResponse<TEducationalAndRecreationalFacilityDto> getEducationalAndRecreationalFacility(@PathVariable String identifier) {
        return new ApiResponse<TEducationalAndRecreationalFacilityDto>()
                .toSuccess(educationalAndRecreationalFacilitiesService.getEducationalAndRecreationalFacility(identifier));
    }

    @GetMapping("/type")
    public ApiResponse<List<TEducationalAndRecreationalFacilityTypeDto>> getAllEducationalAndRecreationalFacilityType() {
        return new ApiResponse<List<TEducationalAndRecreationalFacilityTypeDto>>()
                .toSuccess(educationalAndRecreationalFacilitiesService.getAllEducationalAndRecreationalFacilityType());
    }

    @PostMapping("/type")
    public ApiResponse<TEducationalAndRecreationalFacilityTypeDto> addEducationalAndRecreationalFacilityType(@RequestBody TEducationalAndRecreationalFacilityTypeDto tEducationalAndRecreationalFacilityTypeDto,
                                                                                                             Principal principal) {
        return new ApiResponse<TEducationalAndRecreationalFacilityTypeDto>()
                .toSuccess(educationalAndRecreationalFacilitiesService.addEducationalAndRecreationalFacilityType(tEducationalAndRecreationalFacilityTypeDto, principal));
    }

    @GetMapping("/type/{identifier}")
    public ApiResponse<List<TEducationalAndRecreationalFacilityDto>> getAllEducationalAndRecreationalFacility(@PathVariable String identifier) {
        return new ApiResponse<List<TEducationalAndRecreationalFacilityDto>>()
                .toSuccess(educationalAndRecreationalFacilitiesService.getAllEducationalAndRecreationalFacility(identifier));
    }
}
