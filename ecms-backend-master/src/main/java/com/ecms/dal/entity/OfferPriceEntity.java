package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;


@Table(name="offer_price")
public class OfferPriceEntity extends BaseCompanyAndStatusEntity {

//	private Integer flag;

	@Column(name="status")
	private Integer status;

	@Column(name="company_id")
	private Integer companyId;

	@Column(name="query_price_id")
	private Integer queryPriceId;

	@Column(name="planned_delivery_date")
	private String plannedDeliveryDate;

	@Column(name="contract_number")
	private String contractNumber;

	@Column(name="other_cost")
	private BigDecimal otherCost;

	@Column(name="sum_cost")
	private BigDecimal sumCost;

	@Column(name="offer_price_payment_type")
	private String paymentType;
	@Column(name="offer_price_payment_rule")
	private String paymentRule;
	@Column(name="offer_price_invoice_type")
	private String invoiceType;
	@Column(name="remark")
	private String remark;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getPlannedDeliveryDate() {
		return plannedDeliveryDate;
	}

	public void setPlannedDeliveryDate(String plannedDeliveryDate) {
		this.plannedDeliveryDate = plannedDeliveryDate;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public BigDecimal getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(BigDecimal otherCost) {
		this.otherCost = otherCost;
	}

	public BigDecimal getSumCost() {
		return sumCost;
	}

	public void setSumCost(BigDecimal sumCost) {
		this.sumCost = sumCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getQueryPriceId() {
		return queryPriceId;
	}

	public void setQueryPriceId(Integer queryPriceId) {
		this.queryPriceId = queryPriceId;
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

//	public Integer getFlag() {
//		return flag;
//	}
//
//	public void setFlag(Integer flag) {
//		this.flag = flag;
//	}
}
