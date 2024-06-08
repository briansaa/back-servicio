package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_educational_and_recreational_facility")
public class TEducationalAndRecreationalFacilityEntity extends TBaseEntity {

    @Column(name = "t_description")
    private String description;

    @Column(name = "t_pavilion", columnDefinition = "VARCHAR(100)")
    private String pavilion;

    @ManyToOne
    @JoinColumn(name = "t_educational_and_recreational_facilities_type_identifier")
    private TEducationalAndRecreationalFacilityTypeEntity type;

    @ManyToMany(mappedBy = "educationalAndRecreationalFacilityEntities")
    private Set<TReservationHourEntity> tReservationEntities;
}
