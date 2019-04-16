package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 供应商实体
 */
public class SupplierInfo extends BaseCompanyAndStatusInfo {
	
	//供应商编码
	private String supplierCode;
	
	//供应商名称
	private String supplierName;
	
	//联系电话
	private String supplierTelphone;
	
	//所在省
	private String province;
	
	//市
	private String city;
	
	//县
	private String county;
	
	//具体地址
	private List address;
	
	//联系人
	private String contact;
	
	//电话
	private String mobile;
	
	//传真
	private String fax;
	
	//默认到货仓库
	private Integer defaultRepertory;
	
	/**
	 * 结算方式
	 * @see PurchasePaymentTypeInfo
	 */
	private Integer paymentType;
	
	/**
	 * 结算周期
	 * @see PurchasePaymentPeriodInfo
	 */
	private Integer paymentPeriod;
	
	/**
	 * 发票类型
	 * @see PurchaseInvoiceTypeInfo
	 */
	private Integer invoiceType;
	
	/**
	 * 送货方式
	 * @see PurchaseShippingMethodInfo
	 */
	private Integer shippingMethod;



	private String defaultRepertoryname;

	private String paymentTypename;

	private String invoiceTypename;

	private String paymentPeriodname;

	private String shippingMethodname;
	//银行账户名称
	private String bankAccount;
	
	//账号
	private String bankName;
	
	//开户行
	private String bank;
	
	//税号
	private String taxId;

	//门牌号
	private String houseNumber;

	private Integer supplierNumber;

	public Integer getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(Integer supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public String getDefaultRepertoryname() {
		return defaultRepertoryname;
	}

	public void setDefaultRepertoryname(String defaultRepertoryname) {
		this.defaultRepertoryname = defaultRepertoryname;
	}

	public String getPaymentTypename() {
		return paymentTypename;
	}

	public void setPaymentTypename(String paymentTypename) {
		this.paymentTypename = paymentTypename;
	}

	public String getInvoiceTypename() {
		return invoiceTypename;
	}

	public void setInvoiceTypename(String invoiceTypename) {
		this.invoiceTypename = invoiceTypename;
	}

	public String getPaymentPeriodname() {
		return paymentPeriodname;
	}

	public void setPaymentPeriodname(String paymentPeriodname) {
		this.paymentPeriodname = paymentPeriodname;
	}

	public String getShippingMethodname() {
		return shippingMethodname;
	}

	public void setShippingMethodname(String shippingMethodname) {
		this.shippingMethodname = shippingMethodname;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierTelphone() {
		return supplierTelphone;
	}

	public void setSupplierTelphone(String supplierTelphone) {
		this.supplierTelphone = supplierTelphone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public List getAddress() {
		return address;
	}

	public void setAddress(List address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public Integer getDefaultRepertory() {
		return defaultRepertory;
	}

	public void setDefaultRepertory(Integer defaultRepertory) {
		this.defaultRepertory = defaultRepertory;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(Integer paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Integer getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(Integer shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
}
