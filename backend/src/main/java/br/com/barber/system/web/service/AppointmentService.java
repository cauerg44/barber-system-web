package br.com.barber.system.web.service;

import br.com.barber.system.web.dto.request.AppointmentRequestToCreate;
import br.com.barber.system.web.dto.request.AppointmentRequestToUpdate;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findByStatus(AppointmentStatus status) {
        return repository.findByStatusNative(status.name()).stream()
                .map(entity -> new AppointmentResponse(entity))
                .toList();
    }

    @Transactional
    public AppointmentResponse saveNew(AppointmentRequestToCreate request) {
        AppointmentEntity entity = new AppointmentEntity();
        requestToCreate(request, entity);

        entity.setStatus(AppointmentStatus.AGUARDANDO);

        entity.setAppointmentDate(LocalDate.now());

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

    @Transactional
    public AppointmentResponse update(Long id, AppointmentRequestToUpdate request) {
        AppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado"));

        switch (entity.getStatus()) {
            case AGENDADO -> updateWhenStatusIsScheduled(entity, request);
            case AGUARDANDO -> updateWhenStatusIsWaiting(entity, request);
            case EM_ATENDIMENTO -> updateWhenStatusIsInProgress(entity, request);
            case FINALIZADO -> throw new IllegalStateException("Atendimento finalizado não pode ser atualizado.");
        }

        repository.save(entity);
        return new AppointmentResponse(entity);
    }

    @Transactional
    public AppointmentResponse updateStatus(Long id) {
        AppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado"));

        switch (entity.getStatus()) {
            case AGENDADO, AGUARDANDO -> entity.setStatus(AppointmentStatus.EM_ATENDIMENTO);
            case EM_ATENDIMENTO -> entity.setStatus(AppointmentStatus.FINALIZADO);
            case FINALIZADO -> throw new IllegalStateException("Atendimento já finalizado.");
        }

        repository.save(entity);
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

    private void updateWhenStatusIsScheduled(AppointmentEntity entity, AppointmentRequestToUpdate request) {
        entity.setClientName(request.clientName());
        entity.setAppointmentDate(request.appointmentDate());
        entity.setBarber(barberRepository.findById(request.barberId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado")));
        entity.setPayment(paymentRepository.findById(request.paymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada")));

        refreshServices(entity, request.servicesIds());
    }

    private void updateWhenStatusIsWaiting(AppointmentEntity entity, AppointmentRequestToUpdate request) {
        entity.setClientName(request.clientName());
        entity.setBarber(barberRepository.findById(request.barberId())
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado")));
        entity.setPayment(paymentRepository.findById(request.paymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada")));

        refreshServices(entity, request.servicesIds());
    }

    private void updateWhenStatusIsInProgress(AppointmentEntity entity, AppointmentRequestToUpdate request) {
        request.servicesIds().forEach(serviceId -> {
            ServiceItemEntity service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
            entity.addService(service);
        });
    }

    private void refreshServices(AppointmentEntity entity, Set<Long> servicesIds) {
        entity.getServices().clear();
        servicesIds.forEach(serviceId -> {
            ServiceItemEntity service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));
            entity.addService(service);
        });
    }
}