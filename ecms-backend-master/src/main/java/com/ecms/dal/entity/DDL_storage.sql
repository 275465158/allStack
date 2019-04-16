CREATE TABLE `storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storage_code` varchar(128) DEFAULT NULL,
  `storage_num` varchar(45) DEFAULT NULL,
  `storage_layer` varchar(45) DEFAULT NULL,
  `storage_row` varchar(45) DEFAULT NULL,
  `storage_column` varchar(45) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `repertory_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `visible` int(1) DEFAULT '1' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
