package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Cart () {}

    public Cart(Long id) {
        id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }
}
