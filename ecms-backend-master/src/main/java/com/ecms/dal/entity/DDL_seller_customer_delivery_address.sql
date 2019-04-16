CREATE TABLE `seller_customer_delivery_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序号',
  `customer_id` int(11) unsigned NOT NULL COMMENT '销售-客户ID',
  `name` varchar(32) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '收货人电话',
  `address` varchar(64) DEFAULT NULL COMMENT '收货地址',
  `status` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '状态，1合法，0非法',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售-客户资料-收货地址';