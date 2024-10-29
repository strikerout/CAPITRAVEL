package com.capitravel.Capitravel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PropertyDTO {

    @NotBlank(message = "Name is requiered.")
    @Size(min= 3,max = 32, message = "Name must be between 3 and 32 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min= 15,max = 256, message = "Description must be between 15 and 256 characters")
    private String description;

    @NotBlank(message =  "Image is required")
    private String image;
}
