
DROP TABLE IF EXISTS countries;
CREATE TABLE `promotii`.`countries` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `country_code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `i_UNIQUE` (`id` ASC));

DROP TABLE IF EXISTS `promotii`.`shops`;
CREATE TABLE `promotii`.`shops` (
  `id` INT UNIQUE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `identification_nb` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `street_nb` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `opening_hour` DATE NULL,
  `closing_hour` DATE NULL,
  `country_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_shops_countries_idx` (`country_id` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_shops_countries`
    FOREIGN KEY (`country_id`)
    REFERENCES `promotii`.`countries` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS  `promotii`.`catalogues`;
CREATE TABLE `promotii`.`catalogues` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `shop_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_shops_country_idx` (`shop_id` ASC),
  CONSTRAINT `fk_shops_country`
    FOREIGN KEY (`shop_id`)
    REFERENCES `promotii`.`shops` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


DROP TABLE IF EXISTS `promotii`.`images`;
CREATE TABLE `promotii`.`promotii` (
  `id` INT UNSIGNED UNIQUE NOT NULL,
  `path` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `catalogue_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_catalogues_images_idx` (`catalogue_id` ASC),
  CONSTRAINT `fk_catalogues_images`
    FOREIGN KEY (`catalogue_id`)
    REFERENCES `promotii`.`catalogues` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);