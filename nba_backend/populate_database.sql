-- ============================================
-- SCRIPT PARA POBLAR LA BASE DE DATOS NBA
-- ============================================
-- Este script inserta datos de ejemplo en todas las tablas
-- IMPORTANTE: Las contraseñas se hashearán automáticamente al hacer login
-- Contraseña por defecto para todos los usuarios: "password123"
-- ============================================

-- Limpiar tablas (opcional - descomentar si quieres empezar desde cero)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE apuesta;
-- TRUNCATE TABLE partido;
-- TRUNCATE TABLE jugadores;
-- TRUNCATE TABLE equipo;
-- TRUNCATE TABLE usuarios;
-- SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- USUARIOS
-- ============================================
-- IMPORTANTE: Si usas contraseñas en texto plano, el sistema las hasheará automáticamente
-- al hacer login. Alternativamente, puedes generar hashes BCrypt usando el script
-- PasswordHashGenerator.java y reemplazar las contraseñas aquí.
--
-- Contraseña por defecto para todos: "password123"
-- ============================================

-- OPCIÓN 1: Contraseñas en texto plano (se hashearán automáticamente al login)
INSERT INTO usuarios (username, email, password, puntos, rol) VALUES
('admin', 'admin@nba.com', 'password123', 10000, 'ADMIN'),
('juan23', 'juan@example.com', 'password123', 2500, 'USER'),
('maria_bet', 'maria@example.com', 'password123', 3200, 'USER'),
('kobeFan24', 'kobe@example.com', 'password123', 1800, 'USER'),
('analyticsPro1', 'stats@example.com', 'password123', 4500, 'USER'),
('lebron_fan', 'lebron@example.com', 'password123', 2100, 'USER'),
('curry_shooter', 'curry@example.com', 'password123', 2800, 'USER'),
('basketball_pro', 'pro@example.com', 'password123', 1500, 'USER');

-- ============================================
-- EQUIPOS
-- ============================================
INSERT INTO equipo (nombre, conferencia, division) VALUES
('Los Angeles Lakers', 'Oeste', 'Pacífico'),
('Golden State Warriors', 'Oeste', 'Pacífico'),
('Boston Celtics', 'Este', 'Atlántico'),
('Miami Heat', 'Este', 'Sudeste'),
('Dallas Mavericks', 'Oeste', 'Suroeste'),
('Phoenix Suns', 'Oeste', 'Pacífico'),
('Milwaukee Bucks', 'Este', 'Central'),
('Philadelphia 76ers', 'Este', 'Atlántico'),
('Denver Nuggets', 'Oeste', 'Noroeste'),
('Chicago Bulls', 'Este', 'Central'),
('New York Knicks', 'Este', 'Atlántico'),
('Los Angeles Clippers', 'Oeste', 'Pacífico'),
('Brooklyn Nets', 'Este', 'Atlántico'),
('Portland Trail Blazers', 'Oeste', 'Noroeste'),
('Utah Jazz', 'Oeste', 'Noroeste'),
('Memphis Grizzlies', 'Oeste', 'Suroeste');

-- ============================================
-- JUGADORES
-- ============================================
INSERT INTO jugadores (nombre, posicion, promedio_puntos, promedio_asistencias, promedio_rebotes, equipo_id) VALUES
-- Lakers (1)
('LeBron James', 'Alero', 27.1, 7.4, 7.5, 1),
('Anthony Davis', 'Ala-pívot', 24.0, 3.1, 10.2, 1),
('Austin Reaves', 'Escolta', 15.2, 4.5, 3.8, 1),
('D\'Angelo Russell', 'Base', 17.8, 6.2, 3.1, 1),

-- Warriors (2)
('Stephen Curry', 'Base', 29.4, 6.3, 5.1, 2),
('Klay Thompson', 'Escolta', 21.5, 2.4, 3.8, 2),
('Draymond Green', 'Ala-pívot', 8.5, 6.8, 7.2, 2),
('Andrew Wiggins', 'Alero', 17.1, 2.3, 4.5, 2),

