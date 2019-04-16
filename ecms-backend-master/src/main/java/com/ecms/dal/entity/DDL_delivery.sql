CREATE TABLE `delivery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `company_id` int(11) DEFAULT NULL,
  `delivery_code` varchar(45) DEFAULT NULL COMMENT '出库单号',
  `delivery_order_id` int(11) DEFAULT NULL COMMENT '合同编号',
  `delivery_plan_date` varchar(45) DEFAULT NULL COMMENT '计划发货时间',
  `delivery_prepared_by` varchar(45) DEFAULT NULL COMMENT '操作人',
  `delivery_status` varchar(45) DEFAULT NULL COMMENT '发货状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
