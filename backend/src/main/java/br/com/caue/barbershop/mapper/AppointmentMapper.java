package br.com.caue.barbershop.mapper;

import br.com.caue.barbershop.dto.response.AppointmentResponseDTO;
import br.com.caue.barbershop.entity.Appointment;

import java.util.stream.Collectors;

public class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static AppointmentResponseDTO toDTO(Appointment entity) {
        return new AppointmentResponseDTO(
                entity.getId(),
                BarberMapper.toDTO(entity.getBarber()),
                ClientMapper.toDTO(entity.getClient()),
                entity.getDate(),
                entity.getStatus(),
                entity.getType(),
                entity.getServices().stream()
                        .map(ServiceItemMapper::toDTO)
                        .collect(Collectors.toSet())
        );
    }
}
