package dev.wisespirit.ExpenseTrackerApi.dto;

import dev.wisespirit.ExpenseTrackerApi.model.enums.AuthRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record AuthUserCreateDto(@NotNull
                                String name,
                                @Column(unique = true)
                                String username,
                                @NotNull
                                String password,
                                AuthRole authRole,
                                @NotNull
                                String telegramId) {
}
