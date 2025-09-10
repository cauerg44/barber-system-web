package br.com.barber.system.web.repository;

import br.com.barber.system.web.entity.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<BarberEntity, Long> {
}
