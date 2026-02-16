package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    boolean existsByAppointmentId(Long appointmentId);
}