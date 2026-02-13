package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.response.BarberResponseDTO;
import br.com.caue.barbershop.entity.Barber;
import br.com.caue.barbershop.repository.BarberRepository;
import br.com.caue.barbershop.services.exceptions.BusinessException;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarberService {

    private final BarberRepository repository;

    public BarberService(BarberRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BarberResponseDTO> findAll(Sort name) {
        List<Barber> list = repository.findAll();
        return list.stream()
                .map(Barber -> new BarberResponseDTO(Barber.getId(), Barber.getName(), Barber.getActive()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BarberResponseDTO findById(Long id) {
        Barber barber = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));
        return new BarberResponseDTO(barber.getId(), barber.getName(), barber.getActive());
    }

    @Transactional
    public BarberResponseDTO activate(Long id) {
        try {
            Barber entity = repository.getReferenceById(id);
            entity.activate();
            return new BarberResponseDTO(entity.getId(), entity.getName(), entity.getActive());
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    @Transactional
    public BarberResponseDTO disable(Long id) {
        try {
            Barber entity = repository.getReferenceById(id);
            entity.deactivate();
            return new BarberResponseDTO(entity.getId(), entity.getName(), entity.getActive());
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    protected Barber getEntityById(Long id) {
        Barber barber = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        if (!barber.getActive()) {
            throw new BusinessException("Barber is inactive and cannot receive appointments");
        }

        return barber;
    }
}