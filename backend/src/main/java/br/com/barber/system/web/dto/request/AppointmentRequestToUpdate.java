package br.com.barber.system.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record AppointmentRequestToUpdate(
        @NotBlank(message = "Nome do cliente não pode estar vazio")
        @Size(max = 100, message = "Nome do cliente não pode ter mais que 100 caracteres")
        String clientName,

        @NotNull(message = "Deve ter um barbeiro") Long barberId,
        @NotNull(message = "Deve ter uma data para o atendimento") LocalDate appointmentDate,
        @NotEmpty(message = "Selecione pelo menos um serviço") Set<Long> servicesIds,
        @NotNull(message = "Forma de pagamento é obrigatória") Long paymentId
) {}