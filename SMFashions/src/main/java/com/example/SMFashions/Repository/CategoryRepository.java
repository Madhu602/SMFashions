package com.example.SMFashions.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SMFashions.Entity.Category;
import com.example.SMFashions.Enum.CategoryType;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCaseAndType(String name, CategoryType type);

    List<Category> findByTypeAndActiveTrue(CategoryType type);

    Optional<Category> findByIdAndActiveTrue(Long id);
}
