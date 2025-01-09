package dev.wisespirit.ExpenseTrackerApi.repository;

import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import dev.wisespirit.ExpenseTrackerApi.model.enums.TransactionType;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByTypeAndDateBetween(TransactionType type,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate);
}
