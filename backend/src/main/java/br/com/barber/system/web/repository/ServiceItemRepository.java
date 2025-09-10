package br.com.barber.system.web.repository;

import br.com.barber.system.web.entity.ServiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemRepository extends JpaRepository<ServiceItemEntity, Long> {
}
