package br.com.caue.barbershop.mapper;

import br.com.caue.barbershop.dto.response.BarberResponseDTO;
import br.com.caue.barbershop.entity.Barber;

public class BarberMapper {

    private BarberMapper() {
    }

    public static BarberResponseDTO toDTO(Barber entity) {
        return new BarberResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getActive()
        );
    }
}
