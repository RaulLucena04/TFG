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
('Memphis Grizzlies', 'Oeste', 'Suroeste'),
-- Resto de la NBA (para completar 30 equipos)
('Sacramento Kings', 'Oeste', 'Pacífico'),
('San Antonio Spurs', 'Oeste', 'Suroeste'),
('Houston Rockets', 'Oeste', 'Suroeste'),
('New Orleans Pelicans', 'Oeste', 'Suroeste'),
('Minnesota Timberwolves', 'Oeste', 'Noroeste'),
('Oklahoma City Thunder', 'Oeste', 'Noroeste'),
('Cleveland Cavaliers', 'Este', 'Central'),
('Detroit Pistons', 'Este', 'Central'),
('Indiana Pacers', 'Este', 'Central'),
('Atlanta Hawks', 'Este', 'Sudeste'),
('Charlotte Hornets', 'Este', 'Sudeste'),
('Orlando Magic', 'Este', 'Sudeste'),
('Toronto Raptors', 'Este', 'Atlántico'),
('Washington Wizards', 'Este', 'Sudeste');

-- ============================================
-- JUGADORES
-- ============================================
-- NOTA: Para tener datos de prueba “ricos”, insertamos 10 jugadores por equipo.
-- Las medias son plausibles (sirven para UI, rankings y cuotas) y no pretenden ser exactas al 100%.
INSERT INTO jugadores (nombre, posicion, promedio_puntos, promedio_asistencias, promedio_rebotes, equipo_id) VALUES
-- Lakers (1)
('LeBron James', 'Alero', 25.7, 7.2, 7.4, 1),
('Anthony Davis', 'Ala-pívot', 24.2, 3.1, 12.1, 1),
('Austin Reaves', 'Escolta', 16.0, 5.0, 4.4, 1),
('D''Angelo Russell', 'Base', 18.1, 6.2, 3.2, 1),
('Rui Hachimura', 'Ala-pívot', 13.2, 1.2, 4.9, 1),
('Taurean Prince', 'Alero', 8.7, 1.8, 2.9, 1),
('Gabe Vincent', 'Base', 7.8, 2.6, 1.6, 1),
('Jarred Vanderbilt', 'Ala-pívot', 7.4, 1.3, 6.2, 1),
('Christian Wood', 'Pívot', 10.2, 1.0, 6.8, 1),
('Cam Reddish', 'Alero', 8.1, 1.2, 2.6, 1),

-- Warriors (2)
('Stephen Curry', 'Base', 28.1, 6.3, 4.5, 2),
('Klay Thompson', 'Escolta', 18.0, 2.3, 3.5, 2),
('Draymond Green', 'Ala-pívot', 8.0, 6.5, 7.1, 2),
('Andrew Wiggins', 'Alero', 15.3, 2.5, 4.7, 2),
('Jonathan Kuminga', 'Alero', 16.7, 2.2, 5.1, 2),
('Chris Paul', 'Base', 9.4, 6.8, 3.7, 2),
('Kevon Looney', 'Pívot', 6.1, 2.2, 7.2, 2),
('Brandin Podziemski', 'Escolta', 9.7, 3.5, 5.8, 2),
('Moses Moody', 'Escolta', 8.3, 1.1, 2.8, 2),
('Gary Payton II', 'Escolta', 6.7, 1.5, 3.3, 2),

-- Celtics (3)
('Jayson Tatum', 'Alero', 27.1, 4.6, 8.3, 3),
('Jaylen Brown', 'Escolta', 23.0, 3.6, 5.6, 3),
('Kristaps Porzingis', 'Ala-pívot', 20.2, 2.0, 7.1, 3),
('Jrue Holiday', 'Base', 12.9, 5.1, 5.4, 3),
('Derrick White', 'Base', 15.3, 5.3, 4.1, 3),
('Al Horford', 'Pívot', 8.8, 2.5, 6.3, 3),
('Payton Pritchard', 'Base', 9.6, 3.2, 3.4, 3),
('Sam Hauser', 'Alero', 9.0, 1.0, 3.2, 3),
('Luke Kornet', 'Pívot', 5.2, 1.2, 4.6, 3),
('Boston Bench Wing', 'Alero', 6.8, 1.4, 2.9, 3),

