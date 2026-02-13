package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.response.AppointmentResponseDTO;
import br.com.caue.barbershop.entity.Appointment;
import br.com.caue.barbershop.mapper.AppointmentMapper;
import br.com.caue.barbershop.repository.AppointmentRepository;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> findAll() {
        List<Appointment> list = repository.findAll(Sort.by("id"));
        return list.stream()
                .map(AppointmentMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDTO findById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return AppointmentMapper.toDTO(appointment);
    }
}