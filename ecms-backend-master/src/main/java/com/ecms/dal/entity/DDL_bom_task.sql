CREATE TABLE `bom_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `order_id` varchar(128) NOT NULL COMMENT '订单编号',
  `purchase_id` varchar(128) NOT NULL COMMENT '合同编号',
  `task_progress` varchar(128) DEFAULT NULL COMMENT 'BOM制作进度',
  `draftsman` varchar(128) NOT NULL COMMENT 'BOM制作人',
  `start_date` varchar(45) DEFAULT NULL COMMENT '开始时间',
  `completed_date` varchar(45) DEFAULT NULL COMMENT '完成时间',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `company_id` int(11) NOT NULL COMMENT '所属公司',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '可用状态字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
