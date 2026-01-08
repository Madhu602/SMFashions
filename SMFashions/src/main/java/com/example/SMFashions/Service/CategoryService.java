package com.example.SMFashions.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SMFashions.Entity.Category;
import com.example.SMFashions.Enum.CategoryType;
import com.example.SMFashions.Repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Admin adds category
    public Category addCategory(String name, CategoryType type) {

        if (categoryRepository.existsByNameIgnoreCaseAndType(name, type)) {
            throw new RuntimeException("Category already exists for this type");
        }

        Category category = new Category();
        category.setName(name);
        category.setType(type);
        category.setActive(true);

        return categoryRepository.save(category);
    }

    // Used by UI dropdown
    public List<Category> getCategoriesByType(CategoryType type) {
        return categoryRepository.findByTypeAndActiveTrue(type);
    }
}
