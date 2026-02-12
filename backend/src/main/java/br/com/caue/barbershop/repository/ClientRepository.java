package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}