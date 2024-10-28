package com.capitravel.Capitravel.repository;

import com.capitravel.Capitravel.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    boolean existsByTitle(String title);
}