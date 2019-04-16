CREATE TABLE `product_struct_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_struct_parameter_struct_id` int(11) DEFAULT NULL,
  `product_struct_parameter_select_id` int(11) DEFAULT NULL,
  `product_struct_parameter_order_by` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
