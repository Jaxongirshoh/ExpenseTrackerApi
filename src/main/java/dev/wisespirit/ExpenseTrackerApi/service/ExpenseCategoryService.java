package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.ExpenseCategoryDto;
import dev.wisespirit.ExpenseTrackerApi.model.ExpenseCategory;
import dev.wisespirit.ExpenseTrackerApi.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public Optional<ExpenseCategory> create(ExpenseCategoryDto dto){
        return Optional.of(expenseCategoryRepository.save(new ExpenseCategory(dto.name())));
    }

    public void deleteById(Long id){
        expenseCategoryRepository.deleteById(id);
    }

    public Optional<ExpenseCategory> findById(Long id){
        return expenseCategoryRepository.findById(id);
    }



}
