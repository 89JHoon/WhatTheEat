package com.example.whattheeat.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "menu")
public class MenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private BigDecimal price;
}
public MenuEntity(){
}

