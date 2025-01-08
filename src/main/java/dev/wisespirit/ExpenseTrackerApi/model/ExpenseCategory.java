package dev.wisespirit.ExpenseTrackerApi.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "expense_categories")
public class ExpenseCategory extends BaseModel{
    @NotNull
    private String name;
}
