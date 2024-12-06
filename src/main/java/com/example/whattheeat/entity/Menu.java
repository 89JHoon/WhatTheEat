package com.example.whattheeat.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "menu")
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    public Menu(Shop shop, String name, BigDecimal price) {
        this.shop = shop;
        this.name = name;
        this.price = price;
    }

    public void updateMenu(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void deleteMenu() {
        this.isDeleted = true;
    }
}



