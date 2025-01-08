package dev.wisespirit.ExpenseTrackerApi.model;

import dev.wisespirit.ExpenseTrackerApi.model.enums.AuthRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "auth_users")
public class AuthUser extends BaseModel {
    @NotNull
    private String name;
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthRole authRole;
    @NotNull
    private String telegramId;
}
