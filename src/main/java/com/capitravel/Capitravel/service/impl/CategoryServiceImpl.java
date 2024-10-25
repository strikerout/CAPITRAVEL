package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.repository.CategoryRepository;
import com.capitravel.Capitravel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category idCategory = categoryRepository.findById(id).orElse(null);

        if(idCategory!=null){
            return idCategory;
        }

        throw new IllegalArgumentException("Category for id:" + id + " not found.");
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {

       if(validateCategory(category)) {
           return categoryRepository.save(category);
       }
        throw new IllegalArgumentException("Category does not follow basic structure.");
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isPresent() && validateCategory(category)) {

                Category updatedCategory = existingCategory.get();
                updatedCategory.setName(category.getName());
                updatedCategory.setDescription(category.getDescription());
                return categoryRepository.save(updatedCategory);

        }
        throw new IllegalArgumentException("The Category for id:" + id + " not found.");
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean validateCategory (Category category){
        if (category.getName() == null || category.getName().isEmpty() || category.getName().length() < 3 || category.getName().length() > 50) {
            throw new IllegalArgumentException("Category name must be between 3 and 50 characters long.");
        }
        else if (category.getDescription() == null || category.getDescription().isEmpty() || category.getDescription().length() < 5 || category.getDescription().length() > 100) {
            throw new IllegalArgumentException("Category description must be between 5 and 100 characters long.");
        }
        return true;
    }
}
