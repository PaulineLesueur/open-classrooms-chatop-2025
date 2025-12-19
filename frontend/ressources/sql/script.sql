CREATE DATABASE chatop;
USE chatop;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `RENTALS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `MESSAGES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

ALTER TABLE `RENTALS` ADD FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `MESSAGES` ADD FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`);

INSERT INTO USERS (email, name, password, created_at, updated_at) VALUES
('test@test.com', 'John DOE', '$2a$10$ThFlk/qkG3iZZwpAfcJy0eNSRivxpF9TDxjvoOH3dDRNBXhWPSryy', NOW(), NOW());

INSERT INTO RENTALS (name, surface, price, picture, description, owner_id, created_at, updated_at) VALUES
('Modern family house', 150, 350, 'modern_family_house.jpg', 'A beautiful and spacious family house in the suburbs.', 1, NOW(), NOW()),
('Charming cottage', 70, 100, 'charming_cottage.jpg', 'Charming little cottage nestled in the countryside.', 1, NOW(), NOW()),
('Mediterranean_Villa', 250, 830, 'mediterranean_villa.jpg', 'Modern and spacious mediterranean villa by the sea.', 1, NOW(), NOW());

