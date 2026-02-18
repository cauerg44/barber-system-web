package br.com.caue.barbershop.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyTotalDTO(
        LocalDate date,
        BigDecimal total
) {
}