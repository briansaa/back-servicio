package com.saditec.platform.security.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saditec.platform.entity.TBaseEntity;
import com.saditec.platform.security.auth.reference.EPermissionReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Stream;

@Entity
@Table(name = "t_member")
public class TUserEntity extends TBaseEntity implements UserDetails {

    @Column(name = "t_email", unique = true, nullable = false)
    private String email;

    @Column(name = "t_code", columnDefinition = "VARCHAR(10)", unique = true, nullable = false)
    private String code;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "t_password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "t_member_and_t_role",
            joinColumns = @JoinColumn(name = "t_member_identifier"),
            inverseJoinColumns = @JoinColumn(name = "t_role_identifier"))
    private Set<TRoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<String> _roles = roles.stream().map(TRoleEntity::getAuthority).toList();
        List<String> _permissions = Arrays.stream(EPermissionReference.values())
                .flatMap(ePermissionReference -> _roles.contains(ePermissionReference.name())
                        ? ePermissionReference.getPermissions().stream()
                        : Stream.empty()
                ).toList();

        return Stream.concat(_roles.stream(), _permissions.stream()).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.getEnabled();
    }
}
