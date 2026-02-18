INSERT INTO tb_barber (name, is_active) VALUES ('Júnior', true);
INSERT INTO tb_barber (name, is_active) VALUES ('Allan', true);
INSERT INTO tb_barber (name, is_active) VALUES ('Daniel', true);

INSERT INTO tb_client (name, phone) VALUES ('João Silva', '11999990001');
INSERT INTO tb_client (name, phone) VALUES ('Maria Oliveira', '11999990002');
INSERT INTO tb_client (name, phone) VALUES ('Carlos Mendes', '11999990003');
INSERT INTO tb_client (name, phone) VALUES ('Ana Pereira', '11999990004');
INSERT INTO tb_client (name, phone) VALUES ('Lucas Rocha', '11999990005');
INSERT INTO tb_client (name, phone) VALUES ('Fernanda Lima', '11999990006');
INSERT INTO tb_client (name, phone) VALUES ('Rafael Souza', '11999990007');
INSERT INTO tb_client (name, phone) VALUES ('Bruno Almeida', '11999990008');
INSERT INTO tb_client (name, phone) VALUES ('Juliana Costa', '11999990009');
INSERT INTO tb_client (name, phone) VALUES ('Thiago Martins', '11999990010');

INSERT INTO tb_service (name, base_price) VALUES ('Cabelo Masculino', 25.00);
INSERT INTO tb_service (name, base_price) VALUES ('Cabelo Feminino', 40.00);
INSERT INTO tb_service (name, base_price) VALUES ('Barba', 15.00);
INSERT INTO tb_service (name, base_price) VALUES ('Cabelo e Barba', 40.00);
INSERT INTO tb_service (name, base_price) VALUES ('Corte Navalhado', 30.00);
INSERT INTO tb_service (name, base_price) VALUES ('Sobrancelha', 10.00);
INSERT INTO tb_service (name, base_price) VALUES ('Pezinho', 10.00);

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (1, 1, '2026-02-10 14:00:00', 'COMPLETED', 'IN_WALK');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (2, 3, '2026-02-11 10:00:00', 'WAITING', 'SCHEDULED');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (3, 4, '2026-02-11 15:30:00', 'WAITING', 'SCHEDULED');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (1, 6, '2026-02-12 09:00:00', 'COMPLETED', 'SCHEDULED');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (2, 7, '2026-02-12 11:00:00', 'COMPLETED', 'IN_WALK');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (3, 8, '2026-02-12 14:00:00', 'COMPLETED', 'SCHEDULED');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (1, 9, '2026-02-13 10:30:00', 'COMPLETED', 'IN_WALK');

INSERT INTO tb_appointment (barber_id, client_id, date, status, type)VALUES (2, 10, '2026-02-13 16:00:00', 'COMPLETED', 'SCHEDULED');

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (1, 1);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (1, 2);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (2, 1);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (2, 3);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (3, 2);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (3, 7);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (4, 1);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (4, 3);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (5, 4);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (6, 5);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (6, 6);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (7, 2);

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (8, 1);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (8, 7);

-- CHECKOUTS

-- CHECKOUTS

INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (1, 5.00, 'PIX', 60.00, '2026-02-10 14:30:00');
INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (4, 0.00, 'CASH', 40.00, '2026-02-12 09:40:00');
INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (5, 5.00, 'PIX', 35.00, '2026-02-12 11:40:00');
INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (6, 0.00, 'CREDIT_CARD', 40.00, '2026-02-12 14:45:00');
INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (7, 0.00, 'PIX', 40.00, '2026-02-13 10:50:00');
INSERT INTO tb_checkout (appointment_id, discount, payment, total, created_at) VALUES (8, 10.00, 'CASH', 25.00, '2026-02-13 16:50:00');
