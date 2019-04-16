CREATE TABLE `purchase_orders` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `purchase_code` varchar(255) DEFAULT NULL COMMENT '采购编号',
  `purchase_orders_stay_id` int(11) unsigned DEFAULT NULL COMMENT '待采购单id ',
  `inbound_status` int(1) unsigned NOT NULL DEFAULT '0' COMMENT '入库状态 0：待收货，1：待检测，2：待入库，3：已入库',
  `supplier_id` int(11) unsigned DEFAULT NULL COMMENT '供应商id',
  `purchase_price_total` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '采购总价（实际采购数量*下单时的采购单价）',
  `arrive_date` varchar(24) DEFAULT NULL COMMENT '预计到货时间（下单时间+预计到货天数）理想状态下到货的时间',
  `purchase_person` varchar(255) DEFAULT NULL COMMENT '采购人',
  `purchase_date` varchar(255) DEFAULT NULL COMMENT '采购时间',
  `purchase_reason` varchar(255) DEFAULT NULL COMMENT '采购原因',
  `company_id` int(11) unsigned NOT NULL COMMENT '公司id',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `repertory_id` int(11) unsigned DEFAULT NULL COMMENT '仓库id （废弃字段）',
  `flag` int(1) DEFAULT NULL COMMENT '0:根据待采购生成的  1:手工创建的',
  `material_code` varchar(24) DEFAULT NULL COMMENT '物料编码 （废弃字段）',
  `purchase_qty` float(11,2) DEFAULT NULL COMMENT '采购数量 （废弃字段）',
  `unqualified_qty` float(11,2) unsigned DEFAULT '0.00' COMMENT '不合格数 （废弃字段）',
  `stay_pur_qty` float(11,2) unsigned DEFAULT '0.00' COMMENT '待收货数量（实际采购数量） （废弃字段）',
  `purchase_price` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '采购单价（下单实际价格）（废弃字段）',
  `received_qty` float(11,2) DEFAULT '0.00' COMMENT '已收货数量 （废弃字段）',
  `un_received_qty` float(11,2) DEFAULT NULL COMMENT '未收货数量 （废弃字段）',
  `stay_inbound_qty` float(11,2) unsigned DEFAULT '0.00' COMMENT '待收货数量（合格数）（废弃字段）（废弃字段）',
  `material_id` bigint(11) unsigned DEFAULT NULL COMMENT '物料id （废弃字段）',
  `material_name` varchar(24) DEFAULT NULL COMMENT '物料名称 （废弃字段）',
  `purchase_number` int(11) DEFAULT NULL COMMENT '列表编号、每个公司都从1开始',
  `supplier_contact` varchar(255) DEFAULT NULL COMMENT '供应商联系人',
  `supplier_number` varchar(255) DEFAULT NULL COMMENT '供应商联系电话号码',
  `supplier_phone` varchar(255) DEFAULT NULL COMMENT '供应商联系手机号码',
  `supplier_fax_number` varchar(255) DEFAULT NULL COMMENT '供应商联系传真号码',
  `supplier_email` varchar(255) DEFAULT NULL COMMENT '供应商联系邮箱',
  `purchase_phone_umber` varchar(255) DEFAULT NULL COMMENT '采购电话号码',
  `purchase_phone` varchar(255) DEFAULT NULL COMMENT '采购手机号码',
  `purchase_fax_number` varchar(255) DEFAULT NULL COMMENT '采购传真号码',
  `purchase_email` varchar(255) DEFAULT NULL COMMENT '采购电子邮箱',
  `if_tax` int(1) DEFAULT '1' COMMENT '是否含税 1：含税  0：不含税',
  `if_freight` int(1) DEFAULT '1' COMMENT '是否含运费 1：含运费  0：不含运费',
  `freight` decimal(11,2) DEFAULT NULL COMMENT '运费',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `purchase_agreement` varchar(2000) DEFAULT NULL COMMENT '采购协议',
  `delivery_address_id` int(11) DEFAULT NULL COMMENT '收货地址id',
  `if_pick_up` int(1) DEFAULT NULL COMMENT '是否自提 1：自提  2：不自提',
  `product_price_total` decimal(10,2) DEFAULT NULL COMMENT '产品总价',
  `purchase_orders_review_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;