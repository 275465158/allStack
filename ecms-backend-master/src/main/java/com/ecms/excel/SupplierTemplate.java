package com.ecms.excel;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.poi.hssf.util.HSSFColor;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import com.ecms.bean.PurchaseInvoiceTypeInfo;
import com.ecms.bean.PurchasePaymentPeriodInfo;
import com.ecms.bean.PurchasePaymentTypeInfo;
import com.ecms.bean.PurchaseShippingMethodInfo;

@ExcelSheet(name = "供应商列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
//前面应该是名字，后面是表头颜色
public class SupplierTemplate extends BaseTemplate {

	// 供应商编码
	@NotNull
	@ExcelField(name = "供应商编码")
	private String supplierCode;

	// 供应商名称
	@ExcelField(name = "供应商名称")
	private String supplierName;

	// 联系电话
	@ExcelField(name = "联系电话")
	private String supplierTelphone;

	// 所在省
	@ExcelField(name = "所在省")
	private String province;

	// 市
	@ExcelField(name = "市")
	private String city;

	// 县
	@ExcelField(name = "县")
	private String county;

	// 具体地址
	@ExcelField(name = "具体地址")
	private List address;

	// 联系人
	@ExcelField(name = "联系人")
	private String contact;

	// 电话
	@ExcelField(name = "电话")
	private String mobile;

	// 传真
	@ExcelField(name = "传真")
	private String fax;
	// 默认到货仓库
	@ExcelField(name = "默认到货仓库")
	private String defaultRepertoryname;

	@ExcelField(name = "结算方式")
	private String paymentTypename;


	@ExcelField(name = "付款时间")
	private String paymentPeriodname;

	@ExcelField(name = "发票类型")
	private String invoiceTypename;

	@ExcelField(name = "送货方式")
	private String shippingMethodname;

	// 银行账户名称
	@ExcelField(name = "银行账户名称")
	private String bankAccount;

	// 账号
	@ExcelField(name = "银行账号")
	private String bankName;

	// 开户行
	@ExcelField(name = "开户行")
	private String bank;

	// 税号
	@ExcelField(name = "税号")
	private String taxId;

	// 门牌号
	@ExcelField(name = "门牌号")
	private String houseNumber;

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

	public String getPaymentPeriodname() {
		return paymentPeriodname;
	}

	public void setPaymentPeriodname(String paymentPeriodname) {
		this.paymentPeriodname = paymentPeriodname;
	}

	public String getInvoiceTypename() {
		return invoiceTypename;
	}

	public void setInvoiceTypename(String invoiceTypename) {
		this.invoiceTypename = invoiceTypename;
	}

	public String getShippingMethodname() {
		return shippingMethodname;
	}

	public void setShippingMethodname(String shippingMethodname) {
		this.shippingMethodname = shippingMethodname;
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

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

}
