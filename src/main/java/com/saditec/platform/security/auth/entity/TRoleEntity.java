package com.saditec.platform.security.auth.entity;

import com.saditec.platform.entity.TBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_role")
public class TRoleEntity extends TBaseEntity implements GrantedAuthority {

    @Column(name = "t_description", nullable = false, unique = true)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<TUserEntity> users;

    @Override
    public String getAuthority() {
        return this.description;
    }
}
