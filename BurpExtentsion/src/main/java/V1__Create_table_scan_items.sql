CREATE TABLE `scan`.`scan_items` (
  `item_id` BIGINT NOT NULL AUTO_INCREMENT,
  `method` VARCHAR(64) NULL,
  `url` TEXT NULL,
  `request_content_type` INT NULL,
  `response_status_code` INT NULL,
  `response_content_type` VARCHAR(64) NULL,
  `request_orig` BLOB NULL,
  `response_orig` BLOB NULL,
  `insert_scan_time` DATE NOT NULL,
  `scan_time` DATE NULL,
  `item_hash` VARBINARY(128) NOT NULL,
  `is_scan` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;