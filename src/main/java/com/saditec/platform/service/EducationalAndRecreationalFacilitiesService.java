package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EducationalAndRecreationalFacilitiesService {

    private final IEducationalAndRecreationalFacilitiesRepository educationalAndRecreationalFacilitiesRepository;

    @Transactional
    public TEducationalAndRecreationalFacilityEntity add(){
        TEducationalAndRecreationalFacilityEntity tEducationalAndRecreationalFacilitiesEntity = new TEducationalAndRecreationalFacilityEntity();
        educationalAndRecreationalFacilitiesRepository.save(tEducationalAndRecreationalFacilitiesEntity);
        return tEducationalAndRecreationalFacilitiesEntity;
    }
}
