package dev.wisespirit.ExpenseTrackerApi.model;


import dev.wisespirit.ExpenseTrackerApi.model.enums.MoneyType;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionStatus;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction extends BaseModel {
    @NotNull
    private BigDecimal amount;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Enumerated(EnumType.STRING)
    private MoneyType moneyType;
    @NotNull
    private Long clientId;
    @NotNull
    private Long serviceId;
    private String note;
    private String fileUrl;
}
