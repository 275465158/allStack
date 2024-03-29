package com.ecms.dal.entity;

import javax.persistence.Column;

public class BaseCompanyAndStatusEntity extends AbstractEntity {

	@Column(name = "company_id")
	protected Integer companyId;
	protected Integer status;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
