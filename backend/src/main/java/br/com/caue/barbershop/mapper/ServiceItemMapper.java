package br.com.caue.barbershop.mapper;

import br.com.caue.barbershop.dto.response.ServiceItemResponseDTO;
import br.com.caue.barbershop.entity.ServiceItem;

public class ServiceItemMapper {

    private ServiceItemMapper() {
    }

    public static ServiceItemResponseDTO toDTO(ServiceItem entity) {
        return new ServiceItemResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getBasePrice()
        );
    }
}
