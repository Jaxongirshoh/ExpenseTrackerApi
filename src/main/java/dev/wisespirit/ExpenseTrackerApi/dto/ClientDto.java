package dev.wisespirit.ExpenseTrackerApi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record ClientDto(Long id,
                         @NotNull
                         String fullName,
                         @NotNull
                         @Column(unique = true)
                         String phoneNumber,
                         @NotNull
                         Long serviceId) {
    public ClientDto(@NotNull String fullName, @NotNull String phoneNumber, @NotNull Long serviceId){
        this(null,fullName,phoneNumber,serviceId);
    }
}
