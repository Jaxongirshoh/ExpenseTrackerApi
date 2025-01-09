package dev.wisespirit.ExpenseTrackerApi.controller;

import dev.wisespirit.ExpenseTrackerApi.dto.ServiceDto;
import dev.wisespirit.ExpenseTrackerApi.model.Service;
import dev.wisespirit.ExpenseTrackerApi.service.ServiceTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceTypeService service;

    public ServiceController(ServiceTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Service> createService(@Valid @RequestBody ServiceDto dto) {
        return service.createService(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@Valid @RequestBody ServiceDto dto,
                                                 @PathVariable Long id) {
        return service.updateService(dto, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
