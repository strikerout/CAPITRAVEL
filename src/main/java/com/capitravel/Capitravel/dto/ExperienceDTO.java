package com.capitravel.Capitravel.dto;

import com.capitravel.Capitravel.util.TrimmingStringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ExperienceDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 32, message = "Title must be between 3 and 32 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String title;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 32, message = "Country must be between 2 and 32 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String country;

    @NotBlank(message = "Ubication is required")
    @Size(min = 2, max = 128, message = "Ubication must be between 2 and 128 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String ubication;

    @NotBlank(message = "Description is required")
    @Size(min = 32, max = 512, message = "Description must be between 32 and 512 characters")
    @JsonDeserialize(using = TrimmingStringDeserializer.class)
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @NotEmpty(message = "Images cannot be empty")
    private List<@NotBlank(message = "Images cannot be blank") String> images;

    @NotEmpty(message = "Categories are required")
    private List<Long> categoryIds;

    private List<Long> propertyIds;
}
