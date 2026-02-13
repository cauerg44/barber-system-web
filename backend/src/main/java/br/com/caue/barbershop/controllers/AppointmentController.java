package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.response.AppointmentResponseDTO;
import br.com.caue.barbershop.dto.response.ServiceItemResponseDTO;
import br.com.caue.barbershop.services.AppointmentService;
import br.com.caue.barbershop.services.ServiceItemService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> allAppointments() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponseDTO> findAppointment(@PathVariable Long id) {
        var appointment = service.findById(id);
        return ResponseEntity.ok(appointment);
    }
}