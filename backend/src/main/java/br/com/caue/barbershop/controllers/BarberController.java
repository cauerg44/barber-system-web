package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.response.BarberResponseDTO;
import br.com.caue.barbershop.services.BarberService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/barbers")
public class BarberController {

    private final BarberService service;

    public BarberController(BarberService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> allBarbers() {
        var list = service.findAll(Sort.by("name"));
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BarberResponseDTO> findBarber(@PathVariable Long id) {
        var barber = service.findById(id);
        return ResponseEntity.ok(barber);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<BarberResponseDTO> activateBarber(@PathVariable Long id) {
        var barberActivated = service.activate(id);
        return ResponseEntity.ok(barberActivated);
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<BarberResponseDTO> disableBarber(@PathVariable Long id) {
        var barberDeactivated = service.disable(id);
        return ResponseEntity.ok(barberDeactivated);
    }

}