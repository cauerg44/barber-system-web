INSERT INTO tb_barber (name, is_active) VALUES ('Júnior', true);
INSERT INTO tb_barber (name, is_active) VALUES ('Allan', true);
INSERT INTO tb_barber (name, is_active) VALUES ('Daniel', true);

INSERT INTO tb_client (name, phone) VALUES ('João Silva', '11999990001');
INSERT INTO tb_client (name, phone) VALUES ('Maria Oliveira', '11999990002');

INSERT INTO tb_service (name, base_price) VALUES ('Cabelo Masculino', 25.00);
INSERT INTO tb_service (name, base_price) VALUES ('Cabelo Feminino', 40.00);
INSERT INTO tb_service (name, base_price) VALUES ('Barba', 15.00);
INSERT INTO tb_service (name, base_price) VALUES ('Cabelo e Barba', 40.00);
INSERT INTO tb_service (name, base_price) VALUES ('Corte Navalhado', 30.00);
INSERT INTO tb_service (name, base_price) VALUES ('Sobrancelha', 10.00);
INSERT INTO tb_service (name, base_price) VALUES ('Pezinho', 10.00);


INSERT INTO tb_appointment (barber_id, client_id, date, status, type) VALUES (1, 1, '2026-02-10 14:00:00', 'COMPLETED', 'IN_WALK');

INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (1, 1);
INSERT INTO tb_appointment_service (appointment_id, service_id) VALUES (1, 2);

INSERT INTO tb_checkout (appointment_id, discount, payment, total) VALUES (1, 5.00, 'PIX', 60.00);
