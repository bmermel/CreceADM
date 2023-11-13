-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CreceDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CreceDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CreceDB` DEFAULT CHARACTER SET utf8 ;
USE `CreceDB` ;

-- -----------------------------------------------------
-- Table `CreceDB`.`edificios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CreceDB`.`edificios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `direccion` VARCHAR(100) NULL,
  `razonSocial` VARCHAR(45) NULL,
  `cuit` INT(11) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CreceDB`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CreceDB`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `apellido` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `rol` VARCHAR(45) NULL,
  `edificios_id` INT NOT NULL,
  PRIMARY KEY (`id`, `edificios_id`),
  INDEX `fk_usuarios_edificios_idx` (`edificios_id` ASC) VISIBLE,
  CONSTRAINT `fk_usuarios_edificios`
    FOREIGN KEY (`edificios_id`)
    REFERENCES `CreceDB`.`edificios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