-- Heat (4)
('Jimmy Butler', 'Alero', 21.4, 5.1, 5.6, 4),
('Bam Adebayo', 'Pívot', 20.0, 3.9, 10.4, 4),
('Tyler Herro', 'Escolta', 20.5, 4.3, 5.2, 4),
('Terry Rozier', 'Base', 18.2, 4.7, 4.1, 4),
('Duncan Robinson', 'Escolta', 12.6, 2.7, 2.8, 4),
('Jaime Jaquez Jr.', 'Alero', 11.9, 2.6, 3.8, 4),
('Kevin Love', 'Ala-pívot', 8.2, 2.4, 6.4, 4),
('Caleb Martin', 'Alero', 10.1, 2.1, 4.3, 4),
('Haywood Highsmith', 'Alero', 6.3, 1.2, 3.4, 4),
('Nikola Jovic', 'Ala-pívot', 7.4, 1.5, 3.8, 4),

-- Mavericks (5)
('Luka Doncic', 'Base', 32.0, 8.2, 8.8, 5),
('Kyrie Irving', 'Base', 25.0, 5.1, 4.7, 5),
('Klay Thompson', 'Escolta', 17.5, 2.4, 3.6, 5),
('P.J. Washington', 'Ala-pívot', 13.0, 2.1, 5.8, 5),
('Daniel Gafford', 'Pívot', 11.4, 1.4, 8.2, 5),
('Dereck Lively II', 'Pívot', 9.1, 1.5, 7.7, 5),
('Tim Hardaway Jr.', 'Escolta', 13.8, 1.8, 3.4, 5),
('Josh Green', 'Escolta', 8.9, 2.2, 3.2, 5),
('Dante Exum', 'Base', 7.7, 2.9, 2.6, 5),
('Maxi Kleber', 'Ala-pívot', 6.1, 1.2, 4.2, 5),

-- Suns (6)
('Kevin Durant', 'Alero', 27.8, 5.2, 6.8, 6),
('Devin Booker', 'Escolta', 27.2, 6.8, 4.6, 6),
('Bradley Beal', 'Escolta', 18.3, 4.9, 3.9, 6),
('Jusuf Nurkic', 'Pívot', 11.5, 3.7, 10.3, 6),
('Grayson Allen', 'Escolta', 13.6, 3.2, 3.9, 6),
('Eric Gordon', 'Escolta', 11.2, 2.3, 2.0, 6),
('Royce O''Neale', 'Alero', 8.1, 2.7, 4.3, 6),
('Bol Bol', 'Ala-pívot', 6.2, 1.0, 3.5, 6),
('Drew Eubanks', 'Pívot', 6.0, 1.3, 4.8, 6),
('Nassir Little', 'Alero', 6.6, 1.1, 2.4, 6),

-- Bucks (7)
('Giannis Antetokounmpo', 'Ala-pívot', 30.0, 6.1, 11.5, 7),
('Damian Lillard', 'Base', 24.2, 6.7, 4.4, 7),
('Khris Middleton', 'Alero', 15.2, 4.8, 4.6, 7),
('Brook Lopez', 'Pívot', 13.0, 1.5, 6.4, 7),
('Bobby Portis', 'Ala-pívot', 13.8, 1.4, 7.5, 7),
('Malik Beasley', 'Escolta', 11.4, 1.4, 3.7, 7),
('Pat Connaughton', 'Escolta', 7.5, 1.8, 3.3, 7),
('Jae Crowder', 'Alero', 6.2, 1.3, 3.1, 7),
('Cameron Payne', 'Base', 7.9, 2.9, 1.8, 7),
('MarJon Beauchamp', 'Alero', 5.1, 0.9, 2.2, 7),

