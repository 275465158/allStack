package com.ecms.bean;

/***
 * 
 * @author linlvping
 * 2018年8月1日
 * @Description 公司信息
 */
public class CompanyInfo {

	private Integer id;
	private String name;
	private String address;
	private String logo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