-- Celtics (3)
('Jayson Tatum', 'Alero', 26.9, 4.4, 8.1, 3),
('Jaylen Brown', 'Escolta', 23.0, 3.5, 6.5, 3),
('Kristaps Porzingis', 'Ala-pívot', 20.1, 2.0, 7.2, 3),
('Derrick White', 'Base', 15.2, 5.2, 3.9, 3),

-- Heat (4)
('Jimmy Butler', 'Alero', 22.1, 5.5, 6.3, 4),
('Bam Adebayo', 'Pívot', 19.2, 3.8, 10.1, 4),
('Tyler Herro', 'Escolta', 20.1, 4.2, 5.0, 4),
('Kyle Lowry', 'Base', 11.2, 5.1, 4.1, 4),

-- Mavericks (5)
('Luka Doncic', 'Base', 32.4, 8.0, 8.6, 5),
('Kyrie Irving', 'Base', 25.1, 5.2, 4.8, 5),
('Tim Hardaway Jr.', 'Escolta', 14.4, 1.8, 3.5, 5),

-- Suns (6)
('Kevin Durant', 'Alero', 28.3, 5.4, 7.1, 6),
('Devin Booker', 'Escolta', 27.8, 4.5, 4.3, 6),
('Bradley Beal', 'Escolta', 18.2, 4.1, 3.8, 6),

-- Bucks (7)
('Giannis Antetokounmpo', 'Ala-pívot', 30.1, 5.7, 11.8, 7),
('Damian Lillard', 'Base', 24.3, 6.7, 4.2, 7),
('Khris Middleton', 'Alero', 15.1, 4.8, 4.7, 7),

-- 76ers (8)
('Joel Embiid', 'Pívot', 33.1, 4.2, 10.2, 8),
('Tyrese Maxey', 'Base', 25.9, 6.2, 3.7, 8),
('Tobias Harris', 'Alero', 17.2, 3.1, 6.5, 8),

-- Nuggets (9)
('Nikola Jokic', 'Pívot', 26.4, 9.8, 12.1, 9),
('Jamal Murray', 'Base', 21.2, 6.5, 4.0, 9),
('Michael Porter Jr.', 'Alero', 17.4, 1.5, 5.5, 9),

-- Bulls (10)
('DeMar DeRozan', 'Alero', 24.0, 5.1, 4.3, 10),
('Zach LaVine', 'Escolta', 19.5, 3.9, 3.9, 10),
('Nikola Vucevic', 'Pívot', 18.0, 3.2, 10.5, 10);

-- ============================================
-- PARTIDOS
-- ============================================
-- Fechas en formato: 'YYYY-MM-DD HH:MM:SS'
INSERT INTO partido (fecha, equipo_local_id, equipo_visitante_id, puntos_local, puntos_visitante, estado) VALUES
-- Partidos finalizados
('2024-12-15 20:00:00', 1, 2, 112, 108, 'FINALIZADO'),
('2024-12-16 19:30:00', 3, 4, 98, 101, 'FINALIZADO'),
('2024-12-17 21:00:00', 5, 6, 120, 115, 'FINALIZADO'),
('2024-12-18 20:30:00', 7, 8, 110, 104, 'FINALIZADO'),
('2024-12-19 19:00:00', 9, 10, 105, 98, 'FINALIZADO'),
('2024-12-20 20:00:00', 2, 3, 115, 112, 'FINALIZADO'),
('2024-12-21 19:30:00', 4, 1, 102, 108, 'FINALIZADO'),
('2024-12-22 21:00:00', 6, 7, 118, 120, 'FINALIZADO'),

