package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 供应商联系人
 */
public class SupplierContactInfo extends BaseCompanyAndStatusInfo {
	
	//联系人姓名
	private String contactName;
	
	//部门名称
	private String departmentName;
	
	//职位
	private String title;
	
	//联系电话
	private String mobile;
	
	//传真
	private String fax;
	
	//邮箱
	private String email;
	
	//所属供应商id
	private Integer supplierId;

	List<Map<String,String>> contactTableData;

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

	public List<Map<String, String>> getContactTableData() {
		return contactTableData;
	}

	public void setContactTableData(List<Map<String, String>> contactTableData) {
		this.contactTableData = contactTableData;
	}
}
