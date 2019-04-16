CREATE TABLE `material_bom` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动变号',
  `material_code` varchar(128) NOT NULL COMMENT '物料编码',
  `end_product_code` varchar(128) NOT NULL COMMENT '产品编码',
  `material_name` varchar(128) NOT NULL COMMENT '物料名称',
  `material_unit` varchar(128) DEFAULT NULL COMMENT '适用单位',
  `original_material` varchar(128) DEFAULT NULL,
  `material_bom_created` varchar(45) DEFAULT NULL,
  `author` varchar(45) NOT NULL COMMENT '制作人',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `company_id` int(11) NOT NULL COMMENT '所属公司',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '可用状态字',
  `editable` int(1) NOT NULL DEFAULT '1' COMMENT '是否可编辑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;