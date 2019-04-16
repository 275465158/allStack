CREATE TABLE `material_parameter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `material_id` int(11) NOT NULL COMMENT '物料id',
  `parameter_name` varchar(128) DEFAULT NULL COMMENT '属性名称，不填结果为[属性1,属性2,....]',
  `parameter_value` varchar(128) NOT NULL COMMENT '属性值',
  `is_visible` int(1) NOT NULL DEFAULT '1' COMMENT '是否显示在列表中',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