-- Partidos programados (próximos días)
('2024-12-25 20:00:00', 1, 3, NULL, NULL, 'PROGRAMADO'),
('2024-12-26 19:30:00', 2, 4, NULL, NULL, 'PROGRAMADO'),
('2024-12-27 21:00:00', 5, 7, NULL, NULL, 'PROGRAMADO'),
('2024-12-28 20:30:00', 6, 8, NULL, NULL, 'PROGRAMADO'),
('2024-12-29 19:00:00', 9, 1, NULL, NULL, 'PROGRAMADO'),
('2024-12-30 20:00:00', 10, 2, NULL, NULL, 'PROGRAMADO'),
('2024-12-31 19:30:00', 3, 5, NULL, NULL, 'PROGRAMADO'),
('2025-01-01 21:00:00', 4, 6, NULL, NULL, 'PROGRAMADO'),
('2025-01-02 20:00:00', 7, 9, NULL, NULL, 'PROGRAMADO'),
('2025-01-03 19:30:00', 8, 10, NULL, NULL, 'PROGRAMADO'),
('2025-01-04 21:00:00', 1, 5, NULL, NULL, 'PROGRAMADO'),
('2025-01-05 20:30:00', 2, 6, NULL, NULL, 'PROGRAMADO');

-- ============================================
-- APUESTAS
-- ============================================
-- resultado: 'PENDIENTE', 'GANADA', 'PERDIDA'
-- prediccion: 'LOCAL' o 'VISITANTE'
-- cuota: valor decimal (ej: 2.15)
INSERT INTO apuesta (puntos_apostados, prediccion, resultado, cuota, usuario_id, partido_id) VALUES
-- Apuestas en partidos finalizados
(200, 'LOCAL', 'GANADA', 2.15, 2, 1),  -- juan23 apostó por Lakers (ganó)
(150, 'VISITANTE', 'PERDIDA', 1.95, 3, 1),  -- maria_bet apostó por Warriors (perdió)
(300, 'VISITANTE', 'GANADA', 2.30, 4, 2),  -- kobeFan24 apostó por Heat (ganó)
(250, 'LOCAL', 'PERDIDA', 1.85, 5, 2),  -- analyticsPro1 apostó por Celtics (perdió)
(100, 'LOCAL', 'GANADA', 2.10, 3, 3),  -- maria_bet apostó por Mavericks (ganó)
(180, 'VISITANTE', 'PERDIDA', 1.90, 2, 3),  -- juan23 apostó por Suns (perdió)
(220, 'LOCAL', 'GANADA', 2.25, 5, 4),  -- analyticsPro1 apostó por Bucks (ganó)
(150, 'VISITANTE', 'PERDIDA', 1.88, 4, 4),  -- kobeFan24 apostó por 76ers (perdió)
(300, 'LOCAL', 'GANADA', 2.05, 2, 5),  -- juan23 apostó por Nuggets (ganó)
(200, 'VISITANTE', 'PERDIDA', 1.92, 3, 5),  -- maria_bet apostó por Bulls (perdió)
(250, 'VISITANTE', 'GANADA', 2.20, 4, 6),  -- kobeFan24 apostó por Celtics (ganó)
(180, 'LOCAL', 'PERDIDA', 1.87, 5, 6),  -- analyticsPro1 apostó por Warriors (perdió)
(150, 'VISITANTE', 'GANADA', 2.18, 2, 7),  -- juan23 apostó por Lakers (ganó)
(200, 'LOCAL', 'PERDIDA', 1.89, 3, 7),  -- maria_bet apostó por Heat (perdió)
(280, 'VISITANTE', 'GANADA', 2.12, 4, 8),  -- kobeFan24 apostó por Bucks (ganó)
(170, 'LOCAL', 'PERDIDA', 1.91, 5, 8),  -- analyticsPro1 apostó por Suns (perdió),

