CREATE TABLE `seller_customer_contacter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增序号',
  `customer_id` int(11) unsigned NOT NULL COMMENT '销售-客户ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '联系人姓名',
  `department` varchar(32) DEFAULT NULL COMMENT '部门',
  `post_name` varchar(32) DEFAULT NULL COMMENT '职位',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `fax_number` varchar(16) DEFAULT NULL COMMENT '传真号码',
  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
  `status` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '状态，1合法，0非法',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;