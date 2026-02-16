package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.request.CheckoutCreateRequest;
import br.com.caue.barbershop.dto.response.CheckoutResponseDTO;
import br.com.caue.barbershop.entity.Appointment;
import br.com.caue.barbershop.entity.Checkout;
import br.com.caue.barbershop.dto.mapper.CheckoutMapper;
import br.com.caue.barbershop.repository.AppointmentRepository;
import br.com.caue.barbershop.repository.CheckoutRepository;
import br.com.caue.barbershop.services.exceptions.BusinessException;
import br.com.caue.barbershop.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckoutService {

    private final CheckoutRepository repository;
    private final AppointmentService appointmentService;

    public CheckoutService(CheckoutRepository repository, AppointmentService appointmentService) {
        this.repository = repository;
        this.appointmentService = appointmentService;
    }

    @Transactional(readOnly = true)
    public CheckoutResponseDTO findById(Long id) {

        Checkout checkout = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Checkout not found"));

        return CheckoutMapper.toDTO(checkout);
    }

    @Transactional
    public CheckoutResponseDTO create(CheckoutCreateRequest request) {

        Appointment appointment = appointmentService.findAppointmentById(request.appointmentId());

        if (repository.existsByAppointmentId(request.appointmentId())) {
            throw new BusinessException("Checkout already exists for this appointment");
        }

        Checkout checkout = new Checkout(
                appointment,
                request.discount(),
                request.payment()
        );

        repository.save(checkout);

        return CheckoutMapper.toDTO(checkout);
    }
}