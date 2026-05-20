-- ============================================
-- SCRIPT PARA CREAR BASE DE DATOS VACÍA
-- ============================================
-- Este script:
-- 1) Crea la base de datos `nba_app`
-- 2) Crea las tablas necesarias (DDL) — imprescindible si ejecutas solo este SQL
--    sin haber arrancado antes el backend con Hibernate.
-- 3) Inserta datos mínimos: equipos, jugadores y usuario admin.
-- ============================================
-- IMPORTANTE: Ejecuta este script ANTES de ejecutar populate_database.sql
-- ============================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS nba_app;
USE nba_app;

-- ============================================
-- ESQUEMA (tablas)
-- ============================================
-- Sin estas tablas, los INSERT fallan con ERROR 1146.
-- Hibernate con ddl-auto=update puede alterar columnas después; este DDL
-- coincide con las entidades JPA para poder poblar la BD solo con mysql.

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS equipo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    conferencia VARCHAR(255),
    division VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_equipo_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS jugadores (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    posicion VARCHAR(255),
    promedio_puntos DOUBLE,
    promedio_asistencias DOUBLE,
    promedio_rebotes DOUBLE,
    equipo_id BIGINT,
    PRIMARY KEY (id),
    KEY idx_jugadores_equipo (equipo_id),
    UNIQUE KEY uk_jugador_equipo_nombre (nombre, equipo_id),
    CONSTRAINT fk_jugadores_equipo FOREIGN KEY (equipo_id) REFERENCES equipo (id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    puntos INT NOT NULL,
    rol VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_username (username),
    UNIQUE KEY uk_usuarios_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS partido (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha DATETIME(6),
    equipo_local_id BIGINT,
    equipo_visitante_id BIGINT,
    puntos_local INT,
    puntos_visitante INT,
    estado VARCHAR(255),
    PRIMARY KEY (id),
    KEY idx_partido_local (equipo_local_id),
    KEY idx_partido_visitante (equipo_visitante_id),
    CONSTRAINT fk_partido_equipo_local FOREIGN KEY (equipo_local_id) REFERENCES equipo (id),
    CONSTRAINT fk_partido_equipo_visitante FOREIGN KEY (equipo_visitante_id) REFERENCES equipo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS apuesta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    puntos_apostados INT NOT NULL,
    prediccion VARCHAR(255),
    cuota DOUBLE,
    resultado VARCHAR(255) NOT NULL,
    usuario_id BIGINT,
    partido_id BIGINT,
    PRIMARY KEY (id),
    KEY idx_apuesta_usuario (usuario_id),
    KEY idx_apuesta_partido (partido_id),
    CONSTRAINT fk_apuesta_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_apuesta_partido FOREIGN KEY (partido_id) REFERENCES partido (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- EQUIPOS
-- ============================================
-- Los equipos son imprescindibles para el funcionamiento del sistema
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
('Memphis Grizzlies', 'Oeste', 'Suroeste')
ON DUPLICATE KEY UPDATE nombre=nombre;

-- ============================================
-- JUGADORES
-- ============================================
-- Los jugadores son imprescindibles para mostrar estadísticas
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
('Nikola Vucevic', 'Pívot', 18.0, 3.2, 10.5, 10)
ON DUPLICATE KEY UPDATE nombre=nombre;

-- ============================================
-- USUARIO ADMIN (IMPRESCINDIBLE PARA EL PANEL)
-- ============================================
-- Usuario admin por defecto para poder gestionar partidos desde el cliente.
-- Nota: la contraseña puede estar en texto plano; en el primer login se migrará a BCrypt automáticamente.
INSERT INTO usuarios (username, email, password, puntos, rol) VALUES
('admin', 'admin@tfg.local', 'admin123', 1000, 'ADMIN')
ON DUPLICATE KEY UPDATE username=username;

-- ============================================
-- NOTAS
-- ============================================
-- Este script crea solo los datos imprescindibles (equipos y jugadores).
-- Para poblar la base de datos completa con usuarios, partidos y apuestas,
-- ejecuta el script populate_database.sql después de este.
-- ============================================
