package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_member", indexes = {@Index(columnList = "t_email,t_code")})
public class TMemberEntity extends TBaseEntity {

    @Column(name = "t_email", unique = true, nullable = false)
    private String email;

    @Column(name = "t_code",columnDefinition = "VARCHAR(10)", unique = true, nullable = false)
    private String code;

    @Column(name = "t_first_name")
    private String firstName;

    @Column(name = "t_last_name")
    private String lastName;

    @OneToMany(mappedBy = "memberEntity")
    private Set<TReservationEntity> tReservationEntities;
}
