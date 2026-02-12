package br.com.caue.barbershop.dto.response;

import java.math.BigDecimal;

public record ServiceItemResponseDTO(
        Long id,
        String name,
        BigDecimal basePrice
) {
}