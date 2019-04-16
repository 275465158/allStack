CREATE TABLE `bom_task_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `task_id` int(11) NOT NULL COMMENT '任务编号',
  `draft_name` varchar(128) DEFAULT NULL COMMENT '需要制作BOM的产品名称',
  `material_id` int(11) DEFAULT NULL COMMENT '选择的对应产品id',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `company_id` int(11) NOT NULL COMMENT '所属公司',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '可用状态字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
