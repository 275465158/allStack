CREATE TABLE `seller_order_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_id` int(11) DEFAULT NULL COMMENT '订单编号',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人',
  `operation` varchar(32) DEFAULT NULL COMMENT '动作',
  `content` varchar(512) DEFAULT NULL COMMENT '操作内容',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_orderId` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售-订单-操作记录';