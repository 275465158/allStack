CREATE TABLE `equipment_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `equipment_number` varchar(255) NOT NULL COMMENT '设备编号',
  `employee_id` varchar(11) NOT NULL COMMENT '员工工号',
  `employee_name` varchar(32) NOT NULL COMMENT '员工姓名',
  `company_id` int(11) unsigned NOT NULL COMMENT '公司id',
  `status` int(1) unsigned DEFAULT NULL COMMENT '状态',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `equipment_name` varchar(255) NOT NULL COMMENT '设备名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;