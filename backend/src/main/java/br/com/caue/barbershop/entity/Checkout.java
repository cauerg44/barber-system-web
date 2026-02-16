package br.com.caue.barbershop.entity;

import br.com.caue.barbershop.entity.enums.AppointmentStatus;
import br.com.caue.barbershop.entity.enums.Payment;
import br.com.caue.barbershop.services.exceptions.BusinessException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "appointment_id",
            nullable = false,
            unique = true
    )
    private Appointment appointment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Payment payment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    protected Checkout() {
    }

    public Checkout(Appointment appointment, BigDecimal discount, Payment payment) {
        this.appointment = Objects.requireNonNull(appointment, "Appointment is required");
        this.payment = Objects.requireNonNull(payment, "Payment is required");
        this.discount = discount != null ? discount : BigDecimal.ZERO;

        validateBusinessRules();
        calculateTotal();
    }

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Payment getPayment() {
        return payment;
    }

    public BigDecimal getTotal() {
        return total;
    }

    private void validateBusinessRules() {

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new BusinessException("Only completed appointments can be checked out");
        }

        if (appointment.getSubTotal() == null) {
            throw new IllegalStateException("Subtotal cannot be null");
        }

        if (discount.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Discount must be zero or positive");
        }

        if (discount.compareTo(appointment.getSubTotal()) > 0) {
            throw new BusinessException("Discount cannot be greater than subtotal");
        }
    }

    private void calculateTotal() {
        this.total = appointment.getSubTotal().subtract(discount);
    }
}