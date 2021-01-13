CREATE TABLE `products` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(500) NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `products` (`name`, `description`)
VALUES
('Milk', 'Молоко'),
('Cheese', 'Сыр'),
('Bread', 'Хлеб'),
('Chocolate', 'Шоколад'),
('Meat', 'Мясо'),
('Cherry', 'Вишня'),
('Pie', 'Пирог'),
('Corn flakes', 'Кукурузные хлопья');