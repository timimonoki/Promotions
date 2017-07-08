DROP TABLE IF EXISTS countries;
CREATE TABLE `promotions`.`countries` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `country_code_UNIQUE` (`country_code` ASC));

DROP TABLE IF EXISTS `promotions`.`shops`;
CREATE TABLE `promotions`.`shops` (
  `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `identification_nb` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


DROP TABLE IF EXISTS  `promotions`.`catalog`;
CREATE TABLE `promotions`.`catalog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_shops_country_idx` (`shop_id` ASC),
  CONSTRAINT `fk_shops_catalog`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS `promotions`.`images`;
CREATE TABLE `promotions`.`images` (
  `id` INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
  `path` LONGTEXT NOT NULL,
  `type` VARCHAR(45) NULL,
  `catalog_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_catalog_images_idx` (`catalog_id` ASC),
  CONSTRAINT `fk_catalog_images`
    FOREIGN KEY (`catalog_id`)
    REFERENCES `promotions`.`catalog` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

DROP TABLE IF EXISTS `promotions`.`category`;
CREATE TABLE `promotions`.`category` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


DROP TABLE IF EXISTS  `promotions`.`shops_categories`;
CREATE TABLE `promotions`.`shops_categories` (
  `shop_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`shop_id`, `category_id`),
  UNIQUE INDEX `shop_id_UNIQUE` (`shop_id` ASC),
  UNIQUE INDEX `category_id_UNIQUE` (`category_id` ASC),
  CONSTRAINT `fk_shops_`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_categories_`
    FOREIGN KEY (`category_id`)
    REFERENCES `promotions`.`category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS `promotions`.`shop_details`;
CREATE TABLE `promotions`.`shop_details` (
  `id` INT NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `street_number` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `opening_hour` TIME(0) NULL,
  `closing_hour` TIME(0) NULL,
  `country_id` INT NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_shop_shop_details`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

ALTER TABLE `promotions`.`shop_details`
ADD INDEX `fk_shops_countries_idx` (`country_id` ASC);
ALTER TABLE `promotions`.`shop_details`
ADD CONSTRAINT `fk_shops_countries`
  FOREIGN KEY (`country_id`)
  REFERENCES `promotions`.`countries` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


INSERT INTO countries (id, name, country_code) VALUES
(1, "Romania", "RO"),
(2, "Bulgaria", "BG"),
(3, "Hungary", "HU");

INSERT INTO `promotions`.`category` (id, name)  VALUES
(1, "Haine si incaltaminte"),
(2, "Electronice"),
(3, "Produse alimentare"),
(4, "Amenajari interioare"),
(5, "Amenajari exterioare"),
(6, "Vacante"),
(7, "Frumusete");

INSERT INTO `promotions`.`shops` (id, name, identification_nb) VALUES
(1, "Lidl", 'RO 22891860'),
(2, 'Kaufland', 'RO 15991149'),
(3, 'Carrefour', 'RO 9657315'),
(4, 'Profi', 'RO 14624799'),
(5, 'Pepco', 'RO 31477663'),
(6, 'Takko', 'DE 209094382'),
(7, 'H&M', ' RO 27092928');

INSERT INTO `promotions`.`shop_details` (id, street, street_number, state, city, zipcode, opening_hour, closing_hour, country_id, shop_id) VALUES
(1, 'Str. Apalinei', '1', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1, 1),
(2, 'Str. Garii', '23-25', 'Mures', 'Reghin', '545300', '08:00', '21:00', 1, 2),
(3, 'Str. Libertatii', '16', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1, 3),
(4, 'Str. MIhai Viteazul', '27', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1, 4),
(5, 'Str. Garii', '17', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1, 5),
(6, 'Str. Garii', '17', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1, 6),
(7, 'Str. Alexandru Vaida Voevod', '53B', 'Cluj', 'Cluj Napoca', '400000', '10:00', '22:00', 1, 7),
(8, 'Str. Avram Iancu', '492-500', 'Cluj', 'Floresti', '407280', '10:00', '22:00', 1, 7);

INSERT INTO `promotions`.`catalog` (id, name, start_date, end_date, shop_id)
VALUES (1, 'Delicii ca-n MExic', '26.03.2017', '02.07.2017', 1);



