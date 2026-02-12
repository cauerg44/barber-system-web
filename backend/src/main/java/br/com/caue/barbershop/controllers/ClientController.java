package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.request.ClientRequestSaveDTO;
import br.com.caue.barbershop.dto.request.ClientRequestUpdateDTO;
import br.com.caue.barbershop.dto.response.ClientResponseDTO;
import br.com.caue.barbershop.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> allClients() {
        var list = service.findAll(Sort.by("name"));
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientResponseDTO> findClient(@PathVariable Long id) {
        var client = service.findById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody @Valid ClientRequestSaveDTO request) {
        var newClient = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newClient.id()).toUri();
        return ResponseEntity.created(uri).body(newClient);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable Long id, @RequestBody @Valid ClientRequestUpdateDTO request) {
        var clientUpdated = service.update(id, request);
        return ResponseEntity.ok(clientUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}