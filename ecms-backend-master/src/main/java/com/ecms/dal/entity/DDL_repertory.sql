CREATE TABLE `repertory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `repertory_code` varchar(128) NOT NULL COMMENT '仓库编码',
  `repertory_name` varchar(128) NOT NULL COMMENT '仓库名称',
  `repertory_type` varchar(128) NOT NULL COMMENT '仓库类型 ',
  `repertory_department_id` int(11) NOT NULL COMMENT '仓库所属部门',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `repertory_manager` varchar(128) DEFAULT NULL COMMENT '仓库管理员',
  `company_id` int(11) NOT NULL COMMENT '公司编号',
  `status` int(1) NOT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
