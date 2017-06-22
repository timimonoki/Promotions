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
  `identification_nb` BIGINT(55) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS `promotions`.`shops_countries`;
CREATE TABLE `promotions`.`shops_countries` (
  `shop_id` INT NOT NULL AUTO_INCREMENT,
  `country_id` INT NOT NULL,
  PRIMARY KEY (`shop_id`, `country_id`),
  UNIQUE INDEX `country_id_UNIQUE` (`country_id` ASC),
  UNIQUE INDEX `shop_id_UNIQUE` (`shop_id` ASC),
  CONSTRAINT `fk_shops`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_countries`
    FOREIGN KEY (`country_id`)
    REFERENCES `promotions`.`countries` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS  `promotions`.`catalogue`;
CREATE TABLE `promotions`.`catalogue` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_shops_country_idx` (`shop_id` ASC),
  CONSTRAINT `fk_shops_catalogue`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


DROP TABLE IF EXISTS `promotions`.`images`;
CREATE TABLE `promotions`.`images` (
  `id` INT UNSIGNED UNIQUE NOT NULL AUTO_INCREMENT,
  `path` LONGTEXT NOT NULL,
  `type` VARCHAR(45) NULL,
  `catalogue_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_catalogue_images_idx` (`catalogue_id` ASC),
  CONSTRAINT `fk_catalogue_images`
    FOREIGN KEY (`catalogue_id`)
    REFERENCES `promotions`.`catalogue` (`id`)
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
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `shop_id_UNIQUE` (`shop_id` ASC),
  CONSTRAINT `fk_shop_shop_details`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotions`.`shops` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);



INSERT INTO countries (id, name, country_code) VALUES (1, "Romania", "RO");

INSERT INTO `promotions`.`category` (id, name)  VALUES
(1, "Haine si incaltaminte"),
(2, "Electronice"),
(3, "Produse alimentare"),
(4, "Amenajari interioare"),
(5, "Amenajari exterioare"),
(6, "Vacante"),
(7, "Frumusete");

INSERT INTO `promotions`.`shops` (id, name, identification_nb) VALUES (1, "Lidl", '10223685');
INSERT INTO `promotions`.`shops_countries` (shop_id, country_id) VALUES (1, 1);
INSERT INTO `promotions`.`shop_details` (id, street, street_number, state, city, zipcode, opening_hour, closing_hour, shop_id) VALUES (1, 'Str. Apalinei', '1', 'Mures', 'Reghin', '545300', '7:30', '21:00', 1);


