CREATE TABLE `schedule_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `company_id` int(11) unsigned NOT NULL COMMENT '公司ID',
  `schedule_id` varchar(6) NOT NULL COMMENT '月份标识',
  `schedule_date` varchar(10) NOT NULL COMMENT '日期',
  `schedule_week` varchar(11) NOT NULL COMMENT '星期',
  `work_status` int(1) unsigned NOT NULL COMMENT '状态：1：上班，2:休息',
  `work_hours` double(11,1) unsigned NOT NULL COMMENT '工时',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) unsigned NOT NULL COMMENT '状态',
  `schedule_year` int(4) unsigned NOT NULL COMMENT '年份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4718 DEFAULT CHARSET=utf8;