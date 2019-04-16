CREATE TABLE `delivery_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `delivery_detail_delivery_id` int(11) DEFAULT NULL COMMENT '发货单id',
  `delivery_detail_materiel_id` int(11) DEFAULT NULL COMMENT '物料id',
  `delivery_detail_delivery_qty` float DEFAULT NULL COMMENT '数量',
  `delivery_detail_unit` varchar(45) DEFAULT NULL COMMENT '由物料同步单位数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
