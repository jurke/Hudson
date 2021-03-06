DROP DATABASE IF EXISTS hudson;

CREATE DATABASE hudson;
USE hudson;
CREATE TABLE `t_buildinfo` (
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
`Title` VARCHAR(100) NOT NULL ,
PRIMARY KEY (`ID`),INDEX(`Title`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_runningsteps` (
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
`Step` VARCHAR(100) NOT NULL ,
PRIMARY KEY (`ID`),UNIQUE(`Step`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_messages` (
`ID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
`Message` TEXT NOT NULL ,
PRIMARY KEY (`ID`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_buildlog` (
`BuildID` INT UNSIGNED NOT NULL ,
`RunStepID` INT UNSIGNED NOT NULL ,
`MessID` INT UNSIGNED NOT NULL ,
PRIMARY KEY (`BuildID`,`RunStepID`,`MessID`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `t_buildlog`  ADD CONSTRAINT `FK_t_buildlog_t_buildinfo` FOREIGN KEY (`BuildID`) REFERENCES `t_buildinfo` (`ID`) ON UPDATE CASCADE ON DELETE CASCADE,  ADD CONSTRAINT `FK_t_buildlog_t_runningsteps` FOREIGN KEY (`RunStepID`) REFERENCES `t_runningsteps` (`ID`) ON UPDATE CASCADE ON DELETE NO ACTION,  ADD CONSTRAINT `FK_t_buildlog_t_messages` FOREIGN KEY (`MessID`) REFERENCES `t_messages` (`ID`) ON UPDATE CASCADE ON DELETE NO ACTION;

INSERT INTO `t_messages` (`ID`,`Message`) VALUES ('0', '');

DELIMITER //
CREATE FUNCTION `pr_ExistTable`(`TableName` vARCHAR(255)) RETURNS tinyint(1)
    COMMENT 'Bestimmt die Existenz einer Tabelle mittels Tabellenname'
BEGIN
RETURN (SELECT COUNT(*)=1 AS Exist FROM `information_schema`.`TABLES` WHERE `information_schema`.`TABLES`.TABLE_SCHEMA=DATABASE() AND `information_schema`.`TABLES`.TABLE_NAME=TableName);
END//
DELIMITER ;
