CREATE TABLE `supplier_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `supplier_id` int(11) NOT NULL COMMENT '供应商id',
  `material_id` int(11) NOT NULL COMMENT '物料id',
  `price` decimal(10,0) DEFAULT NULL COMMENT '价格',
  `min_qty` decimal(10,0) DEFAULT NULL COMMENT '最少订单数量',
  `purchase_method` varchar(128) DEFAULT NULL COMMENT '采购方式',
  `purchase_days` varchar(128) DEFAULT NULL COMMENT '到货天数',
  `supplier_material_repertory_id` int(11) DEFAULT NULL COMMENT '默认仓库',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
