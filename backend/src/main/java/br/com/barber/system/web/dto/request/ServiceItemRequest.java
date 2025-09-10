package br.com.barber.system.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceItemRequest(
        @NotBlank(message = "Campo não pode estar vazio") String name,

        @NotNull
        @Positive(message = "Preço deve ser positivo")
        BigDecimal price
) {
}
