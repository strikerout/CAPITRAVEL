package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
