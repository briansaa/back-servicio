package com.saditec.platform.entity;

import com.saditec.platform.security.auth.entity.TRoleEntity;
import com.saditec.platform.type.TMemberDto;
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

    @Column(name = "t_code", columnDefinition = "VARCHAR(10)", unique = true, nullable = false)
    private String code;

    @Column(name = "t_first_name")
    private String firstName;

    @Column(name = "t_last_name")
    private String lastName;

    @Column(name = "t_password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "t_member_and_t_role",
            joinColumns = @JoinColumn(name = "t_member_identifier"),
            inverseJoinColumns = @JoinColumn(name = "t_role_identifier"))
    private Set<TRoleEntity> roles;

    @OneToMany(mappedBy = "memberEntity")
    private Set<TReservationEntity> tReservationEntities;

    public TMemberDto toDto() {
        return TMemberDto.builder()
                .email(this.email)
                .code(this.code)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }
}
