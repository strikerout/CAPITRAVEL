package com.capitravel.Capitravel.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    @NotNull(message = "Check-in date and time are required")
    @FutureOrPresent(message = "Check-in date and time must be in the future or present")
    private LocalDateTime checkIn;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Experience ID is required")
    private Long experienceId;
}
