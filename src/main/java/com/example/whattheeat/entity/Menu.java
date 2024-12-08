package com.example.whattheeat.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@SQLDelete(sql = "update menu set is_deleted = true, deleted_at = current_timestamp where id = ?")
@SQLRestriction("is_deleted = false")
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

    public void deleteMenu(LocalDateTime deletedAt) {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    public void deleteMenu() {
        this.isDeleted = true;
    }
}




