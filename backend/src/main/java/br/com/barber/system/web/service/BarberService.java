package br.com.barber.system.web.service;

import br.com.barber.system.web.dto.request.BarberRequest;
import br.com.barber.system.web.dto.response.BarberResponse;
import br.com.barber.system.web.entity.BarberEntity;
import br.com.barber.system.web.repository.BarberRepository;
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
public class BarberService {

    private final BarberRepository repository;

    public BarberService(BarberRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BarberResponse> findAll() {
        List<BarberEntity> list = repository.findAll();
        return list.stream()
                .map(it -> new BarberResponse(it.getId(), it.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BarberResponse findById(Long id) {
        BarberEntity barberEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado"));
        return new BarberResponse(barberEntity);
    }

    @Transactional
    public BarberResponse save(BarberRequest request) {
        BarberEntity entity = new BarberEntity();
        requestToCreate(request, entity);
        entity = repository.save(entity);
        return new BarberResponse(entity);
    }

    @Transactional
    public BarberResponse update(Long id, BarberRequest request) {
        try {
            BarberEntity entity = repository.getReferenceById(id);
            requestToUpdate(request, entity);
            entity = repository.save(entity);
            return new BarberResponse(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Barbeiro não encontrado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Barbeiro não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void requestToCreate(BarberRequest request, BarberEntity entity) {
        entity.setName(request.name());
    }

    private void requestToUpdate(BarberRequest request, BarberEntity entity) {
        Optional.ofNullable(request.name()).ifPresent(entity::setName);
    }
}