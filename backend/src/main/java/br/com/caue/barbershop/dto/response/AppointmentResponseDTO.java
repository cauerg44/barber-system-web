package br.com.caue.barbershop.dto.response;

import br.com.caue.barbershop.entity.enums.AppointmentStatus;
import br.com.caue.barbershop.entity.enums.AppointmentType;

import java.time.LocalDateTime;
import java.util.Set;

public record AppointmentResponseDTO(
        Long id,
        BarberResponseDTO barber,
        ClientResponseDTO client,
        LocalDateTime date,
        AppointmentStatus status,
        AppointmentType type,
        Set<ServiceItemResponseDTO> services
) {
}