package com.saditec.platform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public class TBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String identifier;

    @Column(name = "t_created_by")
    private String createdBy;

    @Column(name = "d_created_at")
    private Instant createdAt;

    @Column(name = "t_updated_by")
    private String updatedBy;

    @Column(name = "d_updated_at")
    private Instant updatedAt;
}
