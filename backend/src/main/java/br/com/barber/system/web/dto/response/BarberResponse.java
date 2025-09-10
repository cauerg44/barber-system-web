package br.com.barber.system.web.dto.response;

import br.com.barber.system.web.entity.BarberEntity;

public record BarberResponse(
        Long id,
        String name
) {
    public BarberResponse(BarberEntity entity) {
        this(entity.getId(), entity.getName());
    }
}