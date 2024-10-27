package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.exception.ResourceNotFoundException;
import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.dto.CategoryDTO;
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
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(category);

    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isPresent()) {

                Category updatedCategory = existingCategory.get();
                updatedCategory.setName(categoryDTO.getName());
                updatedCategory.setDescription(categoryDTO.getDescription());
                return categoryRepository.save(updatedCategory);

        }
        throw new ResourceNotFoundException("The Category for id:" + id + " not found.");
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
