CREATE TABLE `product_struct_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_struct_option_selector_id` int(11) DEFAULT NULL,
  `product_struct_option_value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `order_by` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
