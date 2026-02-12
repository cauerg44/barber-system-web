package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}