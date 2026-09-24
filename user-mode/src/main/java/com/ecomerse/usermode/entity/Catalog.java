package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Catalog () {}

    public Catalog(Long id) {
        id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }
}
