package br.com.barber.system.web.service;

import br.com.barber.system.web.dto.request.AppointmentRequestToCreate;
import br.com.barber.system.web.dto.response.AppointmentResponse;
import br.com.barber.system.web.entity.AppointmentEntity;
import br.com.barber.system.web.entity.BarberEntity;
import br.com.barber.system.web.entity.PaymentEntity;
import br.com.barber.system.web.entity.ServiceItemEntity;
import br.com.barber.system.web.entity.enums.AppointmentStatus;
import br.com.barber.system.web.repository.AppointmentRepository;
import br.com.barber.system.web.repository.BarberRepository;
import br.com.barber.system.web.repository.PaymentRepository;
import br.com.barber.system.web.repository.ServiceItemRepository;
import br.com.barber.system.web.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;
    private final BarberRepository barberRepository;
    private final PaymentRepository paymentRepository;
    private final ServiceItemRepository serviceRepository;

    public AppointmentService(AppointmentRepository repository, BarberRepository barberRepository, PaymentRepository paymentRepository, ServiceItemRepository serviceRepository) {
        this.repository = repository;
        this.barberRepository = barberRepository;
        this.paymentRepository = paymentRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        List<AppointmentEntity> list = repository.findAll();
        return list.stream().map(entity -> new AppointmentResponse(entity)).toList();
    }

    @Transactional
    public AppointmentResponse saveNew(AppointmentRequestToCreate request) {
        AppointmentEntity entity = new AppointmentEntity();
        requestToCreate(request, entity);

        entity.setStatus(AppointmentStatus.AGUARDANDO);

        entity = repository.save(entity);
        return new AppointmentResponse(entity);
    }

    @Transactional
    public AppointmentResponse schedule(AppointmentRequestToCreate request) {
        AppointmentEntity entity = new AppointmentEntity();
        requestToCreate(request, entity);

        entity.setStatus(AppointmentStatus.AGENDADO);

        entity = repository.save(entity);
        return new AppointmentResponse(entity);
    }

    private void requestToCreate(AppointmentRequestToCreate request, AppointmentEntity entity) {
        BarberEntity barber = barberRepository.findById(request.barberId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado"));

        PaymentEntity payment = paymentRepository.findById(request.paymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Método de pagamento não encontrado"));

        entity.setClientName(request.clientName());
        entity.setBarber(barber);
        entity.setAppointmentDate(request.appointmentDate());

        if (request.servicesIds() != null) {
            request.servicesIds().forEach(serviceId -> {
                ServiceItemEntity service = serviceRepository.findById(serviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
                entity.addService(service);
            });
        }

        entity.setPayment(payment);
    }
}