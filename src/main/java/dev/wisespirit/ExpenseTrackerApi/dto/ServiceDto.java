package dev.wisespirit.ExpenseTrackerApi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record ServiceDto(Long id,
                         @NotNull
                         String fullName,
                         @NotNull
                         @Column(unique = true)
                         String phoneNumber,
                         @NotNull
                         Long serviceId) {
}
