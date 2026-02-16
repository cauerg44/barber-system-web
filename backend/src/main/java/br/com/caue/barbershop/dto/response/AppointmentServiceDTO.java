package br.com.caue.barbershop.dto.response;

import java.math.BigDecimal;

public record AppointmentServiceDTO(
        Long id,
        String name,
        BigDecimal price
) {
}