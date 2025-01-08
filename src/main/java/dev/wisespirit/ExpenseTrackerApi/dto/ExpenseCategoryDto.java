package dev.wisespirit.ExpenseTrackerApi.dto;

import jakarta.validation.constraints.NotNull;

public record ExpenseCategoryDto(@NotNull String name) {
}
