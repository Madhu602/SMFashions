package com.example.SMFashions.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
