package dev.wisespirit.ExpenseTrackerApi.repository;

import dev.wisespirit.ExpenseTrackerApi.model.AuthUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthUserRepository extends CrudRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}