-- Apuestas pendientes en partidos programados
(200, 'LOCAL', 'PENDIENTE', 2.15, 2, 9),  -- juan23
(150, 'VISITANTE', 'PENDIENTE', 1.95, 3, 9),  -- maria_bet
(300, 'LOCAL', 'PENDIENTE', 2.30, 4, 10),  -- kobeFan24
(250, 'VISITANTE', 'PENDIENTE', 1.85, 5, 10),  -- analyticsPro1
(100, 'LOCAL', 'PENDIENTE', 2.10, 2, 11),  -- juan23
(180, 'VISITANTE', 'PENDIENTE', 1.90, 3, 11),  -- maria_bet
(220, 'LOCAL', 'PENDIENTE', 2.25, 4, 12),  -- kobeFan24
(150, 'VISITANTE', 'PENDIENTE', 1.88, 5, 12),  -- analyticsPro1
(300, 'LOCAL', 'PENDIENTE', 2.05, 2, 13),  -- juan23
(200, 'VISITANTE', 'PENDIENTE', 1.92, 3, 13),  -- maria_bet
(250, 'LOCAL', 'PENDIENTE', 2.20, 4, 14),  -- kobeFan24
(180, 'VISITANTE', 'PENDIENTE', 1.87, 5, 14),  -- analyticsPro1
(150, 'LOCAL', 'PENDIENTE', 2.18, 2, 15),  -- juan23
(200, 'VISITANTE', 'PENDIENTE', 1.89, 3, 15),  -- maria_bet
(280, 'LOCAL', 'PENDIENTE', 2.12, 4, 16),  -- kobeFan24
(170, 'VISITANTE', 'PENDIENTE', 1.91, 5, 16);  -- analyticsPro1

-- ============================================
-- NOTAS IMPORTANTES
-- ============================================
-- 1. CONTRASEÑAS:
--    - Si usas contraseñas en texto plano, el sistema las hasheará automáticamente al hacer login
--    - Para generar hashes BCrypt, ejecuta: PasswordHashGenerator.java
--    - Contraseña por defecto para todos: "password123"
--
-- 2. USUARIO ADMINISTRADOR:
--    Username: admin
--    Password: password123
--    Email: admin@nba.com
--    Rol: ADMIN
--    Puntos: 10000
--
-- 3. USUARIOS NORMALES:
--    Todos tienen rol USER y contraseña "password123"
--    Puntos iniciales: entre 1500 y 4500
--
-- 4. EQUIPOS:
--    - 16 equipos de la NBA
--    - Distribuidos en conferencias Este y Oeste
--    - Con diferentes divisiones
--
-- 5. JUGADORES:
--    - Jugadores reales de la NBA con estadísticas
--    - Asignados a sus equipos correspondientes
--    - Estadísticas: puntos, asistencias y rebotes por juego
--
-- 6. PARTIDOS:
--    - Partidos finalizados: tienen puntos y estado 'FINALIZADO'
--    - Partidos programados: tienen NULL en puntos y estado 'PROGRAMADO'
--    - Fechas en formato: 'YYYY-MM-DD HH:MM:SS'
--
-- 7. APUESTAS:
--    - Apuestas en partidos finalizados: resultado 'GANADA' o 'PERDIDA'
--    - Apuestas en partidos programados: resultado 'PENDIENTE'
--    - Todas tienen cuotas asignadas (entre 1.85 y 2.30)
--    - Predicción: 'LOCAL' o 'VISITANTE'
--
-- 8. PARA USAR ESTE SCRIPT:
--    a) Asegúrate de que la base de datos 'nba_app' existe
--    b) Ejecuta este script en MySQL/MariaDB:
--       mysql -u root -p nba_app < populate_database.sql
--    c) O ejecuta desde MySQL Workbench/HeidiSQL
--    d) Las tablas se crearán automáticamente si usas 'spring.jpa.hibernate.ddl-auto=update'
--
-- 9. DESPUÉS DE EJECUTAR:
--    - Haz login con cualquier usuario usando "password123"
--    - El sistema hasheará automáticamente las contraseñas en texto plano
--    - El usuario 'admin' tiene acceso al panel de administración
-- ============================================
