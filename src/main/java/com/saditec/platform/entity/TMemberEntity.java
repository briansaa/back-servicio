package com.saditec.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_member")
public class TMemberEntity extends TBaseEntity {

    @OneToMany(mappedBy = "memberEntity")
    private Set<TReservationEntity> tReservationEntities;
}
