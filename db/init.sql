CREATE DATABASE IF NOT EXISTS `AndroidProject`;
USE AndroidProject;


-- Creazione delle tabelle

CREATE TABLE `categories` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);

CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(100) UNIQUE NOT NULL,
  `username` VARCHAR(100) UNIQUE,
  `description` TEXT,
  `is_admin` TINYINT(1) DEFAULT 0,
  `password` VARCHAR(255) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `events` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(150) NOT NULL,
  `description` TEXT,
  `date_start` DATETIME NOT NULL,
  `date_end` DATETIME NOT NULL,
  `category_id` INT,
  `location` VARCHAR(150),
  `seats_total` INT,
  `seats_available` INT,
  `created_by` INT,
  `image_url` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`category_id`) REFERENCES `categories`(`id`) ON DELETE SET NULL,
  FOREIGN KEY (`created_by`) REFERENCES `users`(`id`) ON DELETE SET NULL
);

CREATE TABLE `bookings` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  `status` VARCHAR(20) DEFAULT 'active',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`event_id`) REFERENCES `events`(`id`) ON DELETE CASCADE
);

-- Inserimento dati iniziali nelle categorie

INSERT INTO `categories` (`name`) VALUES
('Musica'),
('Sport'),
('Arte e Cultura'),
('Tecnologia'),
('Cibo e Bevande'),
('Salute e Benessere'),
('Viaggi e Turismo'),
('Formazione e Corsi'),
('Cinema e Spettacolo'),
('Eventi per Famiglie')

INSERT INTO `users` (`email`, `username`, `description`, `is_admin`, `password`)
VALUES ('admin@example.com', 'adminuser', 'Amministratore del sistema', 1, '$2b$10$mPV0kHvpGS02WDTFQH5Jc.u1gAurjUDofkqzNfpqZMhPmjtdCt1ua');

-- la password è "123" hashata con bcrypt

INSERT INTO `events` 
(`title`, `description`, `date_start`, `date_end`, `category_id`, `location`, `seats_total`, `seats_available`, `created_by`, `image_url`) 
VALUES
('Concerto Rock', 'Un grande concerto rock per gli amanti della musica.', '2025-12-10 20:00:00', '2025-12-10 23:00:00', 1, 'Stadio Centrale', 500, 500, 1, 'https://example.com/rock_concert.jpg'),
('Mostra d\'Arte Moderna', 'Esposizione di opere di artisti contemporanei.', '2025-12-15 10:00:00', '2025-12-20 18:00:00', 3, 'Museo d\'Arte Moderna', 200, 200, 1, 'https://example.com/arte_moderna.jpg');