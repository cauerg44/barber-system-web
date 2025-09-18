package br.com.barber.system.web.repository;

import br.com.barber.system.web.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query(value = "SELECT * FROM tb_appointments WHERE status = :status", nativeQuery = true)
    List<AppointmentEntity> findByStatusNative(@Param("status") String status);
}