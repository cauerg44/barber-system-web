package br.com.caue.barbershop.dto.response;

import br.com.caue.barbershop.entity.enums.AppointmentType;

import java.math.BigDecimal;

public record AppointmentSummaryDTO(
        Long id,
        String barberName,
        String clientName,
        AppointmentType type,
        BigDecimal subTotal
) {
}