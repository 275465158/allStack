CREATE TABLE `material_part` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `material_parent_id` int(11) NOT NULL COMMENT '父项编号',
  `material_id` int(11) NOT NULL COMMENT 'bom编号',
  `material_part_original_code` varchar(45) DEFAULT NULL COMMENT '原材料编号',
  `material_part_original_name` varchar(45) DEFAULT NULL COMMENT '原材料名称',
  `material_part_consume_qty` float DEFAULT NULL COMMENT '原材料消耗量',
  `material_part_original_unit` varchar(45) DEFAULT NULL COMMENT '原材料单位',
  `material_part_purchase_type` int(1) DEFAULT NULL COMMENT '部件来源',
  `material_part_reusable` varchar(45) DEFAULT NULL COMMENT '通用性',
  `status` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

