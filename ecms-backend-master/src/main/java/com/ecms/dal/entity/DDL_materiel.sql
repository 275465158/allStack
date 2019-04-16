//仓库物料
CREATE TABLE `materiel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `materiel_code` varchar(128) NOT NULL,
  `materiel_name` varchar(128) NOT NULL,
  `original_materiel` varchar(128) DEFAULT NULL,
  `shelf_position` varchar(128) DEFAULT NULL,
  `onhand` double DEFAULT NULL,
  `safe_stock` double DEFAULT NULL,
  `min_purchase_num` double DEFAULT NULL,
  `purchase_type` varchar(45) DEFAULT NULL,
  `materiel_unit` varchar(45) DEFAULT NULL,
  `required_by_orders` double DEFAULT NULL,
  `repertory_id` int(11) NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `material_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*CREATE TABLE `materiel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `material_id` int(11) NOT NULL COMMENT '物料id',
  `onhand` double DEFAULT NULL COMMENT '数量',
  `safe_stock` double DEFAULT NULL COMMENT '安全库存',
  `min_purchase_num` double DEFAULT NULL COMMENT '最少采购量',
  `purchase_type` varchar(45) DEFAULT NULL COMMENT '采购方式：数量，批量',
  `materiel_unit` varchar(45) DEFAULT NULL COMMENT '物料单位',
  `required_by_orders` double DEFAULT NULL COMMENT '订单需求量',
  `repertory_id` int(11) NOT NULL COMMENT '所在仓库',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;*/

//物料货物明细
create table `material_inventory_detail` (
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  	`updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  	`status` int(1) DEFAULT '1',
  	`company_id` int(11) NOT NULL,
  	`materiel_id` int(11) not null '0',
  	`shelf_position` varchar(128) DEFAULT NULL COMMENT '放置库位',
  	`supplier_id` int(11) DEFAULT NULL COMMENT '来源供应商',
  	`materiel_batch` varchar(128) DEFAULT NULL COMMENT '批次号',
  	`purchase_cause` varchar(128) DEFAULT NULL COMMENT '采购原因',
  	`origin_of_materiel` varchar(128) DEFAULT NULL COMMENT '物料来源',
  	`materiel_stock_from` varchar(45) DEFAULT NULL COMMENT '入库时间',
  	`materiel_expiry_date` varchar(128) DEFAULT NULL COMMENT '保质期',
 	`materiel_consignee` varchar(45) DEFAULT NULL COMMENT '签收人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;