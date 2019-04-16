CREATE TABLE `supplier_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_contact_name` varchar(45) NOT NULL COMMENT '联系人姓名',
  `supplier_contact_department_name` varchar(45) DEFAULT NULL COMMENT '所属部门',
  `supplier_contact_title` varchar(45) DEFAULT NULL COMMENT '职位',
  `supplier_contact_mobile` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `supplier_contact_fax` varchar(45) DEFAULT NULL COMMENT '传真',
  `supplier_contact_email` varchar(45) DEFAULT NULL COMMENT '电邮',
  `company_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL COMMENT '所属供应商',
  `status` int(1) NOT NULL DEFAULT '1',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
