package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
}