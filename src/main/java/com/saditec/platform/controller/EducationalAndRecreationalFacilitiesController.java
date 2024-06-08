package com.saditec.platform.controller;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.service.EducationalAndRecreationalFacilitiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educational/recreational/facilities")
@RequiredArgsConstructor
public class EducationalAndRecreationalFacilitiesController {

    private final EducationalAndRecreationalFacilitiesService educationalAndRecreationalFacilitiesService;

    @GetMapping
    public TEducationalAndRecreationalFacilityEntity add(){
        return educationalAndRecreationalFacilitiesService.add();
    }
}
