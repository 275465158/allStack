CREATE TABLE `materiel_inventory_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `company_id` int(11) NOT NULL,
  `materiel_id` int(11) NOT NULL DEFAULT '0',
  `shelf_position` varchar(128) DEFAULT NULL COMMENT '放置库位',
  `supplier_id` int(11) DEFAULT NULL COMMENT '来源供应商',
  `materiel_batch` varchar(128) DEFAULT NULL COMMENT '批次号',
  `purchase_cause` varchar(128) DEFAULT NULL COMMENT '采购原因',
  `origin_of_materiel` varchar(128) DEFAULT NULL COMMENT '物料来源',
  `materiel_stock_from` varchar(45) DEFAULT NULL COMMENT '入库时间',
  `materiel_expiry_date` varchar(128) DEFAULT NULL COMMENT '保质期',
  `materiel_consignee` varchar(45) DEFAULT NULL COMMENT '签收人',
  `entry_id` int(11) DEFAULT NULL,
  `qty` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;