-- 76ers (8)
('Joel Embiid', 'Pívot', 31.0, 5.2, 11.2, 8),
('Tyrese Maxey', 'Base', 25.9, 6.1, 3.7, 8),
('Paul George', 'Alero', 21.6, 4.5, 5.3, 8),
('Kelly Oubre Jr.', 'Alero', 15.2, 1.6, 5.0, 8),
('Tobias Harris', 'Alero', 17.0, 3.0, 6.2, 8),
('Nicolas Batum', 'Alero', 5.9, 2.2, 4.1, 8),
('Kyle Lowry', 'Base', 8.1, 4.2, 3.1, 8),
('De''Anthony Melton', 'Base', 10.8, 3.1, 3.7, 8),
('Mo Bamba', 'Pívot', 6.4, 0.8, 4.5, 8),
('Buddy Hield', 'Escolta', 12.7, 2.8, 3.2, 8),

-- Nuggets (9)
('Nikola Jokic', 'Pívot', 26.4, 9.6, 12.3, 9),
('Jamal Murray', 'Base', 21.2, 6.6, 4.0, 9),
('Michael Porter Jr.', 'Alero', 17.4, 1.6, 5.6, 9),
('Aaron Gordon', 'Ala-pívot', 16.1, 3.4, 6.6, 9),
('Kentavious Caldwell-Pope', 'Escolta', 10.5, 2.4, 2.5, 9),
('Christian Braun', 'Escolta', 7.9, 1.6, 3.2, 9),
('Reggie Jackson', 'Base', 10.2, 3.9, 2.3, 9),
('Peyton Watson', 'Alero', 7.0, 1.3, 3.1, 9),
('Zeke Nnaji', 'Ala-pívot', 5.7, 0.8, 3.8, 9),
('DeAndre Jordan', 'Pívot', 3.4, 0.7, 3.1, 9),

-- Bulls (10)
('DeMar DeRozan', 'Alero', 24.0, 5.1, 4.3, 10),
('Zach LaVine', 'Escolta', 20.4, 3.9, 4.0, 10),
('Nikola Vucevic', 'Pívot', 18.0, 3.2, 10.5, 10),
('Coby White', 'Base', 19.4, 5.1, 4.5, 10),
('Alex Caruso', 'Escolta', 10.2, 3.8, 3.4, 10),
('Patrick Williams', 'Ala-pívot', 10.0, 1.5, 4.3, 10),
('Ayo Dosunmu', 'Base', 11.2, 3.3, 2.8, 10),
('Jevon Carter', 'Base', 5.0, 1.6, 1.5, 10),
('Andre Drummond', 'Pívot', 8.0, 0.8, 9.0, 10),
('Torrey Craig', 'Alero', 6.1, 1.0, 4.0, 10),

-- Knicks (11)
('Jalen Brunson', 'Base', 28.2, 6.7, 3.6, 11),
('Julius Randle', 'Ala-pívot', 22.8, 5.1, 9.2, 11),
('OG Anunoby', 'Alero', 14.6, 2.1, 4.7, 11),
('Mikal Bridges', 'Alero', 18.1, 3.6, 4.4, 11),
('Josh Hart', 'Alero', 10.3, 3.9, 8.1, 11),
('Donte DiVincenzo', 'Escolta', 15.5, 3.0, 4.7, 11),
('Mitchell Robinson', 'Pívot', 8.7, 1.2, 9.4, 11),
('Isaiah Hartenstein', 'Pívot', 8.3, 2.5, 8.0, 11),
('Miles McBride', 'Base', 8.1, 2.2, 1.7, 11),
('Precious Achiuwa', 'Ala-pívot', 7.5, 1.3, 5.8, 11),

-- Clippers (12)
('Kawhi Leonard', 'Alero', 23.7, 3.6, 6.1, 12),
('Paul George', 'Alero', 22.4, 4.7, 5.3, 12),
('James Harden', 'Base', 16.6, 8.5, 5.1, 12),
('Russell Westbrook', 'Base', 11.3, 4.5, 5.0, 12),
('Ivica Zubac', 'Pívot', 13.6, 1.4, 9.2, 12),
('Terance Mann', 'Escolta', 10.5, 2.4, 4.2, 12),
('Norman Powell', 'Escolta', 15.2, 2.6, 2.7, 12),
('P.J. Tucker', 'Alero', 2.0, 0.5, 1.7, 12),
('Amir Coffey', 'Alero', 7.3, 1.2, 2.6, 12),
('Mason Plumlee', 'Pívot', 6.1, 1.8, 5.0, 12),

