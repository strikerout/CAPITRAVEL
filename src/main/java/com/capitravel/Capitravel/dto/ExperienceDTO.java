package com.capitravel.Capitravel.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ExperienceDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country name cannot exceed 50 characters")
    private String country;

    @NotBlank(message = "Ubication is required")
    @Size(max = 100, message = "Ubication cannot exceed 100 characters")
    private String ubication;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @NotEmpty(message = "Images cannot be empty")
    private List<@NotBlank(message = "Image URL cannot be blank") String> images;

    @NotEmpty(message = "Categories are required")
    private List<Long> categoryIds;

    private List<Long> propertyIds;
}