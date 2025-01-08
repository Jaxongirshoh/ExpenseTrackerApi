package dev.wisespirit.ExpenseTrackerApi.dto;

import dev.wisespirit.ExpenseTrackerApi.model.enums.MoneyType;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionStatus;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionCreatDto(BigDecimal amount,
                                  LocalDate date,
                                  TransactionType type,
                                  TransactionStatus status,
                                  MoneyType moneyType,
                                  Long clientId,
                                  Long serviceId,
                                  String note,
                                  String fileUrl
) {
}