-- Nets (13)
('Mikal Bridges', 'Alero', 19.6, 3.6, 4.5, 13),
('Cam Thomas', 'Escolta', 21.0, 2.7, 3.2, 13),
('Nic Claxton', 'Pívot', 12.5, 1.9, 9.5, 13),
('Ben Simmons', 'Base', 7.4, 5.8, 5.9, 13),
('Cameron Johnson', 'Alero', 13.4, 2.4, 4.3, 13),
('Dorian Finney-Smith', 'Alero', 8.7, 1.5, 4.8, 13),
('Dennis Schroder', 'Base', 14.1, 6.2, 3.1, 13),
('Royce O''Neale', 'Alero', 7.8, 2.5, 4.2, 13),
('Spencer Dinwiddie', 'Base', 11.8, 5.3, 3.4, 13),
('Day''Ron Sharpe', 'Pívot', 6.2, 1.2, 6.5, 13),

-- Trail Blazers (14)
('Anfernee Simons', 'Base', 22.4, 4.6, 3.3, 14),
('Scoot Henderson', 'Base', 14.2, 5.6, 3.4, 14),
('Jerami Grant', 'Alero', 20.7, 2.9, 3.8, 14),
('Deandre Ayton', 'Pívot', 16.1, 1.7, 10.6, 14),
('Shaedon Sharpe', 'Escolta', 16.0, 2.7, 4.9, 14),
('Malcolm Brogdon', 'Base', 14.8, 4.2, 3.5, 14),
('Matisse Thybulle', 'Escolta', 7.3, 1.5, 2.6, 14),
('Robert Williams III', 'Pívot', 6.8, 1.4, 6.9, 14),
('Jabari Walker', 'Ala-pívot', 7.1, 1.1, 7.4, 14),
('Duop Reath', 'Pívot', 7.4, 1.0, 3.9, 14),

-- Jazz (15)
('Lauri Markkanen', 'Ala-pívot', 23.2, 2.1, 8.2, 15),
('Jordan Clarkson', 'Escolta', 17.1, 5.0, 3.4, 15),
('Collin Sexton', 'Base', 18.2, 4.9, 2.6, 15),
('John Collins', 'Ala-pívot', 15.1, 1.2, 8.4, 15),
('Walker Kessler', 'Pívot', 8.4, 1.1, 7.5, 15),
('Keyonte George', 'Base', 13.0, 4.2, 2.8, 15),
('Taylor Hendricks', 'Ala-pívot', 6.4, 1.0, 4.3, 15),
('Kelly Olynyk', 'Pívot', 9.6, 3.0, 5.2, 15),
('Kris Dunn', 'Base', 7.8, 5.4, 3.0, 15),
('Ochai Agbaji', 'Escolta', 6.2, 1.1, 2.5, 15),

-- Grizzlies (16)
('Ja Morant', 'Base', 25.1, 8.1, 5.6, 16),
('Jaren Jackson Jr.', 'Ala-pívot', 22.5, 2.3, 5.5, 16),
('Desmond Bane', 'Escolta', 23.7, 5.3, 4.4, 16),
('Marcus Smart', 'Base', 12.2, 4.8, 3.2, 16),
('Brandon Clarke', 'Ala-pívot', 10.1, 1.4, 5.8, 16),
('Steven Adams', 'Pívot', 8.6, 2.4, 9.7, 16),
('Santi Aldama', 'Ala-pívot', 10.0, 2.1, 5.7, 16),
('Luke Kennard', 'Escolta', 10.6, 2.9, 3.0, 16),
('Ziaire Williams', 'Alero', 8.2, 1.5, 3.3, 16),
('Xavier Tillman', 'Ala-pívot', 7.4, 2.1, 4.6, 16),

