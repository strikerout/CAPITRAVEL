package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.model.Experience;
import com.capitravel.Capitravel.model.Property;
import com.capitravel.Capitravel.repository.CategoryRepository;
import com.capitravel.Capitravel.repository.ExperienceRepository;
import com.capitravel.Capitravel.repository.PropertyRepository;
import com.capitravel.Capitravel.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }

    @Override
    public Experience createExperience(Experience experience) {
        List<Category> categories = experience.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        experience.setCategories(categories);

        List<Property> properties = experience.getProperty().stream()
                .map(property -> propertyRepository.findById(property.getId())
                        .orElseThrow(() -> new RuntimeException("Property not found")))
                .collect(Collectors.toList());

        experience.setProperty(properties);
        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateExperience(Long id, Experience updatedExperience) {
        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        List<Category> categories = updatedExperience.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        List<Property> properties = updatedExperience.getProperty().stream()
                .map(property -> propertyRepository.findById(property.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        existingExperience.setProperty(properties);
        existingExperience.setCategories(categories);
        existingExperience.setTitle(updatedExperience.getTitle());
        existingExperience.setCountry(updatedExperience.getCountry());
        existingExperience.setUbication(updatedExperience.getUbication());
        existingExperience.setDescription(updatedExperience.getDescription());
        existingExperience.setImages(updatedExperience.getImages());
        existingExperience.setDuration(updatedExperience.getDuration());

        return experienceRepository.save(existingExperience);
    }

    @Override
    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}
