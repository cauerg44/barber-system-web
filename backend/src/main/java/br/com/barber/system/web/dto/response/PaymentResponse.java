package br.com.barber.system.web.dto.response;

import br.com.barber.system.web.entity.PaymentEntity;

public record PaymentResponse(
        Long id,
        String method
) {
    public PaymentResponse(PaymentEntity entity) {
        this(entity.getId(), entity.getMethod());
    }
}