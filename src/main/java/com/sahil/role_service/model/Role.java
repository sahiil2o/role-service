package com.sahil.role_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.registry.selector.StrategyRegistration;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String description;
}
