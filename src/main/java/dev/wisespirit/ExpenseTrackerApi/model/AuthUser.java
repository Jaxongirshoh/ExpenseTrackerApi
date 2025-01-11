package dev.wisespirit.ExpenseTrackerApi.model;

import dev.wisespirit.ExpenseTrackerApi.model.enums.AuthRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor*/
@Entity
@Table(name = "auth_users")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthRole getAuthRole() {
        return authRole;
    }

    public void setAuthRole(AuthRole authRole) {
        this.authRole = authRole;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }
}
