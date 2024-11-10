package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.dto.ExperienceDTO;
import com.capitravel.Capitravel.model.Experience;

import java.util.List;

public interface ExperienceService {

    List<Experience> getAllExperiences();

    Experience getExperienceById(Long id);

    List<Experience> getExperiencesByCategories(Long categoryId);

    Experience createExperience(ExperienceDTO experience);

    Experience updateExperience(Long id, ExperienceDTO updatedExperience);

    void deleteExperience(Long id);
}
