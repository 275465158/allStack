CREATE TABLE `produce_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) unsigned NOT NULL COMMENT '公司ID',
  `work_month` varchar(11) NOT NULL COMMENT '月份',
  `work_days` int(2) unsigned NOT NULL COMMENT '工作天数',
  `work_hours` double(3,0) unsigned NOT NULL COMMENT '工作时长',
  `work_week_day` int(1) unsigned NOT NULL COMMENT '每周工作天数',
  `work_day_hours` int(2) unsigned NOT NULL COMMENT '每天每人工作时长',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) unsigned NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;