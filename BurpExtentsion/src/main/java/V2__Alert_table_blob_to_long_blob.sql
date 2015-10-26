ALTER TABLE `scan`.`scan_items` 
CHANGE COLUMN `request_orig` `request_orig` LONGBLOB NULL DEFAULT NULL ,
CHANGE COLUMN `response_orig` `response_orig` LONGBLOB NULL DEFAULT NULL ;