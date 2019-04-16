CREATE TABLE `seller_invoice` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键，序号',
  `company_id` int(11) unsigned NOT NULL COMMENT '公司编号',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `tax_rate` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '税率',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `status` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '状态，1正常，0异常',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售-发票类型';