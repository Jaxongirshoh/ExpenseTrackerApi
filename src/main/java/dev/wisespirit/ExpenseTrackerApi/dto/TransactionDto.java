package dev.wisespirit.ExpenseTrackerApi.dto;

import dev.wisespirit.ExpenseTrackerApi.model.enums.MoneyType;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionStatus;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(Long id,
                             @NotNull
                             BigDecimal amount,
                             LocalDate date,
                             TransactionType type,
                             TransactionStatus status,
                             MoneyType moneyType,
                             @NotNull
                             Long clientId,
                             @NotNull
                             Long serviceId,
                             String note,
                             String fileUrl) {
}
