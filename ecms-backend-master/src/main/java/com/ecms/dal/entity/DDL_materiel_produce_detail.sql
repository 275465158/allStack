CREATE TABLE `materiel_produce_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT '1',
  `company_id` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `materiel_produce_id` int(11) DEFAULT NULL COMMENT '发料记录id',
  `materiel_produce_material_id` int(11) DEFAULT NULL COMMENT '物料id',
  `materiel_produce_materiel_pick_qty` float DEFAULT '0' COMMENT '发货数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;