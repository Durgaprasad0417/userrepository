package com.fireBnb.repository;

import com.fireBnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyUserRepository extends JpaRepository<PropertyUser, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<PropertyUser> findByUsername(String username);
}