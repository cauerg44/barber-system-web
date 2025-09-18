package br.com.barber.system.web.dto.response;

import br.com.barber.system.web.entity.AppointmentEntity;
import br.com.barber.system.web.entity.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public record AppointmentResponse(
        Long id,
        String clientName,
        BarberResponse barber,
        AppointmentStatus status,
        LocalDate appointmentDate,
        Set<ServiceItemResponse> services,
        PaymentResponse payment,
        BigDecimal totalPrice
) {
    public AppointmentResponse(AppointmentEntity entity) {
        this(
                entity.getId(),
                entity.getClientName(),
                new BarberResponse(entity.getBarber().getId(), entity.getBarber().getName()),
                entity.getStatus(),
                entity.getAppointmentDate(),
                entity.getServices().stream()
                        .map(s -> new ServiceItemResponse(s.getId(), s.getName(), s.getPrice()))
                        .collect(Collectors.toSet()),
                new PaymentResponse(entity.getPayment().getId(), entity.getPayment().getMethod()),
                entity.getTotalPrice()
        );
    }
}