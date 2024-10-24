package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.model.Experience;

import java.util.List;

public interface ExperienceService {

    List<Experience> getAllExperiences();

    Experience getExperienceById(Long id);

    Experience createExperience(Experience experience);

    Experience updateExperience(Long id, Experience updatedExperience);

    void deleteExperience(Long id);
}
