package br.com.barber.system.web.service;

import br.com.barber.system.web.dto.request.ServiceItemRequest;
import br.com.barber.system.web.dto.response.ServiceItemResponse;
import br.com.barber.system.web.entity.ServiceItemEntity;
import br.com.barber.system.web.repository.ServiceItemRepository;
import br.com.barber.system.web.service.exceptions.DatabaseException;
import br.com.barber.system.web.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceItemService {

    private final ServiceItemRepository repository;

    public ServiceItemService(ServiceItemRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ServiceItemResponse> findAll() {
        List<ServiceItemEntity> list = repository.findAll();
        return list.stream()
                .map(it -> new ServiceItemResponse(it.getId(), it.getName(), it.getPrice()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceItemResponse findById(Long id) {
        ServiceItemEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço do salão não encontrado"));
        return new ServiceItemResponse(entity);
    }

    @Transactional
    public ServiceItemResponse save(ServiceItemRequest request) {
        ServiceItemEntity entity = new ServiceItemEntity();
        requestToCreate(request, entity);
        entity = repository.save(entity);
        return new ServiceItemResponse(entity);
    }

    @Transactional
    public ServiceItemResponse update(Long id, ServiceItemRequest request) {
        try {
            ServiceItemEntity entity = repository.getReferenceById(id);
            requestToUpdate(request, entity);
            entity = repository.save(entity);
            return new ServiceItemResponse(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Serviço da barbearia não encontrado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Serviço da barbearia não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void requestToCreate(ServiceItemRequest request, ServiceItemEntity entity) {
        entity.setName(request.name());
        entity.setPrice(request.price());
    }

    private void requestToUpdate(ServiceItemRequest request, ServiceItemEntity entity) {
        Optional.ofNullable(request.name()).ifPresent(entity::setName);
        Optional.ofNullable(request.price()).ifPresent(entity::setPrice);
    }
}