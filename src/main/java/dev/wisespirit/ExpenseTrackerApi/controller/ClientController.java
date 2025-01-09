package dev.wisespirit.ExpenseTrackerApi.controller;

import dev.wisespirit.ExpenseTrackerApi.dto.ClientDto;
import dev.wisespirit.ExpenseTrackerApi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto dto){
        return clientService.createClient(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().body(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient( @Valid @RequestBody ClientDto dto,
                                                   @PathVariable Long id){
        return clientService.updateClient(dto, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().body(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id){
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable Long id){
        if (clientService.existById(id)){
            clientService.deleteClient(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(){
        return clientService.getALlClients()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
