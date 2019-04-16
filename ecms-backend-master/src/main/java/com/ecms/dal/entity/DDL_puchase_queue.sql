CREATE TABLE `purchase_queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `purchase_queue_material_id` int(11) NOT NULL COMMENT '物料id',
  `purchase_queue_qty` decimal(10,0) DEFAULT NULL COMMENT '预采购数量',
  `purchase_queue_reason` varchar(128) DEFAULT NULL COMMENT '采购原因',
  `purchase_queue_lastest_time` varchar(45) DEFAULT NULL COMMENT '最晚采购时间',
  `purchase_queue_ordered` int(1) NOT NULL DEFAULT '0' COMMENT '是否已下单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
