package br.com.caue.barbershop.entity;

import br.com.caue.barbershop.entity.enums.Payment;
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
    private BigDecimal discount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Payment payment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    public Checkout() {
    }

    public Checkout(Appointment appointment, BigDecimal discount, Payment payment) {
        this.appointment = Objects.requireNonNull(appointment, "Appointment is required");
        this.discount = discount != null ? discount : BigDecimal.ZERO;
        this.payment = Objects.requireNonNull(payment, "Payment is required");
        calculateTotal();
    }

    @PrePersist
    private void calculateTotal() {
        BigDecimal subTotal = appointment.getSubTotal();

        if (subTotal == null) {
            throw new IllegalStateException("Subtotal can not be null");
        }

        this.total = subTotal.subtract(discount);

        if (this.total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Checkout must be greater than zero.");
        }
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Checkout checkout = (Checkout) o;
        return Objects.equals(id, checkout.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
