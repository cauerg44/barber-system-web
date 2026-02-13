package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
