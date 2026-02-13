package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.request.AppointmentScheduleRequest;
import br.com.caue.barbershop.dto.request.AppointmentWalkInRequest;
import br.com.caue.barbershop.dto.response.AppointmentResponseDTO;
import br.com.caue.barbershop.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping(value = "/schedule")
    public ResponseEntity<AppointmentResponseDTO> schedule(@RequestBody @Valid AppointmentScheduleRequest request) {
        var appointmentScheduled = service.schedule(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appointmentScheduled.id()).toUri();
        return ResponseEntity.created(uri).body(appointmentScheduled);
    }

    @PostMapping(value = "/in-walk")
    public ResponseEntity<AppointmentResponseDTO> createWalkIn(@RequestBody @Valid AppointmentWalkInRequest request) {
        var appointmentInWalk = service.createWalkIn(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appointmentInWalk.id()).toUri();
        return ResponseEntity.created(uri).body(appointmentInWalk);
    }
}