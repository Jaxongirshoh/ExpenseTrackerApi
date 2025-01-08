package dev.wisespirit.ExpenseTrackerApi.repository;

import dev.wisespirit.ExpenseTrackerApi.model.ExpenseCategory;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseCategoryRepository extends CrudRepository<ExpenseCategory, Long> {
}
