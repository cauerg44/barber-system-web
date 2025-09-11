package br.com.barber.system.web.controller;

import br.com.barber.system.web.dto.response.PaymentResponse;
import br.com.barber.system.web.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPaymentsMethod() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentResponse> getPaymentMethodById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }
}