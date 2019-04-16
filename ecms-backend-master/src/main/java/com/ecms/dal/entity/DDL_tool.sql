CREATE TABLE `tool` (
  `id` int(11) NOT NULL COMMENT '工具编号',
  `tool_type_id` int(11) DEFAULT NULL COMMENT '工具类型编号',
  `tool_name` varchar(255) DEFAULT NULL COMMENT '工具名称',
  `tool_model_number` varchar(255) DEFAULT NULL COMMENT '工具型号',
  `tool_min_spec` int(11) DEFAULT NULL COMMENT '工具最小规格',
  `tool_max_spec` int(11) DEFAULT NULL COMMENT '工具最大规格',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '可用状态字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
