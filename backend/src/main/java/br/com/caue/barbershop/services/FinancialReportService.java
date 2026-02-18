package br.com.caue.barbershop.services;

import br.com.caue.barbershop.dto.response.DailyTotalDTO;
import br.com.caue.barbershop.dto.response.PaymentTotalDTO;
import br.com.caue.barbershop.entity.enums.Payment;
import br.com.caue.barbershop.repository.FinancialReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class FinancialReportService {

    private final FinancialReportRepository repository;

    public FinancialReportService(FinancialReportRepository repository) {
        this.repository = repository;
    }

    public DailyTotalDTO totalByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        BigDecimal total = repository.sumByDate(start, end);

        if (total == null) {
            total = BigDecimal.ZERO;
        }

        return new DailyTotalDTO(date, total);
    }

    public PaymentTotalDTO totalByPayment(Payment payment, LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        BigDecimal total = repository.sumByPaymentAndDate(
                payment.name(),
                start,
                end
        );

        if (total == null) {
            total = BigDecimal.ZERO;
        }

        return new PaymentTotalDTO(payment.name(), total);
    }
}