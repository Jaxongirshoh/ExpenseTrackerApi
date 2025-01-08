package dev.wisespirit.ExpenseTrackerApi.dto;

import dev.wisespirit.ExpenseTrackerApi.model.enums.AuthRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record AuthUserUpdateDto(@NotNull
                                String name,
                                @Column(unique = true)
                                String username,
                                @NotNull
                                String password,
                                AuthRole authRole) {
}
