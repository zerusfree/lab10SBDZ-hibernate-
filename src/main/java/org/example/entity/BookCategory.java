package org.example.entity;

import jakarta.persistence.*;

@Entity
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String categoryName;

    // Конструктор без параметрів
    public BookCategory() {}

    // Конструктор з параметрами
    public BookCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    // Геттери та Сеттери
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
