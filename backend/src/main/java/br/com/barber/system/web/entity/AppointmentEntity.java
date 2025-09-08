package br.com.barber.system.web.entity;

import br.com.barber.system.web.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tb_appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private BarberEntity barber;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @ManyToMany
    @JoinTable(
            name = "tb_appointment_services",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceItemEntity> services;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public AppointmentEntity() {
    }

    public AppointmentEntity(Long id, String clientName, BarberEntity barber, AppointmentStatus status, LocalDate appointmentDate, PaymentEntity payment) {
        this.id = id;
        this.clientName = clientName;
        this.barber = barber;
        this.status = status;
        this.appointmentDate = appointmentDate;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BarberEntity getBarber() {
        return barber;
    }

    public void setBarber(BarberEntity barber) {
        this.barber = barber;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public Set<ServiceItemEntity> getServices() {
        return services;
    }

    public void addService(ServiceItemEntity serviceItem) {
        this.services.add(serviceItem);
    }

    public void removeService(ServiceItemEntity serviceItem) {
        this.services.remove(serviceItem);
    }

    public BigDecimal getTotalPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (ServiceItemEntity service : services) {
            sum = sum.add(service.getPrice());
        }
        return sum;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}