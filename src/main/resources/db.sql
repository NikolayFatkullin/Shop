CREATE SCHEMA `shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `shop`.`manufacturer`
(
    `id`                   BIGINT      NOT NULL AUTO_INCREMENT,
    `manufacturer_name`    VARCHAR(45) NOT NULL,
    `manufacturer_country` VARCHAR(45) NOT NULL,
    `manufacturer_deleted` TINYINT     NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
