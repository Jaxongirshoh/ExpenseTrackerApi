package dev.wisespirit.ExpenseTrackerApi.repository;

import dev.wisespirit.ExpenseTrackerApi.model.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service, Long> {
}
