package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.request.ClientRequestSaveDTO;
import br.com.caue.barbershop.dto.request.ClientRequestUpdateDTO;
import br.com.caue.barbershop.dto.response.ClientResponseDTO;
import br.com.caue.barbershop.entity.Client;
import br.com.caue.barbershop.repository.ClientRepository;
import br.com.caue.barbershop.services.exceptions.DatabaseException;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClientResponseDTO> findAll() {
        List<Client> list = repository.findAll(Sort.by("name"));
        return list.stream()
                .map(client -> new ClientResponseDTO(client.getId(), client.getName(), client.getPhone()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return new ClientResponseDTO(client.getId(), client.getName(), client.getPhone());
    }

    @Transactional
    public ClientResponseDTO save(ClientRequestSaveDTO request) {
        Client entity = new Client();
        saveDtoToEntity(request, entity);
        entity = repository.save(entity);
        return new ClientResponseDTO(entity.getId(), entity.getName(), entity.getPhone());
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestUpdateDTO request) {
        try {
            Client entity = repository.getReferenceById(id);
            updateDtoToEntity(request, entity);
            entity = repository.save(entity);
            return new ClientResponseDTO(entity.getId(), entity.getName(), entity.getPhone());
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found.");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity reference fail");
        }
    }

    public void saveDtoToEntity(ClientRequestSaveDTO dto, Client entity) {
        entity.setName(dto.name());
        entity.setPhone(dto.phone());
    }

    public void updateDtoToEntity(ClientRequestUpdateDTO dto, Client entity) {
        if (dto.name() != null && !dto.name().isBlank()) {
            entity.setName(dto.name());
        }
        if (dto.phone() != null && !dto.phone().isBlank()) {
            entity.setPhone(dto.phone());
        }
    }

    protected Client getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

}