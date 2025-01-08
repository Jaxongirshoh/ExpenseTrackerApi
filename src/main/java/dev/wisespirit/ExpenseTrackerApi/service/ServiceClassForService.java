package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.ServiceDto;
import dev.wisespirit.ExpenseTrackerApi.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceClassForService {
    private final ServiceRepository serviceRepository;

    public ServiceClassForService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Optional<dev.wisespirit.ExpenseTrackerApi.model.Service> createService(ServiceDto serviceDto) {
        var saved = serviceRepository.save(new dev.wisespirit.ExpenseTrackerApi.model.Service(serviceDto.name()));
        return Optional.of(saved);
    }

    public Optional<dev.wisespirit.ExpenseTrackerApi.model.Service> updateService(ServiceDto serviceDto, Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setName(serviceDto.name());
                    return new dev.wisespirit.ExpenseTrackerApi.model.Service(serviceRepository.save(service).getName());
                });
    }

    public Optional<dev.wisespirit.ExpenseTrackerApi.model.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return serviceRepository.existsById(id);
    }


}
