package dev.wisespirit.ExpenseTrackerApi.dto;

import jakarta.validation.constraints.NotNull;

public record ExpenseCategory(Long id, @NotNull String name) {
}
