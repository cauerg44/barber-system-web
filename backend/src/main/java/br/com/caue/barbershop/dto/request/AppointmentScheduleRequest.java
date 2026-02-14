package br.com.caue.barbershop.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record AppointmentScheduleRequest(

        @NotNull(message = "Barber id is required")
        Long barberId,

        @NotNull(message = "Client id is required")
        Long clientId,

        @NotNull(message = "Appointment date is required")
        @Future(message = "Appointment date must be in the future")
        LocalDateTime date,

        @NotEmpty(message = "At least one service must be selected")
        List<Long> serviceIds
) {
}