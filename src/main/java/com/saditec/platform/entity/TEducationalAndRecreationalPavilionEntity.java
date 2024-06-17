package com.saditec.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_educational_and_recreational_pavilion")
public class TEducationalAndRecreationalPavilionEntity extends TBaseEntity{

    @Column(name = "t_name")
    private String name;

    @ManyToMany(mappedBy = "recreationalPavilions")
    private Set<TEducationalAndRecreationalFacilityEntity> recreationalFacilities;

}
