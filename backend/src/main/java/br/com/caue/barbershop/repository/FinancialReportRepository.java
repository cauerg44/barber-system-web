package br.com.caue.barbershop.repository;

import br.com.caue.barbershop.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface FinancialReportRepository extends JpaRepository<Checkout, Long> {

    @Query(value = """
        SELECT SUM(c.total)
        FROM tb_checkout c
        WHERE c.created_at BETWEEN :start AND :end
        """, nativeQuery = true)
    BigDecimal sumByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = """
        SELECT SUM(c.total)
        FROM tb_checkout c
        WHERE c.payment = :payment
        AND c.created_at >= :start
        AND c.created_at < :end
        """, nativeQuery = true)
    BigDecimal sumByPaymentAndDate(@Param("payment") String payment, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}