-- Kings (17)
('De''Aaron Fox', 'Base', 26.6, 5.8, 4.4, 17),
('Domantas Sabonis', 'Pívot', 19.6, 8.2, 13.7, 17),
('Keegan Murray', 'Alero', 15.5, 1.7, 5.7, 17),
('DeMar DeRozan', 'Alero', 23.1, 5.0, 4.2, 17),
('Malik Monk', 'Escolta', 15.4, 5.1, 2.9, 17),
('Kevin Huerter', 'Escolta', 11.5, 3.0, 3.4, 17),
('Harrison Barnes', 'Alero', 12.0, 1.7, 3.8, 17),
('Davion Mitchell', 'Base', 8.1, 3.5, 1.7, 17),
('Trey Lyles', 'Ala-pívot', 7.2, 1.1, 4.4, 17),
('Alex Len', 'Pívot', 3.5, 0.8, 2.7, 17),

-- Spurs (18)
('Victor Wembanyama', 'Pívot', 22.1, 3.7, 10.6, 18),
('Devin Vassell', 'Escolta', 19.1, 3.9, 4.1, 18),
('Keldon Johnson', 'Alero', 16.3, 2.7, 5.5, 18),
('Jeremy Sochan', 'Ala-pívot', 11.5, 3.2, 6.4, 18),
('Tre Jones', 'Base', 10.1, 6.1, 3.3, 18),
('Zach Collins', 'Pívot', 11.0, 2.8, 6.2, 18),
('Malaki Branham', 'Escolta', 10.0, 2.0, 2.7, 18),
('Julian Champagnie', 'Alero', 8.4, 1.3, 3.6, 18),
('Sandro Mamukelashvili', 'Ala-pívot', 6.8, 1.3, 4.4, 18),
('Charles Bassey', 'Pívot', 4.6, 0.6, 4.8, 18),

-- Rockets (19)
('Alperen Sengun', 'Pívot', 21.1, 5.0, 9.2, 19),
('Jalen Green', 'Escolta', 19.8, 3.6, 5.1, 19),
('Fred VanVleet', 'Base', 16.9, 8.1, 3.7, 19),
('Dillon Brooks', 'Alero', 13.2, 1.7, 3.3, 19),
('Jabari Smith Jr.', 'Ala-pívot', 13.5, 1.6, 8.1, 19),
('Amen Thompson', 'Base', 9.4, 3.2, 6.1, 19),
('Cam Whitmore', 'Alero', 12.5, 1.1, 3.9, 19),
('Tari Eason', 'Alero', 10.2, 1.3, 6.2, 19),
('Steven Adams', 'Pívot', 7.6, 2.2, 8.8, 19),
('Aaron Holiday', 'Base', 6.4, 1.8, 1.4, 19),

-- Pelicans (20)
('Zion Williamson', 'Ala-pívot', 23.0, 4.9, 5.7, 20),
('Brandon Ingram', 'Alero', 20.8, 5.4, 5.1, 20),
('CJ McCollum', 'Escolta', 20.1, 4.6, 4.3, 20),
('Herb Jones', 'Alero', 11.0, 2.6, 3.6, 20),
('Trey Murphy III', 'Alero', 14.6, 2.2, 4.7, 20),
('Jonas Valanciunas', 'Pívot', 12.3, 2.0, 8.8, 20),
('Jose Alvarado', 'Base', 9.0, 3.6, 2.3, 20),
('Larry Nance Jr.', 'Ala-pívot', 7.8, 2.6, 5.8, 20),
('Dyson Daniels', 'Base', 6.9, 2.7, 3.7, 20),
('Naji Marshall', 'Alero', 9.2, 2.4, 3.8, 20),

-- Timberwolves (21)
('Anthony Edwards', 'Escolta', 26.2, 5.1, 5.4, 21),
('Karl-Anthony Towns', 'Pívot', 21.8, 3.0, 8.3, 21),
('Rudy Gobert', 'Pívot', 13.9, 1.3, 12.9, 21),
('Mike Conley', 'Base', 11.4, 6.2, 2.9, 21),
('Jaden McDaniels', 'Alero', 11.2, 2.1, 3.6, 21),
('Naz Reid', 'Pívot', 13.7, 1.6, 5.4, 21),
('Kyle Anderson', 'Alero', 8.2, 4.3, 3.9, 21),
('Nickeil Alexander-Walker', 'Escolta', 8.8, 2.4, 2.7, 21),
('Josh Okogie', 'Alero', 6.1, 1.3, 3.1, 21),
('Monte Morris', 'Base', 6.4, 2.7, 1.7, 21),

