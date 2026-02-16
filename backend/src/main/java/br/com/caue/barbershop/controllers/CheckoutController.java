package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.request.CheckoutCreateRequest;
import br.com.caue.barbershop.dto.response.CheckoutResponseDTO;
import br.com.caue.barbershop.services.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/checkouts")
public class CheckoutController {

    private final CheckoutService service;

    public CheckoutController(CheckoutService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> create(@RequestBody @Valid CheckoutCreateRequest request) {

        var checkoutCreated = service.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(checkoutCreated.id())
                .toUri();

        return ResponseEntity.created(uri).body(checkoutCreated);
    }
}
