package com.saditec.platform.entity;

import com.saditec.platform.type.TEducationalAndRecreationalFacilityTypeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "t_educational_and_recreational_facility_type")
@SQLRestriction("b_enabled = 1")
public class TEducationalAndRecreationalFacilityTypeEntity extends TBaseEntity {

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "t_image_admin", columnDefinition = "LONGTEXT")
    private String image;

    @Column(name = "t_image_public", columnDefinition = "LONGTEXT")
    private String imagePublic;

    @OneToMany(mappedBy = "recreationalFacilityType")
    private Set<TEducationalAndRecreationalFacilityEntity> educationalAndRecreationalFacilities;

    public TEducationalAndRecreationalFacilityTypeDto toDto() {
        return TEducationalAndRecreationalFacilityTypeDto.builder()
                .identifier(getIdentifier())
                .name(getName())
                .description(getDescription())
                .image(getImage())
                .imagePublic(getImagePublic())
                .build();
    }

    public static TEducationalAndRecreationalFacilityTypeEntity toEntity(TEducationalAndRecreationalFacilityTypeDto tEducationalAndRecreationalFacilityTypeDto, String user) {

        TEducationalAndRecreationalFacilityTypeEntity tEducationalAndRecreationalFacilityTypeEntity = new TEducationalAndRecreationalFacilityTypeEntity();
        tEducationalAndRecreationalFacilityTypeEntity.setIdentifier(UUID.randomUUID().toString());
        tEducationalAndRecreationalFacilityTypeEntity.setName(tEducationalAndRecreationalFacilityTypeDto.getName());
        tEducationalAndRecreationalFacilityTypeEntity.setDescription(tEducationalAndRecreationalFacilityTypeDto.getDescription());
        tEducationalAndRecreationalFacilityTypeEntity.setImage(tEducationalAndRecreationalFacilityTypeDto.getImage());
        tEducationalAndRecreationalFacilityTypeEntity.setImagePublic(tEducationalAndRecreationalFacilityTypeDto.getImagePublic());
        tEducationalAndRecreationalFacilityTypeEntity.setEnabled(true);
        tEducationalAndRecreationalFacilityTypeEntity.setCreatedBy(user);
        tEducationalAndRecreationalFacilityTypeEntity.setCreatedAt(Instant.now());
        tEducationalAndRecreationalFacilityTypeEntity.setUpdatedBy(user);
        tEducationalAndRecreationalFacilityTypeEntity.setUpdatedAt(Instant.now());

        return tEducationalAndRecreationalFacilityTypeEntity;
    }
}
