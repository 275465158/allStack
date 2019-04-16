/**
 * 
 */
package com.ecms.bean;

import java.math.BigDecimal;

public class ProductListInfo extends BaseInfo {
	//页面展示的参数名
	private String materialBomParamNameStr;
	//物料名称（产品名称）
	private String materialName;

	//计量单位
	private String materialUnit;
	//----------------------拼接-------------------------

	private Integer purchaseNumber;

	private String productStandard;

	private String deliveryDate;

	private Integer companyId;

	private Integer status;

	private String productType;
	private Integer productId;
	private Integer queryId;
	private Integer taxRate;
	private BigDecimal sumPrice;
	private BigDecimal 	price;

	public BigDecimal getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(BigDecimal sumPrice) {
		this.sumPrice = sumPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	public String getMaterialBomParamNameStr() {
		return materialBomParamNameStr;
	}

	public void setMaterialBomParamNameStr(String materialBomParamNameStr) {
		this.materialBomParamNameStr = materialBomParamNameStr;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPurchaseNumber() {
		return purchaseNumber;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setPurchaseNumber(Integer purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public String getProductStandard() {
		return productStandard;
	}

	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}

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
