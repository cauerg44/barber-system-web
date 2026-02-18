package br.com.caue.barbershop.controllers;

import br.com.caue.barbershop.dto.response.DailyTotalDTO;
import br.com.caue.barbershop.dto.response.PaymentTotalDTO;
import br.com.caue.barbershop.entity.enums.Payment;
import br.com.caue.barbershop.services.FinancialReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class FinancialReportController {

    private final FinancialReportService service;

    public FinancialReportController(FinancialReportService service) {
        this.service = service;
    }

    @GetMapping("/daily")
    public DailyTotalDTO totalByDate(@RequestParam LocalDate date) {
        return service.totalByDate(date);
    }

    @GetMapping("/payment")
    public PaymentTotalDTO totalByPayment(@RequestParam Payment payment, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.totalByPayment(payment, date);
    }
}