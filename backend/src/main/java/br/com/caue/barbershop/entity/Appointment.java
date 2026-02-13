package br.com.caue.barbershop.entity;

import br.com.caue.barbershop.entity.enums.AppointmentStatus;
import br.com.caue.barbershop.entity.enums.AppointmentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barber barber;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentType type;

    @ManyToMany
    @JoinTable(
            name = "tb_appointment_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceItem> services = new HashSet<>();

    public Appointment() {
    }

    public Appointment(Barber barber, Client client, LocalDateTime date, AppointmentStatus status, AppointmentType type) {
        this.barber = barber;
        this.client = client;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Barber getBarber() {
        return barber;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public AppointmentType getType() {
        return type;
    }

    public Set<ServiceItem> getServices() {
        return services;
    }

    public void addService(ServiceItem serviceItem) {
        services.add(serviceItem);
    }

    public void removeService(ServiceItem serviceItem) {
        services.remove(serviceItem);
    }

    public void addServices(Collection<ServiceItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Services cannot be empty");
        }
        services.addAll(items);
    }


    public BigDecimal getSubTotal() {
        return services.stream()
                .map(ServiceItem::getBasePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
