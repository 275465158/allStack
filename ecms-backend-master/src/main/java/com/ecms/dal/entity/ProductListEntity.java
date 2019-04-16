package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name="product_list")
public class ProductListEntity extends BaseCompanyAndStatusEntity {
	@Column(name="purchase_number")
	private Integer purchaseNumber;
	@Column(name="product_standard")
	private String productStandard;
	@Column(name="delivery_date")
	private String deliveryDate;

	@Column(name="company_id")
	private Integer companyId;
	//可用状态 1:可用 2:不可用
	@Column(name="status")
	private Integer status;

	@Column(name="query_price_id")
	private Integer queryId;

	@Column(name="product_id")
	private Integer productId;
	@Column(name="sum_price")
	private BigDecimal sumPrice;
	@Column(name="price")
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

	public void setPurchaseNumber(Integer purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public String getProductStandard() {
		return productStandard;
	}

	public void setProductStandard(String productStandard) {
		this.productStandard = productStandard;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}
}
