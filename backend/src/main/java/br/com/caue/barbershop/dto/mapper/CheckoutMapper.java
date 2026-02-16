package br.com.caue.barbershop.dto.mapper;

import br.com.caue.barbershop.dto.response.AppointmentSummaryDTO;
import br.com.caue.barbershop.dto.response.CheckoutResponseDTO;
import br.com.caue.barbershop.entity.Checkout;

public final class CheckoutMapper {

    private CheckoutMapper() {}

    public static CheckoutResponseDTO toDTO(Checkout entity) {

        var appointment = entity.getAppointment();

        AppointmentSummaryDTO summary = new AppointmentSummaryDTO(
                appointment.getId(),
                appointment.getBarber().getName(),
                appointment.getClient().getName(),
                appointment.getType(),
                appointment.getSubTotal()
        );

        return new CheckoutResponseDTO(
                entity.getId(),
                summary,
                entity.getDiscount(),
                entity.getTotal(),
                entity.getPayment()
        );
    }
}
