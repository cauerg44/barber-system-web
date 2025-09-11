package br.com.barber.system.web.service;

import br.com.barber.system.web.dto.response.PaymentResponse;
import br.com.barber.system.web.entity.PaymentEntity;
import br.com.barber.system.web.repository.PaymentRepository;
import br.com.barber.system.web.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        List<PaymentEntity> list = repository.findAll();
        return list.stream().map(it -> new PaymentResponse(it.getId(), it.getMethod()))
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        PaymentEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Método de pagamento não encontrado"));
        return new PaymentResponse(entity);
    }
}