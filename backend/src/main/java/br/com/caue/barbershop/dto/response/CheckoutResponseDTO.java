package br.com.caue.barbershop.dto.response;

import br.com.caue.barbershop.entity.enums.Payment;

import java.math.BigDecimal;

public record CheckoutResponseDTO(
        Long id,
        AppointmentSummaryDTO appointment,
        BigDecimal discount,
        BigDecimal total,
        Payment payment
) {
}