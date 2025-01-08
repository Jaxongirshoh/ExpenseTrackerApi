package dev.wisespirit.ExpenseTrackerApi.repository;

import dev.wisespirit.ExpenseTrackerApi.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client,Long> {
}
