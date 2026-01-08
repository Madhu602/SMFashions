package com.example.SMFashions.Entity;

import com.example.SMFashions.Enum.CategoryType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "categories",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type"})
    }
)
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;      // T-Shirts, Dresses, Sarees

    @Enumerated(EnumType.STRING)
    private CategoryType type; // KIDS / MEN / WOMEN

    private Boolean active = true;
}
