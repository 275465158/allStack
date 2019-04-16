CREATE TABLE `seller_order_product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键，序号',
  `order_id` int(11) unsigned NOT NULL COMMENT '订单ID',
  `product_id` int(11) unsigned NOT NULL COMMENT '产品ID',
  `product_name` varchar(32) NOT NULL DEFAULT '' COMMENT '产品名称',
  `product_number` int(11) unsigned DEFAULT NULL COMMENT '产品数量',
  `unit` varchar(8) DEFAULT NULL COMMENT '单位',
  `delivery_date` varchar(16) DEFAULT NULL COMMENT '交货日期',
  `retail_price` int(11) unsigned DEFAULT NULL COMMENT '零售价',
  `tax_rate` int(11) unsigned DEFAULT NULL COMMENT '税率',
  `tax_include` int(11) unsigned DEFAULT NULL COMMENT '是否含税，1是，0不是',
  `untaxed_price` int(11) unsigned DEFAULT NULL COMMENT '未税价格',
  `taxes` int(11) unsigned DEFAULT NULL COMMENT '税金',
  `status` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '状态，1正常，0异常',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_orderId` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;