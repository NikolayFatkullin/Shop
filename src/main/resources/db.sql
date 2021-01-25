CREATE SCHEMA `shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `shop`.`manufacturer`
(
    `id`                   BIGINT      NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(45) NOT NULL,
    `country` VARCHAR(45) NOT NULL,
    `deleted` TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
CREATE TABLE `shop`.`cars`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `manufacturer_id` BIGINT      NOT NULL,
    `model`           VARCHAR(45) NOT NULL,
    `deleted`         TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    INDEX `manufacturer_fk_idx` (`manufacturer_id` ASC) VISIBLE,
    CONSTRAINT `manufacturer_fk`
        FOREIGN KEY (`manufacturer_id`)
            REFERENCES `shop`.`manufacturer` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
CREATE TABLE `shop`.`drivers`
(
    `id`             BIGINT      NOT NULL AUTO_INCREMENT,
    `name`           VARCHAR(45) NOT NULL,
    `license_number` VARCHAR(45) NOT NULL,
    `deleted`        VARCHAR(45) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
CREATE TABLE `shop`.`cars_drivers`
(
    `driver_id` BIGINT NOT NULL,
    `car_id`    BIGINT NOT NULL,
    INDEX `car_id_fk_idx` (`car_id` ASC) VISIBLE,
    INDEX `driver_id_fk_idx` (`driver_id` ASC) VISIBLE,
    CONSTRAINT `car_id_fk`
        FOREIGN KEY (`car_id`)
            REFERENCES `shop`.`cars` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `driver_id_fk`
        FOREIGN KEY (`driver_id`)
            REFERENCES `shop`.`drivers` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
ALTER TABLE `shop`.`drivers`
    ADD COLUMN `login` VARCHAR(45) NOT NULL AFTER `deleted`,
    ADD COLUMN `password` VARCHAR(45) NOT NULL AFTER `login`;

