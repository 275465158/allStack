package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 供应商实体相关
 */
public class SupplierModal {
	
	//供应商实体
	private SupplierInfo supplier;
	//供应商供货物料清单
	private List<SupplierMaterialInfo> supplierMaterialList;
	//联系人清单
	private List<SupplierContactInfo> supplierContactList;
	//发票类型
	private List<PurchaseInvoiceTypeInfo> invoiceTypeList;
	//付款周期
	private List<PurchasePaymentPeriodInfo> paymentPeriodList;
	//结算方式
	private List<PurchasePaymentTypeInfo> paymentTypeList;
	//运输方式
	private List<PurchaseShippingMethodInfo> shippingMethodList;
	//仓库列表
	private List<RepertoryInfo> repertoryList;
	public SupplierInfo getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}
	public List<SupplierMaterialInfo> getSupplierMaterialList() {
		return supplierMaterialList;
	}
	public void setSupplierMaterialList(List<SupplierMaterialInfo> supplierMaterialList) {
		this.supplierMaterialList = supplierMaterialList;
	}
	public List<SupplierContactInfo> getSupplierContactList() {
		return supplierContactList;
	}
	public void setSupplierContactList(List<SupplierContactInfo> supplierContactList) {
		this.supplierContactList = supplierContactList;
	}
	public List<PurchaseInvoiceTypeInfo> getInvoiceTypeList() {
		return invoiceTypeList;
	}
	public void setInvoiceTypeList(List<PurchaseInvoiceTypeInfo> invoiceTypeList) {
		this.invoiceTypeList = invoiceTypeList;
	}
	public List<PurchasePaymentPeriodInfo> getPaymentPeriodList() {
		return paymentPeriodList;
	}
	public void setPaymentPeriodList(List<PurchasePaymentPeriodInfo> paymentPeriodList) {
		this.paymentPeriodList = paymentPeriodList;
	}
	public List<PurchasePaymentTypeInfo> getPaymentTypeList() {
		return paymentTypeList;
	}
	public void setPaymentTypeList(List<PurchasePaymentTypeInfo> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}
	public List<PurchaseShippingMethodInfo> getShippingMethodList() {
		return shippingMethodList;
	}
	public void setShippingMethodList(List<PurchaseShippingMethodInfo> shippingMethodList) {
		this.shippingMethodList = shippingMethodList;
	}
	public List<RepertoryInfo> getRepertoryList() {
		return repertoryList;
	}
	public void setRepertoryList(List<RepertoryInfo> repertoryList) {
		this.repertoryList = repertoryList;
	}
	
	
}
