package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TEducationalAndRecreationalFacilityTypeEntity;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesTypeRepository;
import com.saditec.platform.type.TEducationalAndRecreationalFacilityTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationalAndRecreationalFacilitiesService {

    private final IEducationalAndRecreationalFacilitiesRepository educationalAndRecreationalFacilitiesRepository;
    private final IEducationalAndRecreationalFacilitiesTypeRepository educationalAndRecreationalFacilitiesTypeRepository;

    @Transactional
    public TEducationalAndRecreationalFacilityEntity add() {
        TEducationalAndRecreationalFacilityEntity tEducationalAndRecreationalFacilitiesEntity = new TEducationalAndRecreationalFacilityEntity();
        educationalAndRecreationalFacilitiesRepository.save(tEducationalAndRecreationalFacilitiesEntity);
        return tEducationalAndRecreationalFacilitiesEntity;
    }

    public List<TEducationalAndRecreationalFacilityTypeDto> getAllEducationalAndRecreationalFacility() {
        List<TEducationalAndRecreationalFacilityTypeEntity> educationalAndRecreationalFacilityEntityList = educationalAndRecreationalFacilitiesTypeRepository.findAll();
        return educationalAndRecreationalFacilityEntityList.stream().map(TEducationalAndRecreationalFacilityTypeEntity::toDto).toList();
    }

    @Transactional
    public TEducationalAndRecreationalFacilityTypeDto addEducationalAndRecreationalFacilityType(TEducationalAndRecreationalFacilityTypeDto tEducationalAndRecreationalFacilityTypeDto, Principal principal) {

        String userId = principal.getName();

        TEducationalAndRecreationalFacilityTypeEntity tEducationalAndRecreationalFacilityTypeEntity =
                educationalAndRecreationalFacilitiesTypeRepository.save(TEducationalAndRecreationalFacilityTypeEntity.toEntity(tEducationalAndRecreationalFacilityTypeDto, userId));

        return tEducationalAndRecreationalFacilityTypeEntity.toDto();
    }
}
