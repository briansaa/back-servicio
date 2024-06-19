package com.saditec.platform.entity;

import com.saditec.platform.type.TReservationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "t_reservation")
public class TReservationEntity extends TBaseEntity {

    @Column(name = "t_member_reservation")
    private String member;

    @Column(name = "t_member_description")
    private String memberDescription;

    @Column(name = "d_reservation")
    private Instant reservationDate;

    @Column(name = "lt_reservation_start_time")
    private LocalTime startTime;

    @Column(name = "lt_reservation_end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "t_member_identifier")
    private TMemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "t_educational_and_recreational_facility_identifier")
    private TEducationalAndRecreationalFacilityEntity educationalAndRecreationalFacilityEntity;

    public TReservationDto toDto() {
        return TReservationDto.builder()
                .identifier(getIdentifier())
                .member(getMember())
                .memberDescription(getMemberDescription())
                .educationalAndRecreationalFacility(getEducationalAndRecreationalFacilityEntity().getIdentifier())
                .reservationDate(getReservationDate().toString())
                .startTime(getStartTime())
                .endTime(getEndTime())
                .build();
    }

    public static TReservationEntity toEntity(TReservationDto tReservationDto, TMemberEntity tMemberEntity, TEducationalAndRecreationalFacilityEntity educationalAndRecreationalFacilityEntity) {
        TReservationEntity tReservationEntity = new TReservationEntity();
        tReservationEntity.setMember(tMemberEntity.getIdentifier());
        tReservationEntity.setMemberDescription(tMemberEntity.getFirstName().concat(" ").concat(tMemberEntity.getLastName()));
        tReservationEntity.setReservationDate(Instant.parse(tReservationDto.getReservationDate()));
        tReservationEntity.setStartTime(tReservationDto.getStartTime());
        tReservationEntity.setEndTime(tReservationDto.getEndTime());
        tReservationEntity.setMemberEntity(tMemberEntity);
        tReservationEntity.setEducationalAndRecreationalFacilityEntity(educationalAndRecreationalFacilityEntity);
        return tReservationEntity;
    }
}
