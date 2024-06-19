package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TMemberEntity;
import com.saditec.platform.entity.TReservationEntity;
import com.saditec.platform.error.AppException;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IMemberRepository;
import com.saditec.platform.repository.IReservationRepository;
import com.saditec.platform.type.TReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IMemberRepository memberRepository;
    private final IEducationalAndRecreationalFacilitiesRepository educationalAndRecreationalFacilitiesRepository;

    @Transactional
    public TReservationDto addReservation(TReservationDto tReservationDto, Principal principal) {

        String userId = principal.getName();

        TMemberEntity tMemberEntity = memberRepository.findById(userId).orElseThrow(() -> new AppException("No member found"));
        TEducationalAndRecreationalFacilityEntity educationalAndRecreationalFacilityEntity = educationalAndRecreationalFacilitiesRepository.findById(tReservationDto.getEducationalAndRecreationalFacility()).orElseThrow(() -> new AppException("No educational and recreational facility found"));

        TReservationEntity tReservationEntity = TReservationEntity.toEntity(tReservationDto, tMemberEntity, educationalAndRecreationalFacilityEntity);

        tReservationEntity = reservationRepository.save(tReservationEntity);
        return tReservationEntity.toDto();
    }
}
