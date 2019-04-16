CREATE TABLE `purchasepaymenttype` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `payment_name` varchar(16) NOT NULL COMMENT '结算方式名称',
  `remark` text,
  `status` int(1) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `company_id` int(11) NOT NULL COMMENT '公司编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB