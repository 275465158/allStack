CREATE TABLE `material_processing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT NULL,
  `material_processing_material_id` int(11) NOT NULL COMMENT '物料id',
  `material_processing_name` varchar(45) DEFAULT NULL COMMENT '制程名称',
  `material_processing_speciality` varchar(45) DEFAULT NULL COMMENT '特性',
  `material_processing_cost_time` varchar(45) DEFAULT NULL COMMENT '标准工时',
  `material_processing_special_equipment` varchar(45) DEFAULT NULL COMMENT '加工设备',
  `material_processing_ordered_by` varchar(45) DEFAULT NULL,
  `material_processing_comment` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
