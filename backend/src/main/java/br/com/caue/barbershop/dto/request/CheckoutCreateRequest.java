package br.com.caue.barbershop.dto.request;

import br.com.caue.barbershop.entity.enums.Payment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CheckoutCreateRequest(
        @NotNull(message = "Appointment id is required")
        Long appointmentId,

        @PositiveOrZero(message = "Discount must be zero or positive")
        BigDecimal discount,

        @NotNull(message = "Payment method is required")
        Payment payment
) {
}