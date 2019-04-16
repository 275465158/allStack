CREATE TABLE `bpm_definition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bpm_definition_model_id` int(11) DEFAULT NULL,
  `bpm_definition_deploy_id` int(11) DEFAULT NULL,
  `bpm_deploy_subject` varchar(255) DEFAULT NULL,
  `bpm_deploy_def_key` varchar(255) DEFAULT NULL,
  `bpm_deploy_task_name_rules` text,
  `bpm_deploy_descp` text,
  `bpm_deploy_xml` text,
  `company_id` int(11) DEFAULT '1',
  `status` int(11) DEFAULT '1',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;