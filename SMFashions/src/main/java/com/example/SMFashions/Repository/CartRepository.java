package com.example.SMFashions.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByAccount_IdAndActiveTrue(Long accountId);
}
