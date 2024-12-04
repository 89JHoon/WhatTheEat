package com.example.whattheeat.repository;

import com.example.whattheeat.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
    List<ShopEntity> findByDeletedAtIsNull();
}

