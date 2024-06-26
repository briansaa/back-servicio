package com.saditec.platform.entity;

import com.saditec.platform.type.TEducationalAndRecreationalFacilityDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_educational_and_recreational_facility")
@SQLRestriction("b_enabled = 1")
public class TEducationalAndRecreationalFacilityEntity extends TBaseEntity {

    @Column(name = "t_description")
    private String description;

    @Column(name = "t_location")
    private String location;

    @ManyToOne
    @JoinColumn(name = "t_educational_and_recreational_facilities_type_identifier")
    private TEducationalAndRecreationalFacilityTypeEntity recreationalFacilityType;

    @ManyToMany(mappedBy = "educationalAndRecreationalFacilityEntities")
    private Set<TEducationalAndRecreationalFacilityHourEntity> recreationalFacilityHourEntities;

    @ManyToMany
    @JoinTable(
            name = "t_edu_rec_fac_and_t_rec_pav",
            joinColumns = @JoinColumn(name = "t_educational_facility_identifier"),
            inverseJoinColumns = @JoinColumn(name = "t_recreational_pavilion_identifier")
    )
    private Set<TEducationalAndRecreationalPavilionEntity> recreationalPavilions;

    @OneToMany(mappedBy = "educationalAndRecreationalFacilityEntity")
    private Set<TReservationEntity> tReservationEntities;


    public TEducationalAndRecreationalFacilityDto toDto() {
        return TEducationalAndRecreationalFacilityDto.builder()
                .identifier(getIdentifier())
                .description(getDescription())
                .recreationalHours(getRecreationalFacilityHourEntities() != null
                        ? getRecreationalFacilityHourEntities().stream().map(TEducationalAndRecreationalFacilityHourEntity::toDto).toList()
                        : Collections.emptyList())
                .reservations(getTReservationEntities() != null
                        ? getTReservationEntities().stream().map(TReservationEntity::toDto).toList()
                        : Collections.emptyList())
                .build();
    }
}
