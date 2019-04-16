package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="inventory_status")
public class InventoryStatusEntity extends BaseCompanyAndStatusEntity {

	@Column(name="status_name")
	private String statusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
