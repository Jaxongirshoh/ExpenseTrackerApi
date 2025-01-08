package dev.wisespirit.ExpenseTrackerApi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record ServiceCreateDto(@NotNull
                               String fullName,
                               @NotNull
                               @Column(unique = true)
                               String phoneNumber,
                               @NotNull
                               Long serviceId) {
}
