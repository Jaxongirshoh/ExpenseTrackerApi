package dev.wisespirit.ExpenseTrackerApi.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor*/
@Entity(name = "services")
public class Service extends BaseModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service(String name) {
        this.name = name;
    }
}
