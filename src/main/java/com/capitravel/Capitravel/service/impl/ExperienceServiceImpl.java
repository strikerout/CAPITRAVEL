package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.dto.ExperienceDTO;
import com.capitravel.Capitravel.dto.ReservationDatesDTO;
import com.capitravel.Capitravel.exception.DuplicatedResourceException;
import com.capitravel.Capitravel.exception.ResourceNotFoundException;
import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.model.Experience;
import com.capitravel.Capitravel.model.Property;
import com.capitravel.Capitravel.repository.CategoryRepository;
import com.capitravel.Capitravel.repository.ExperienceRepository;
import com.capitravel.Capitravel.repository.PropertyRepository;
import com.capitravel.Capitravel.service.CategoryService;
import com.capitravel.Capitravel.service.ExperienceService;
import com.capitravel.Capitravel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    public static final String CATEGORIES_FIELD_NAME = "Categories";
    public static final String PROPERTIES_FIELD_NAME = "Properties";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReservationService reservationService;

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience with id: " + id + " not found"));
    }

    @Override
    public List<Experience> getExperiencesByCategories(List<Long> categoryIds) {
        List<Long> validCategoryIds = new ArrayList<>();
        List<Long> notFoundCategoryIds = new ArrayList<>();

        for (Long categoryId : categoryIds) {
            try {
                categoryService.getCategoryById(categoryId);
                validCategoryIds.add(categoryId);
            } catch (Exception e) {
                notFoundCategoryIds.add(categoryId);
            }
        }
        if (!notFoundCategoryIds.isEmpty()) {
            throw new ResourceNotFoundException("Categories not found: " + notFoundCategoryIds);
        }
        Long categoryCount = (long) validCategoryIds.size();
        return experienceRepository.findByCategoryIds(validCategoryIds, categoryCount);
    }

    @Override
    public List<String> getCountriesFromExperiences() {
        List<Experience> experiences = experienceRepository.findAll();

        return experiences.stream()
                .map(Experience::getCountry)
                .filter(Objects::nonNull)
                .map(country -> country.trim().toLowerCase())
                .distinct()
                .map(this::capitalizeEachWord)
                .collect(Collectors.toList());
    }

    @Override
    public List<Experience> searchExperiences(String keywords, String country, LocalDateTime startDate, LocalDateTime endDate) {
        List<Experience> experiences = experienceRepository.findAll();

        if (keywords != null && !keywords.isEmpty()) {
            List<String> keywordList = Arrays.asList(keywords.toLowerCase().split(" "));

            experiences = experiences.stream()
                    .filter(exp -> {
                        String title = exp.getTitle().toLowerCase();
                        boolean titleMatches = keywordList.stream().anyMatch(title::contains);

                        boolean propertyMatches = exp.getProperties().stream()
                                .anyMatch(prop -> keywordList.stream()
                                        .anyMatch(keyword -> prop.getName().toLowerCase().contains(keyword)));

                        return titleMatches || propertyMatches;
                    })
                    .collect(Collectors.toList());
        }

        if (country != null && !country.isEmpty()) {
            experiences = experiences.stream()
                    .filter(exp -> exp.getCountry().toLowerCase().contains(country.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            experiences = experiences.stream()
                    .filter(exp -> isAvailable(exp.getId(), startDate, endDate))
                    .collect(Collectors.toList());
        }

        return experiences;
    }

    @Override
    public Experience createExperience(ExperienceDTO experienceDTO) {
        if (experienceRepository.existsByTitle(experienceDTO.getTitle())) {
            throw new DuplicatedResourceException("An experience with title " + experienceDTO.getTitle() + " already exists");
        }

        validateNoDuplicates(experienceDTO.getCategoryIds(), CATEGORIES_FIELD_NAME);
        validateNoDuplicates(experienceDTO.getPropertyIds(), PROPERTIES_FIELD_NAME);

        List<Category> categories = experienceDTO.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId)))
                .collect(Collectors.toList());

        List<Property> properties = experienceDTO.getPropertyIds().stream()
                .map(propertyId -> propertyRepository.findById(propertyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId)))
                .collect(Collectors.toList());

        Experience experience = new Experience();
        experience.setTitle(experienceDTO.getTitle());
        experience.setCountry(experienceDTO.getCountry());
        experience.setUbication(experienceDTO.getUbication());
        experience.setDescription(experienceDTO.getDescription());
        experience.setImages(experienceDTO.getImages());
        experience.setQuantity(experienceDTO.getQuantity());
        experience.setTimeUnit(experienceDTO.getTimeUnit());
        experience.setCategories(categories);
        experience.setProperties(properties);
        experience.setReputation(getRandomReputation());
        experience.setServiceHours(experienceDTO.getServiceHours());
        experience.setAvailableDays(experienceDTO.getAvailableDays());

        return experienceRepository.save(experience);
    }

    @Override
    public Experience updateExperience(Long id, ExperienceDTO updatedExperienceDTO) {
        Experience existingExperience = experienceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found with id: " + id));

        if (!existingExperience.getTitle().equals(updatedExperienceDTO.getTitle()) &&
                experienceRepository.existsByTitle(updatedExperienceDTO.getTitle())) {
            throw new DuplicatedResourceException("An experience with title " + updatedExperienceDTO.getTitle() + " already exists");
        }

        validateNoDuplicates(updatedExperienceDTO.getCategoryIds(), CATEGORIES_FIELD_NAME);
        validateNoDuplicates(updatedExperienceDTO.getPropertyIds(), PROPERTIES_FIELD_NAME);

        List<Category> categories = updatedExperienceDTO.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId)))
                .collect(Collectors.toList());

        List<Property> properties = updatedExperienceDTO.getPropertyIds().stream()
                .map(propertyId -> propertyRepository.findById(propertyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + propertyId)))
                .collect(Collectors.toList());

        existingExperience.setTitle(updatedExperienceDTO.getTitle());
        existingExperience.setCountry(updatedExperienceDTO.getCountry());
        existingExperience.setUbication(updatedExperienceDTO.getUbication());
        existingExperience.setDescription(updatedExperienceDTO.getDescription());
        existingExperience.setImages(updatedExperienceDTO.getImages());
        existingExperience.setQuantity(updatedExperienceDTO.getQuantity());
        existingExperience.setTimeUnit(updatedExperienceDTO.getTimeUnit());
        existingExperience.setCategories(categories);
        existingExperience.setProperties(properties);
        existingExperience.setServiceHours(updatedExperienceDTO.getServiceHours());
        existingExperience.setAvailableDays(updatedExperienceDTO.getAvailableDays());

        return experienceRepository.save(existingExperience);
    }

    @Override
    public void deleteExperience(Long id) {
        Optional<Experience> existingExperience = experienceRepository.findById(id);

        if (existingExperience.isEmpty()) {
            throw new ResourceNotFoundException("The Experience for id: " + id + " was not found.");
        }
        experienceRepository.deleteById(id);
    }

    private void validateNoDuplicates(List<Long> ids, String fieldName) {
        Set<Long> uniqueIds = new HashSet<>(ids);
        if (uniqueIds.size() < ids.size()) {
            throw new DuplicatedResourceException("Duplicated " + fieldName + " are not allowed.");
        }
    }

    private double getRandomReputation() {
        double randomValue = ThreadLocalRandom.current().nextDouble(1.0, 5.0);
        return Math.round(randomValue * 10.0) / 10.0;
    }

    private boolean isAvailable(Long experienceId, LocalDateTime startDate, LocalDateTime endDate) {
        List<ReservationDatesDTO> reservationDates = reservationService.getReservationsByExperience(experienceId);

        for (ReservationDatesDTO reservation : reservationDates) {
            if (!(endDate.isBefore(reservation.getCheckIn()) || startDate.isAfter(reservation.getCheckOut()))) {
                return false;
            }
        }
        return true;
    }

    private String capitalizeEachWord(String country) {
        return Arrays.stream(country.split(" "))
                .filter(word -> !word.isEmpty())
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
