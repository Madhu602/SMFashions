package com.example.SMFashions.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.ProductImage;

public interface ProductImageRepository
extends JpaRepository<ProductImage, Long> {

List<ProductImage> findByProduct_Id(Long productId);
}
