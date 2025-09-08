CREATE TABLE IF NOT EXISTS tb_appointments (
    id SERIAL PRIMARY KEY,
    client_name VARCHAR(100) NOT NULL,
    barber_id INTEGER NOT NULL,
    appointment_date DATE DEFAULT NOW(),
    status VARCHAR(20) NOT NULL DEFAULT 'AGUARDANDO',
    payment_id INTEGER,
    total_price NUMERIC(10, 2) NOT NULL,
    CONSTRAINT status_check CHECK (status IN ('AGUARDANDO', 'CORTANDO', 'FINALIZADO')),
    CONSTRAINT fk_appointment_barber FOREIGN KEY (barber_id) REFERENCES tb_barbers (id),
    CONSTRAINT fk_appointment_payment FOREIGN KEY (payment_id) REFERENCES tb_payments (id)
);