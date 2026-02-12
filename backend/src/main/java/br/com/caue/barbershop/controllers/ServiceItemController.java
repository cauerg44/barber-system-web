package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.response.BarberResponseDTO;
import br.com.caue.barbershop.dto.response.ServiceItemResponseDTO;
import br.com.caue.barbershop.services.ServiceItemService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/services-items")
public class ServiceItemController {

    private final ServiceItemService service;

    public ServiceItemController(ServiceItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServiceItemResponseDTO>> allServices() {
        var list = service.findAll(Sort.by("name"));
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceItemResponseDTO> findService(@PathVariable Long id) {
        var barber = service.findById(id);
        return ResponseEntity.ok(barber);
    }
}