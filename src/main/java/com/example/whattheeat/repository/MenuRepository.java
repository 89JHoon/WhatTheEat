package com.example.whattheeat.repository;

import com.example.whattheeat.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{
    Optional<Menu> findMenuById(int menuId);
}
