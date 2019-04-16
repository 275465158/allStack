package com.ecms.dal.entity;

import javax.persistence.Table;

@Table(name = "company")
public class CompanyEntity extends AbstractEntity {

	private String name;
	private String address;
	private String logo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
}
