package com.company.booking.model.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserRq (
        @NotNull(message = "Name cannot be empty!") String name,
        @NotNull(message = "Email cannot be empty!") String email) {
}