CREATE TABLE `product_struct_selector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_struct_selector_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `product_struct_selector_display_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `product_struct_selector_comment` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
