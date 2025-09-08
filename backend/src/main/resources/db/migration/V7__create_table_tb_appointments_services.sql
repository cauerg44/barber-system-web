CREATE TABLE IF NOT EXISTS tb_appointment_services (
    appointment_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    PRIMARY KEY (appointment_id, service_id),
    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES tb_appointments(id) ON DELETE CASCADE,
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES tb_services(id) ON DELETE CASCADE
);