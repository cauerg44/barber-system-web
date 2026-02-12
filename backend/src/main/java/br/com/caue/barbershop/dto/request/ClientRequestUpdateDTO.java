package br.com.caue.barbershop.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequestUpdateDTO(
        @Size(min = 10, max = 60)
        String name,

        @Pattern(
                regexp = "\\d{10,11}",
                message = "Phone must contain 10 or 11 digits"
        )
        String phone
) {
}