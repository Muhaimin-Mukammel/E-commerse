package com.ecomerse.usermode.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Transaction () {}

    public Transaction(Long id) {
        id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }
}
