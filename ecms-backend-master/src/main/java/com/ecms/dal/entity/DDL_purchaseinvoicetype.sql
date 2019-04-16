CREATE TABLE `purchaseinvoicetype` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(16) NOT NULL COMMENT '发票类型',
  `tax_rate` int(11) NOT NULL COMMENT '税率值',
  `remark` text,
  `status` int(1) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `company_id` int(11) NOT NULL COMMENT '公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB