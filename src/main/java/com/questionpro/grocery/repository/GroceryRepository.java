package com.questionpro.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroceryRepository extends JpaRepository<Grocery, Integer> {
    Optional<Grocery> findByName(String name);
}
