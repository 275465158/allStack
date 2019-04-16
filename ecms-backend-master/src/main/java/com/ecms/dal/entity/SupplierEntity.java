package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="supplier")
public class SupplierEntity extends BaseCompanyAndStatusEntity {

	@Column(name="supplier_code")
	private String supplierCode;
	
	@Column(name="supplier_name")
	private String supplierName;
	
	@Column(name="supplier_telphone")
	private String supplierTelphone;
	
	@Column(name="supplier_province")
	private String province;
	
	@Column(name="supplier_city")
	private String city;
	
	@Column(name="supplier_county")
	private String county;
	
	@Column(name="supplier_address")
	private String address;
	
	@Column(name="supplier_contact")
	private String contact;
	
	@Column(name="supplier_mobile")
	private String mobile;
	
	@Column(name="supplier_fax")
	private String fax;
	
	@Column(name="supplier_default_repertory")
	private Integer defaultRepertory;
	
	@Column(name="supplier_payment_type")
	private Integer paymentType;
	
	@Column(name="supplier_payment_period")
	private Integer paymentPeriod;
	
	@Column(name="supplier_invoice_type")
	private Integer invoiceType;
	
	@Column(name="supplier_shipping_method")
	private Integer shippingMethod;
	
	@Column(name="supplier_bank_account")
	private String bankAccount;
	
	@Column(name="supplier_bank_name")
	private String bankName;
	
	@Column(name="supplier_bank")
	private String bank;
	
	@Column(name="supplier_tax_id")
	private String taxId;

	@Column(name="house_number")
	private String houseNumber;

	//没有用到
	@Column(name="supplier_number")
	private Integer supplierNumber;

	@Column(name = "supplier_flag")
	private Integer supplierFlag;

	public Integer getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(Integer supplierNumber) {
		this.supplierNumber = supplierNumber;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
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

	public Integer getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(Integer supplierFlag) {
		this.supplierFlag = supplierFlag;
	}
}
