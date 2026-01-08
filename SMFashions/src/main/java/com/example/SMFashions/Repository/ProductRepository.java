package com.example.SMFashions.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.Product;
import com.example.SMFashions.Enum.CategoryType;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_Id(Long categoryId);

    List<Product> findByCategory_Type(CategoryType type);
}