-- Thunder (22)
('Shai Gilgeous-Alexander', 'Base', 30.1, 6.3, 5.5, 22),
('Jalen Williams', 'Alero', 19.1, 4.5, 4.0, 22),
('Chet Holmgren', 'Pívot', 16.7, 2.7, 7.9, 22),
('Josh Giddey', 'Base', 12.5, 4.8, 6.4, 22),
('Luguentz Dort', 'Escolta', 11.0, 1.8, 3.6, 22),
('Isaiah Joe', 'Escolta', 9.5, 1.6, 2.5, 22),
('Cason Wallace', 'Base', 6.8, 2.2, 2.3, 22),
('Kenrich Williams', 'Alero', 6.4, 2.0, 3.4, 22),
('Jaylin Williams', 'Pívot', 6.1, 2.4, 4.5, 22),
('Aaron Wiggins', 'Escolta', 6.7, 1.6, 2.4, 22),

-- Cavaliers (23)
('Donovan Mitchell', 'Escolta', 26.6, 6.1, 5.2, 23),
('Darius Garland', 'Base', 18.0, 6.5, 2.7, 23),
('Evan Mobley', 'Ala-pívot', 16.0, 2.9, 9.4, 23),
('Jarrett Allen', 'Pívot', 16.5, 1.9, 10.6, 23),
('Max Strus', 'Escolta', 12.8, 4.1, 4.7, 23),
('Caris LeVert', 'Escolta', 14.2, 4.0, 4.1, 23),
('Isaac Okoro', 'Alero', 9.4, 1.8, 3.0, 23),
('Georges Niang', 'Ala-pívot', 8.0, 1.2, 3.2, 23),
('Dean Wade', 'Ala-pívot', 6.5, 1.0, 4.2, 23),
('Tristan Thompson', 'Pívot', 4.0, 0.7, 3.8, 23),

-- Pistons (24)
('Cade Cunningham', 'Base', 22.6, 7.5, 4.3, 24),
('Jaden Ivey', 'Base', 16.7, 4.2, 3.8, 24),
('Ausar Thompson', 'Alero', 9.8, 2.7, 6.2, 24),
('Bojan Bogdanovic', 'Alero', 17.2, 2.6, 3.4, 24),
('Isaiah Stewart', 'Ala-pívot', 10.4, 1.6, 7.0, 24),
('Jalen Duren', 'Pívot', 13.8, 2.3, 11.6, 24),
('Alec Burks', 'Escolta', 10.8, 2.1, 2.9, 24),
('Monte Morris', 'Base', 6.9, 3.5, 1.9, 24),
('Simone Fontecchio', 'Alero', 9.2, 1.2, 3.0, 24),
('Killian Hayes', 'Base', 6.0, 3.8, 2.3, 24),

-- Pacers (25)
('Tyrese Haliburton', 'Base', 20.1, 10.9, 3.9, 25),
('Pascal Siakam', 'Ala-pívot', 21.7, 4.6, 7.1, 25),
('Myles Turner', 'Pívot', 17.1, 1.3, 6.8, 25),
('Bennedict Mathurin', 'Escolta', 14.4, 2.0, 4.1, 25),
('Buddy Hield', 'Escolta', 12.8, 2.7, 3.2, 25),
('Andrew Nembhard', 'Base', 9.8, 4.1, 2.7, 25),
('Aaron Nesmith', 'Alero', 10.2, 1.7, 3.9, 25),
('Obi Toppin', 'Ala-pívot', 10.4, 1.7, 3.8, 25),
('T.J. McConnell', 'Base', 10.3, 5.5, 2.7, 25),
('Isaiah Jackson', 'Pívot', 7.3, 1.0, 4.9, 25),

