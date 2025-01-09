package dev.wisespirit.ExpenseTrackerApi.service;

import dev.wisespirit.ExpenseTrackerApi.dto.ClientDto;
import dev.wisespirit.ExpenseTrackerApi.model.AuthUser;
import dev.wisespirit.ExpenseTrackerApi.model.Client;
import dev.wisespirit.ExpenseTrackerApi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<ClientDto> createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setFullName(clientDto.fullName());
        client.setPhoneNumber(clientDto.phoneNumber());
        client.setServiceId(clientDto.serviceId());
        Client saved = clientRepository.save(client);
        return Optional.of(convertEntityToDto(saved));
    }

    public Optional<ClientDto> updateClient(ClientDto clientDto, Long id) {
        return clientRepository.findById(id)
                .map(client->{
                   client.setFullName(clientDto.fullName());
                   client.setPhoneNumber(clientDto.phoneNumber());
                   client.setServiceId(clientDto.serviceId());
                   return convertEntityToDto(clientRepository.save(client));
                });
    }

    public Optional<ClientDto> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientService::convertEntityToDto);
    }

    public Optional<List<ClientDto>> getALlClients() {
        List<ClientDto> list = new ArrayList<>();
        clientRepository.findAll()
                .forEach(client -> list.add(convertEntityToDto(client)));
        return Optional.of(list);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public boolean existById(Long id) {
        return clientRepository.existsById(id);
    }

    public static ClientDto convertEntityToDto(Client client) {
        return new ClientDto(
                client.getFullName(),
                client.getPhoneNumber(),
                client.getServiceId());
    }


}
