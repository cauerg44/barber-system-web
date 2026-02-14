package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.request.AppointmentScheduleRequest;
import br.com.caue.barbershop.dto.request.AppointmentWalkInRequest;
import br.com.caue.barbershop.dto.response.AppointmentResponseDTO;
import br.com.caue.barbershop.entity.Appointment;
import br.com.caue.barbershop.entity.enums.AppointmentStatus;
import br.com.caue.barbershop.entity.enums.AppointmentType;
import br.com.caue.barbershop.mapper.AppointmentMapper;
import br.com.caue.barbershop.repository.AppointmentRepository;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final BarberService barberService;
    private final ClientService clientService;
    private final ServiceItemService serviceItemService;

    public AppointmentService(AppointmentRepository repository, BarberService barberService, ClientService clientService, ServiceItemService serviceItemService) {
        this.repository = repository;
        this.barberService = barberService;
        this.clientService = clientService;
        this.serviceItemService = serviceItemService;
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

    @Transactional
    public AppointmentResponseDTO schedule(AppointmentScheduleRequest request) {

        if (request.date().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment date must be in the future");
        }

        Appointment appointment = new Appointment(
                barberService.getEntityById(request.barberId()),
                clientService.getEntityById(request.clientId()),
                request.date(),
                AppointmentStatus.WAITING,
                AppointmentType.SCHEDULED
        );

        appointment.addServices(serviceItemService.findAllByIds(request.serviceIds()));

        repository.save(appointment);

        return AppointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentResponseDTO createWalkIn(AppointmentWalkInRequest request) {

        Appointment appointment = new Appointment(
                barberService.getEntityById(request.barberId()),
                clientService.getEntityById(request.clientId()),
                LocalDateTime.now(),
                AppointmentStatus.WAITING,
                AppointmentType.IN_WALK
        );

        appointment.addServices(serviceItemService.findAllByIds(request.serviceIds()));

        repository.save(appointment);

        return AppointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentResponseDTO start(Long id) {

        Appointment appointment = findAppointment(id);
        appointment.start();

        return AppointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentResponseDTO complete(Long id) {

        Appointment appointment = findAppointment(id);
        appointment.complete();

        return AppointmentMapper.toDTO(appointment);
    }

    @Transactional
    public AppointmentResponseDTO cancel(Long id) {

        Appointment appointment = findAppointment(id);
        appointment.cancel();

        return AppointmentMapper.toDTO(appointment);
    }

    private Appointment findAppointment(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));
    }
}