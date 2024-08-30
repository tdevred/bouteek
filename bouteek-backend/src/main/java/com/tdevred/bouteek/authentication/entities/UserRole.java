package com.tdevred.bouteek.authentication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
