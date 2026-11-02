package com.saditec.platform.service;

import com.saditec.platform.entity.TEducationalAndRecreationalFacilityEntity;
import com.saditec.platform.entity.TMemberEntity;
import com.saditec.platform.entity.TReservationEntity;
import com.saditec.platform.error.BadRequestException;
import com.saditec.platform.repository.IEducationalAndRecreationalFacilitiesRepository;
import com.saditec.platform.repository.IMemberRepository;
import com.saditec.platform.repository.IReservationRepository;
import com.saditec.platform.type.TReservationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock IReservationRepository reservationRepository;
    @Mock IMemberRepository memberRepository;
    @Mock IEducationalAndRecreationalFacilitiesRepository facilitiesRepository;
    @InjectMocks ReservationService service;

    private final Principal principal = () -> "member-1";
    private TEducationalAndRecreationalFacilityEntity facility;
    private TMemberEntity member;

    @BeforeEach
    void setUp() {
        facility = new TEducationalAndRecreationalFacilityEntity();
        facility.setIdentifier("lab-101");
        member = new TMemberEntity();
        member.setIdentifier("member-1");
        member.setFirstName("Ana");
        member.setLastName("Perez");
    }

    private TReservationDto dto(String start, String end) {
        return TReservationDto.builder()
                .educationalAndRecreationalFacility("lab-101")
                .reservationDate("2026-11-03T00:00:00Z")
                .startTime(LocalTime.parse(start))
                .endTime(LocalTime.parse(end))
                .build();
    }

    @Test
    void createsReservationWhenSlotIsFree() {
        when(memberRepository.findById("member-1")).thenReturn(Optional.of(member));
        when(facilitiesRepository.findById("lab-101")).thenReturn(Optional.of(facility));
        when(reservationRepository.countOverlapping(any(), any(), any(), any(), any())).thenReturn(0L);
        when(reservationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0, TReservationEntity.class));

        TReservationDto result = service.addReservation(dto("08:00", "10:00"), principal);

        assertThat(result.getMemberDescription()).isEqualTo("Ana Perez");
        verify(reservationRepository).save(any());
    }

    @Test
    void rejectsOverlappingReservation() {
        when(memberRepository.findById("member-1")).thenReturn(Optional.of(member));
        when(facilitiesRepository.findById("lab-101")).thenReturn(Optional.of(facility));
        when(reservationRepository.countOverlapping(any(), any(), any(), any(), any())).thenReturn(1L);

        assertThatThrownBy(() -> service.addReservation(dto("09:00", "11:00"), principal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already reserved");
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void rejectsEndTimeBeforeStartTime() {
        assertThatThrownBy(() -> service.addReservation(dto("12:00", "11:00"), principal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("End time");
        verifyNoInteractions(reservationRepository);
    }

    @Test
    void rejectsInvalidDate() {
        TReservationDto invalid = dto("08:00", "10:00");
        invalid.setReservationDate("03/11/2026");
        assertThatThrownBy(() -> service.addReservation(invalid, principal))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("ISO-8601");
    }
}
