package br.com.caue.barbershop.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AppointmentWalkInRequest(
        @NotNull(message = "Barber id is required")
        Long barberId,

        @NotNull(message = "Client id is required")
        Long clientId,

        @NotEmpty(message = "At least one service is required")
        List<Long> serviceIds
) {
}