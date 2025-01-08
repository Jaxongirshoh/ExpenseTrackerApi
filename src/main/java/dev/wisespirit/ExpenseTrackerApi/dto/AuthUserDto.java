package dev.wisespirit.ExpenseTrackerApi.dto;

import dev.wisespirit.ExpenseTrackerApi.model.enums.AuthRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record AuthUserDto(@NotNull String name,
                          @Column(unique = true) String username,
                          AuthRole authRole,
                          @Column(unique = true) String telegramId) {
}
