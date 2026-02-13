package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.response.ServiceItemResponseDTO;
import br.com.caue.barbershop.entity.ServiceItem;
import br.com.caue.barbershop.repository.ServiceItemRepository;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceItemService {

    private final ServiceItemRepository repository;

    public ServiceItemService(ServiceItemRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ServiceItemResponseDTO> findAll(Sort name) {
        List<ServiceItem> list = repository.findAll(Sort.by("name"));
        return list.stream()
                .map(serviceItem -> new ServiceItemResponseDTO(serviceItem.getId(), serviceItem.getName(), serviceItem.getBasePrice()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceItemResponseDTO findById(Long id) {
        ServiceItem serviceItem = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return new ServiceItemResponseDTO(serviceItem.getId(), serviceItem.getName(), serviceItem.getBasePrice());
    }

    @Transactional(readOnly = true)
    protected List<ServiceItem> findAllByIds(List<Long> ids) {
        List<ServiceItem> services = repository.findAllById(ids);

        if (services.size() != ids.size()) {
            throw new ResourceNotFoundException("One or more services not found");
        }

        return services;
    }

}