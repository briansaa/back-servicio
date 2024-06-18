package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TEducationalAndRecreationalFacilityTypeEntity;
import com.saditec.platform.error.AppException;
import com.saditec.platform.error.BadRequestException;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesTypeRepository;
import com.saditec.platform.type.TEducationalAndRecreationalFacilityDto;
import com.saditec.platform.type.TEducationalAndRecreationalFacilityTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationalAndRecreationalFacilitiesService {

    private final IEducationalAndRecreationalFacilitiesRepository educationalAndRecreationalFacilitiesRepository;
    private final IEducationalAndRecreationalFacilitiesTypeRepository educationalAndRecreationalFacilitiesTypeRepository;

    public TEducationalAndRecreationalFacilityDto getEducationalAndRecreationalFacility(String identifier) {
        Optional<TEducationalAndRecreationalFacilityEntity> educationalAndRecreationalFacilityEntity = educationalAndRecreationalFacilitiesRepository.findByIdentifier(identifier);

        if (educationalAndRecreationalFacilityEntity.isEmpty()) throw new AppException("No educational and recreational facility found");

        return educationalAndRecreationalFacilityEntity.get().toDto();
    }

    public List<TEducationalAndRecreationalFacilityDto> getAllEducationalAndRecreationalFacility(String identifier) {
        List<TEducationalAndRecreationalFacilityEntity> educationalAndRecreationalFacilityEntity = educationalAndRecreationalFacilitiesRepository.findByTEducationalType(identifier);
        return educationalAndRecreationalFacilityEntity.stream().map(TEducationalAndRecreationalFacilityEntity::toDto).toList();
    }

    public List<TEducationalAndRecreationalFacilityTypeDto> getAllEducationalAndRecreationalFacilityType() {
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
