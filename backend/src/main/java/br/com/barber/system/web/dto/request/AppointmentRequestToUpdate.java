package br.com.barber.system.web.dto.request;

import br.com.barber.system.web.entity.enums.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record AppointmentRequestToUpdate(
        @NotBlank(message = "Nome do cliente não pode estar vazio")
        @Size(max = 100, message = "Nome do cliente não pode ter mais que 100 caracteres")
        String clientName,

        @NotNull(message = "Barbeiro deve ser informado")
        @Positive(message = "Barbeiro inválido")
        Long barberId,

        @NotNull(message = "Método de pagamento deve ser informado")
        @Positive(message = "Pagamento inválido")
        Long paymentId,

        @NotNull(message = "Deve informar ao menos um serviço")
        Set<@Positive(message = "Serviço inválido") Long> servicesIds,

        @NotNull(message = "Status do atendimento deve ser informado")
        AppointmentStatus status
) {}