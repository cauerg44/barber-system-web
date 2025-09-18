package br.com.barber.system.web.controller;

import br.com.barber.system.web.dto.request.AppointmentRequestToCreate;
import br.com.barber.system.web.dto.response.AppointmentResponse;
import br.com.barber.system.web.service.AppointmentService;
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
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AppointmentResponse> registerNewAppointment(@RequestBody AppointmentRequestToCreate request) {
        AppointmentResponse response = service.saveNew(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping(value = "/schedule")
    public ResponseEntity<AppointmentResponse> scheduleAppointment(@RequestBody AppointmentRequestToCreate request) {
        AppointmentResponse response = service.schedule(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}