package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_educational_and_recreational_facility_type")
public class TEducationalAndRecreationalFacilityTypeEntity extends TBaseEntity {

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "t_image_admin", columnDefinition = "TEXT")
    private String image;

    @Column(name = "t_image_public", columnDefinition = "TEXT")
    private String imagePublic;

    @OneToMany(mappedBy = "type")
    private Set<TEducationalAndRecreationalFacilityEntity> educationalAndRecreationalFacilities;
}
