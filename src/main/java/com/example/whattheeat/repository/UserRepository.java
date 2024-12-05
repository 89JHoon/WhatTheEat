package com.example.whattheeat.repository;

import com.example.whattheeat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value =
            "SELECT EXISTS (SELECT 1 FROM USER WHERE email = ?1) ",
            nativeQuery = true)
    Long existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
