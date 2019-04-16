CREATE TABLE `tool_type` (
  `id` int(11) NOT NULL COMMENT '工具类型编号',
  `tool_type_name` varchar(255) NOT NULL COMMENT '工具类型名称',
  `status` int(1) NOT NULL COMMENT '可用状态',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

