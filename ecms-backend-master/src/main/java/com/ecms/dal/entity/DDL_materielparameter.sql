CREATE TABLE `materielparameter` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `materiel_id` int(11) NOT NULL,
  `parameter_name` varchar(128) DEFAULT NULL,
  `parameter_value` varchar(128) NOT NULL,
  `is_visible` int(1) NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB