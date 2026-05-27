<<<<<<< HEAD
-- Insertar Usuarios con contraseña y signo zodiacal
INSERT INTO usuarios (nombre, email, password, signo_zodiacal) VALUES ('Circe Alva', 'circe@hecate.com', '1234', 'Escorpio');
INSERT INTO usuarios (nombre, email, password, signo_zodiacal) VALUES ('Hermes Trimegisto', 'hermes@alquimia.com', '1234', 'Géminis');
INSERT INTO usuarios (nombre, email, password, signo_zodiacal) VALUES ('Morgana LeFay', 'morgana@avalon.es', '1234', 'Piscis');

-- Insertar Reservas asignadas a los IDs de los usuarios
INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id) 
VALUES ('2026-10-31 20:00:00', 'Rincón del Oráculo', 'Lectura de Tarot', 1);

INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id) 
VALUES ('2026-11-01 18:30:00', 'Altar Central VIP', 'Limpieza del Aura', 1);

INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id) 
=======
INSERT INTO usuarios (nombre, email, signo_zodiacal) VALUES ('Circe Alva', 'circe@hecate.com', 'Escorpio');
INSERT INTO usuarios (nombre, email, signo_zodiacal) VALUES ('Hermes Trimegisto', 'hermes@alquimia.com', 'Géminis');
INSERT INTO usuarios (nombre, email, signo_zodiacal) VALUES ('Morgana LeFay', 'morgana@avalon.es', 'Piscis');

INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id)
VALUES ('2026-10-31 20:00:00', 'Rincón del Oráculo', 'Lectura de Tarot', 1);

INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id)
VALUES ('2026-11-01 18:30:00', 'Altar Central VIP', 'Limpieza del Aura', 1);

INSERT INTO reservas (fecha_hora, mesa_solicitada, experiencia_esoterica, usuario_id)
>>>>>>> 8689bf185cc0758aed9cd71df4667622986fdfbd
VALUES ('2026-10-31 22:00:00', 'Mesa de la Luna Alta', 'Ninguna, solo Pociones/Café', 2);