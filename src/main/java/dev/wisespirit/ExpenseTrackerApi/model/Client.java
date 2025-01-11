package dev.wisespirit.ExpenseTrackerApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor*/
@Entity(name = "clients")
public class Client extends BaseModel {
    @NotNull
    private String fullName;
    @NotNull
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    private Long serviceId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
