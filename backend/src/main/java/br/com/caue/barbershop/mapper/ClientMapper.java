package br.com.caue.barbershop.mapper;

import br.com.caue.barbershop.dto.response.ClientResponseDTO;
import br.com.caue.barbershop.entity.Client;

public class ClientMapper {

    private ClientMapper() {
    }

    public static ClientResponseDTO toDTO(Client entity) {
        return new ClientResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getPhone()
        );
    }
}
