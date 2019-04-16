CREATE TABLE `materiel_produce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT '1',
  `company_id` varchar(45) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `materiel_produce_number` varchar(45) DEFAULT NULL COMMENT '出库单编号',
  `materiel_produce_product_id` int(11) DEFAULT NULL COMMENT '生产订单id',
  `materiel_produce_materiel_status` varchar(45) DEFAULT NULL COMMENT '物料状态',
  `materiel_produce_creator` varchar(45) DEFAULT NULL COMMENT '制表人',
  `materiel_produce_repertory_id` int(11) DEFAULT NULL COMMENT '仓库编号',
  `materiel_produce_picked_date` varchar(45) DEFAULT NULL COMMENT '领料时间',
  `materiel_produce_picked_signer` varchar(45) DEFAULT NULL COMMENT '领料人',
  `materiel_produce_pick_status` varchar(45) DEFAULT NULL COMMENT '领料状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
