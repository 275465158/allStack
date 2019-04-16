package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="supplier_contact")
public class SupplierContactEntity extends BaseCompanyAndStatusEntity {

	@Column(name="supplier_contact_name")
	private String contactName;
	
	@Column(name="supplier_contact_department_name")
	private String departmentName;
	
	@Column(name="supplier_contact_title")
	private String title;
	
	@Column(name="supplier_contact_mobile")
	private String mobile;
	
	@Column(name="supplier_contact_fax")
	private String fax;
	
	@Column(name="supplier_contact_email")
	private String email;
	
	@Column(name="supplier_id")
	private Integer supplierId;

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	
	
}
