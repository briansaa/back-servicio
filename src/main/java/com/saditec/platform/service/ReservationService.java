package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TMemberEntity;
import com.saditec.platform.entity.TReservationEntity;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IMemberRepository;
import com.saditec.platform.repository.IReservationRepository;
import com.saditec.platform.type.TReservationDto;
import com.saditec.platform.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final IReservationRepository reservationRepository;
    private final IMemberRepository memberRepository;
    private final IEducationalAndRecreationalFacilitiesRepository educationalAndRecreationalFacilitiesRepository;

    @Transactional
    public TReservationDto addReservation(TReservationDto tReservationDto, Principal principal) {

        validate(tReservationDto);

        String userId = principal.getName();

        TMemberEntity tMemberEntity = memberRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("No member found"));
        TEducationalAndRecreationalFacilityEntity educationalAndRecreationalFacilityEntity = educationalAndRecreationalFacilitiesRepository
                .findById(tReservationDto.getEducationalAndRecreationalFacility())
                .orElseThrow(() -> new BadRequestException("No educational and recreational facility found"));

        Instant day = parseDate(tReservationDto.getReservationDate()).truncatedTo(ChronoUnit.DAYS);
        long overlapping = reservationRepository.countOverlapping(
                educationalAndRecreationalFacilityEntity.getIdentifier(),
                day, day.plus(1, ChronoUnit.DAYS),
                tReservationDto.getStartTime(), tReservationDto.getEndTime());
        if (overlapping > 0) {
            throw new BadRequestException("The facility is already reserved in that time range");
        }

        TReservationEntity tReservationEntity = TReservationEntity.toEntity(tReservationDto, tMemberEntity, educationalAndRecreationalFacilityEntity);

        tReservationEntity = reservationRepository.save(tReservationEntity);
        return tReservationEntity.toDto();
    }

    private void validate(TReservationDto dto) {
        if (dto.getEducationalAndRecreationalFacility() == null || dto.getEducationalAndRecreationalFacility().isBlank()) {
            throw new BadRequestException("Facility is required");
        }
        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new BadRequestException("Start time and end time are required");
        }
        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new BadRequestException("End time must be after start time");
        }
        parseDate(dto.getReservationDate());
    }

    private Instant parseDate(String value) {
        try {
            return Instant.parse(value);
        } catch (Exception e) {
            throw new BadRequestException("Reservation date must be ISO-8601, e.g. 2026-11-03T00:00:00Z");
        }
    }
}
