package br.com.barber.system.web.controller;

import br.com.barber.system.web.dto.request.AppointmentRequestToCreate;
import br.com.barber.system.web.dto.request.AppointmentRequestToUpdate;
import br.com.barber.system.web.dto.response.AppointmentResponse;
import br.com.barber.system.web.entity.enums.AppointmentStatus;
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

    @GetMapping("/scheduled")
    public ResponseEntity<List<AppointmentResponse>> getScheduled() {
        return ResponseEntity.ok(service.findByStatus(AppointmentStatus.AGENDADO));
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<AppointmentResponse>> getWaiting() {
        return ResponseEntity.ok(service.findByStatus(AppointmentStatus.AGUARDANDO));
    }

    @GetMapping("/in-attendence")
    public ResponseEntity<List<AppointmentResponse>> getInAttendence() {
        return ResponseEntity.ok(service.findByStatus(AppointmentStatus.EM_ATENDIMENTO));
    }

    @GetMapping("/finished")
    public ResponseEntity<List<AppointmentResponse>> getFinished() {
        return ResponseEntity.ok(service.findByStatus(AppointmentStatus.FINALIZADO));
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestToUpdate request) {
        AppointmentResponse response = service.updateAppointment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}