-- Hawks (26)
('Trae Young', 'Base', 26.4, 10.8, 2.9, 26),
('Dejounte Murray', 'Base', 20.5, 5.2, 5.3, 26),
('Bogdan Bogdanovic', 'Escolta', 15.6, 3.1, 3.2, 26),
('De''Andre Hunter', 'Alero', 15.1, 1.6, 3.8, 26),
('Clint Capela', 'Pívot', 11.5, 1.1, 10.7, 26),
('Jalen Johnson', 'Ala-pívot', 14.1, 3.3, 8.2, 26),
('Onyeka Okongwu', 'Pívot', 10.2, 1.6, 6.8, 26),
('Saddiq Bey', 'Alero', 13.7, 1.6, 6.2, 26),
('Kobe Bufkin', 'Base', 5.2, 1.6, 1.4, 26),
('Vit Krejci', 'Escolta', 4.6, 1.2, 1.8, 26),

-- Hornets (27)
('LaMelo Ball', 'Base', 23.1, 8.0, 5.3, 27),
('Brandon Miller', 'Alero', 17.3, 2.4, 4.4, 27),
('Miles Bridges', 'Alero', 21.0, 3.3, 7.3, 27),
('Terry Rozier', 'Base', 19.1, 5.0, 4.1, 27),
('P.J. Washington', 'Ala-pívot', 13.5, 2.3, 5.2, 27),
('Mark Williams', 'Pívot', 11.0, 1.3, 9.0, 27),
('Nick Richards', 'Pívot', 9.2, 0.9, 7.8, 27),
('Cody Martin', 'Escolta', 7.4, 2.3, 3.0, 27),
('Grant Williams', 'Ala-pívot', 10.0, 2.4, 5.1, 27),
('Vasa Micic', 'Base', 7.6, 3.6, 2.0, 27),

-- Magic (28)
('Paolo Banchero', 'Ala-pívot', 22.6, 5.2, 6.9, 28),
('Franz Wagner', 'Alero', 19.7, 3.8, 5.3, 28),
('Jalen Suggs', 'Base', 12.6, 3.1, 3.4, 28),
('Wendell Carter Jr.', 'Pívot', 11.7, 1.9, 7.2, 28),
('Markelle Fultz', 'Base', 11.0, 4.6, 3.2, 28),
('Cole Anthony', 'Base', 11.5, 3.7, 3.9, 28),
('Jonathan Isaac', 'Ala-pívot', 6.8, 1.2, 4.4, 28),
('Moritz Wagner', 'Pívot', 10.4, 1.6, 4.1, 28),
('Gary Harris', 'Escolta', 7.0, 1.2, 2.0, 28),
('Goga Bitadze', 'Pívot', 6.2, 1.4, 5.0, 28),

-- Raptors (29)
('Scottie Barnes', 'Alero', 20.0, 6.1, 8.2, 29),
('RJ Barrett', 'Alero', 19.5, 3.7, 5.2, 29),
('Immanuel Quickley', 'Base', 17.0, 6.1, 4.1, 29),
('Jakob Poeltl', 'Pívot', 12.3, 2.6, 9.0, 29),
('Gary Trent Jr.', 'Escolta', 13.7, 1.8, 2.6, 29),
('Gradey Dick', 'Escolta', 8.6, 1.6, 2.8, 29),
('Bruce Brown', 'Escolta', 11.9, 3.4, 4.2, 29),
('Kelly Olynyk', 'Pívot', 10.2, 3.1, 5.1, 29),
('Chris Boucher', 'Ala-pívot', 9.3, 1.0, 6.0, 29),
('Dennis Schroder', 'Base', 13.6, 5.7, 3.0, 29),

-- Wizards (30)
('Jordan Poole', 'Escolta', 17.8, 4.3, 2.6, 30),
('Kyle Kuzma', 'Ala-pívot', 22.2, 4.2, 6.6, 30),
('Tyus Jones', 'Base', 12.3, 7.3, 2.7, 30),
('Deni Avdija', 'Alero', 14.7, 3.7, 7.2, 30),
('Daniel Gafford', 'Pívot', 11.8, 1.4, 7.8, 30),
('Corey Kispert', 'Alero', 13.2, 2.1, 3.0, 30),
('Bilal Coulibaly', 'Alero', 8.4, 1.7, 4.1, 30),
('Marvin Bagley III', 'Ala-pívot', 10.6, 1.0, 6.4, 30),
('Landry Shamet', 'Escolta', 7.4, 1.8, 1.6, 30),
('Richaun Holmes', 'Pívot', 6.7, 1.0, 4.4, 30);

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
