package br.com.caue.barbershop.dto.response;

import java.math.BigDecimal;

public record PaymentTotalDTO(
        String payment,
        BigDecimal total
) {
}