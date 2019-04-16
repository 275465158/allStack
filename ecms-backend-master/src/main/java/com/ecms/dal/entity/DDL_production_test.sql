CREATE TABLE `production_test` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `production_number` varchar(32) DEFAULT NULL COMMENT '生产单号',
  `production_type` varchar(11) DEFAULT NULL COMMENT '生产类型',
  `product_name` varchar(11) DEFAULT NULL COMMENT '产品名称',
  `production_batch` varchar(11) DEFAULT NULL COMMENT '生产项次',
  `total_number` int(11) DEFAULT NULL COMMENT '数量',
  `unqualified_number` int(11) unsigned DEFAULT NULL COMMENT '不合格数',
  `company_id` int(11) DEFAULT NULL COMMENT '公司编号',
  `production_date` varchar(32) DEFAULT NULL COMMENT '计划生产日期',
  `materiel_status` int(2) unsigned NOT NULL COMMENT '物料状态1、备齐 2、缺失\n\n                备齐：生产单号所需要的物料都到在仓库\n\n                缺失：生产单号所需要的物料不足',
  `latest_production_date` varchar(32) DEFAULT NULL COMMENT '最晚生产日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;