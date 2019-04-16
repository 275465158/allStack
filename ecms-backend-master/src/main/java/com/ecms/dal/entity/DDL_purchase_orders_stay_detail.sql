CREATE TABLE `purchase_orders_stay_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_orders_stay_id` int(11) NOT NULL,
  `purchase_orders_id` int(11) NOT NULL,
  `purchase_orders_list_id` int(11) NOT NULL,
  `purchase_ordered_number` float(11,0) unsigned DEFAULT NULL,
  `purchase_orders_stay_detail_status` varchar(45) COLLATE utf8_bin DEFAULT '下单',
  `status` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
