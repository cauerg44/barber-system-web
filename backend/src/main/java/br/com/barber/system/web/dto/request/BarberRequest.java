package br.com.barber.system.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BarberRequest(
        @NotBlank(message = "Campo não pode estar vazio") String name
) {
}