CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `supplier_code` varchar(128) NOT NULL COMMENT '供应商编号',
  `supplier_name` varchar(128) NOT NULL COMMENT '供应商名称',
  `supplier_telphone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `supplier_province` varchar(45) DEFAULT NULL COMMENT '省',
  `supplier_city` varchar(45) DEFAULT NULL COMMENT '地市',
  `supplier_county` varchar(45) DEFAULT NULL COMMENT '县',
  `supplier_address` varchar(128) DEFAULT NULL COMMENT '详细地址',
  `supplier_contact` varchar(45) DEFAULT NULL COMMENT '联系人',
  `supplier_mobile` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `supplier_fax` varchar(45) DEFAULT NULL COMMENT '传真',
  `supplier_default_repertory` int(11) DEFAULT NULL COMMENT '收货仓库',
  `supplier_payment_type` int(11) DEFAULT NULL COMMENT '结算方式',
  `supplier_payment_period` int(11) DEFAULT NULL COMMENT '结算周期',
  `supplier_invoice_type` int(11) DEFAULT NULL COMMENT '发票类型',
  `supplier_shipping_method` int(11) DEFAULT NULL COMMENT '运输方式',
  `supplier_bank_account` varchar(128) DEFAULT NULL COMMENT '银行账户名称',
  `supplier_bank_name` varchar(128) DEFAULT NULL COMMENT '银行账号',
  `supplier_bank` varchar(128) DEFAULT NULL COMMENT '开户银行',
  `supplier_tax_id` varchar(128) DEFAULT NULL COMMENT '税号',
  `company_id` int(11) DEFAULT NULL COMMENT '公司编号',
  `status` int(11) NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `house_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '门牌号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
