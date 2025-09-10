package br.com.barber.system.web.dto.response;

import br.com.barber.system.web.entity.ServiceItemEntity;

import java.math.BigDecimal;

public record ServiceItemResponse(
        Long id,
        String name,
        BigDecimal price
) {
    public ServiceItemResponse(ServiceItemEntity entity) {
        this(entity.getId(), entity.getName(), entity.getPrice());
    }
}
