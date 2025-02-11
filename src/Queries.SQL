INSERT INTO users (email, password, first_name, last_name) VALUES
                                                               ('ion.popescu@example.com', 'parolaSigura123', 'Ion', 'Popescu'),
                                                               ('maria.ionescu@example.com', 'parola456', 'Maria', 'Ionescu'),
                                                               ('vasile.stan@example.com', 'parola789', 'Vasile', 'Stan');
INSERT INTO barbers (name, specialty, rating) VALUES
                                                  ('Alexandru Tuns', 'Tunsori moderne', 4.8),
                                                  ('Cristian Barbu', 'Barbierit si contur', 4.6),
                                                  ('Simona Coafura', 'Tunsori clasice', 4.9);
INSERT INTO services (name, duration, price) VALUES
                                                 ('Tuns barbati', 30, 50.00),
                                                 ('Barbierit', 20, 35.00),
                                                 ('Spalat si coafat', 25, 40.00),
                                                 ('Aranjat barba', 15, 25.00);
INSERT INTO bookings (date_time, status, paid, user_id, barber_id) VALUES
                                                                       ('2025-03-10 14:00:00', 'PROGRAMAT', TRUE, 1, 1),
                                                                       ('2025-03-12 16:30:00', 'FINALIZAT', TRUE, 2, 2),
                                                                       ('2025-03-15 11:00:00', 'ANULAT', FALSE, 3, 3);
-- Associated services to bookings
INSERT INTO booking_services (booking_id, service_id) VALUES
                                                          (1, 1),  -- Programare 1: Tuns barbati
                                                          (1, 4),  -- Programare 1: Aranjat barba
                                                          (2, 2),  -- Programare 2: Barbierit
                                                          (3, 3);  -- Programare 3: Spalat si coafat
INSERT INTO notifications (message, recipient_email, sent_time, delivered, booking_id) VALUES
                                                                                           ('Programarea ta este confirmata.', 'ion.popescu@example.com', '2025-03-09 10:00:00', TRUE, 1),
                                                                                           ('Programarea ta a fost finalizata.', 'maria.ionescu@example.com', '2025-03-12 18:00:00', TRUE, 2),
                                                                                           ('Programarea ta a fost anulata.', 'vasile.stan@example.com', '2025-03-15 09:30:00', TRUE, 3);
INSERT INTO reviews (rating, comment, review_date, booking_id) VALUES
                                                                   (5, 'Foarte multumit! Tuns perfect!', '2025-03-10 15:00:00', 1),
                                                                   (4, 'Barbierit bun, dar putea fi mai atent.', '2025-03-12 17:00:00', 2),
                                                                   (3, 'Programarea a fost anulata, nu pot lasa recenzie.', '2025-03-15 11:30:00', 3);


SELECT * FROM users;
SELECT * FROM barbers;
SELECT * FROM services;
SELECT * FROM bookings;
SELECT * FROM booking_services;
SELECT * FROM notifications;
SELECT * FROM reviews;