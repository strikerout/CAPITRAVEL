package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.ExperienceDTO;
import com.capitravel.Capitravel.model.Experience;
import com.capitravel.Capitravel.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/experiences")
@Validated
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        Experience experience = experienceService.getExperienceById(id);
        return experience != null ? ResponseEntity.ok(experience) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Experience> getExperiences(
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        LocalDateTime startDateTime = parseToLocalDateTime(startDate, true);
        LocalDateTime endDateTime = parseToLocalDateTime(endDate, false);

        if (categoryIds != null && !categoryIds.isEmpty()) {
            return experienceService.getExperiencesByCategories(categoryIds);
        } else if (keywords != null || country != null || (startDate != null && endDate != null)) {
            return experienceService.searchExperiences(keywords, country, startDateTime, endDateTime);
        } else {
            return experienceService.getAllExperiences();
        }
    }

    @PostMapping
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody ExperienceDTO experienceDTO) {
        Experience createdExperience = experienceService.createExperience(experienceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExperience);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @Valid @RequestBody ExperienceDTO updatedExperience) {
        Experience experience = experienceService.updateExperience(id, updatedExperience);
        return experience != null ? ResponseEntity.ok(experience) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }

    private LocalDateTime parseToLocalDateTime(String dateStr, boolean isStart) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return LocalDateTime.parse(dateStr, dateTimeFormatter);
        } catch (Exception e) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime date = LocalDate.parse(dateStr, dateFormatter).atStartOfDay();
            return isStart ? date : date.plusHours(23).plusMinutes(59);
        }
    }

}
