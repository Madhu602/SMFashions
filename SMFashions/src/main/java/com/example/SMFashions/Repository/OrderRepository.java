package com.example.SMFashions.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
