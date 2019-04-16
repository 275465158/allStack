CREATE TABLE `picking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `company_id` int(11) DEFAULT NULL,
  `picking_code` varchar(45) DEFAULT NULL COMMENT '出库单号',
  `picking_production_id` int(11) DEFAULT NULL COMMENT '生产单号',
  `picking_useage` varchar(45) DEFAULT NULL COMMENT '领料用途',
  `picking_pick_date` varchar(45) DEFAULT NULL COMMENT '领料时间',
  `picking_prepared_by` varchar(45) DEFAULT NULL COMMENT '操作人',
  `picking_picked_by` varchar(45) DEFAULT NULL COMMENT '领料人',
  `picking_status` varchar(45) DEFAULT NULL COMMENT '领料状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;