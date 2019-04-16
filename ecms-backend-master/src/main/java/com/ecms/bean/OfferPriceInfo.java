package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OfferPriceInfo extends BaseCompanyAndStatusInfo {


    private Integer flag;
    private Integer status;
    private Integer companyId;
    private Integer queryPriceId;

    private String plannedDeliveryDate;
    private String contractNumber;
    private BigDecimal otherCost;
    private BigDecimal sumCost;
    private String paymentType;
    private String paymentRule;
    private String invoiceType;
    private String remark;
    private String offerCode;
    private Integer contractProgress;
    //-------------------以下拼接字段---------------------

    //    private String productType;
//    private String productName;

    //询价单状态  是否关闭
    private Integer queryPriceFlag;
    private String queryCode;
    private String queryDate;
    //询价进度
    private Integer offerPriceFlag;
    private String customerName;
    private Integer productSum;
    private Integer taxInclude;
    private String otherRequire;
    //地址有关的
//    private String province;
//    private String city;
//    private String county;
    private String detailAddress;
    private String supplierName;
    //要求交货日期
    private String requireDeliveryDate;
    //报价截止日期
    private String offerDeadline;


    public String getRequireDeliveryDate() {
        return requireDeliveryDate;
    }

    public void setRequireDeliveryDate(String requireDeliveryDate) {
        this.requireDeliveryDate = requireDeliveryDate;
    }

    public String getOfferDeadline() {
        return offerDeadline;
    }

    public void setOfferDeadline(String offerDeadline) {
        this.offerDeadline = offerDeadline;
    }

    private List<Map<String,Object>> productList;

    public List<Map<String, Object>> getProductList() {
        return productList;
    }

    public void setProductList(List<Map<String, Object>> productList) {
        this.productList = productList;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Integer getQueryPriceFlag() {
        return queryPriceFlag;
    }

    public void setQueryPriceFlag(Integer queryPriceFlag) {
        this.queryPriceFlag = queryPriceFlag;
    }

    public Integer getProductSum() {
        return productSum;
    }

    public void setProductSum(Integer productSum) {
        this.productSum = productSum;
    }

    public Integer getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Integer taxInclude) {
        this.taxInclude = taxInclude;
    }

    public String getOtherRequire() {
        return otherRequire;
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire;
    }

//    private List<Map<String, Object>> productList;

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public Integer getContractProgress() {
        return contractProgress;
    }

    public void setContractProgress(Integer contractProgress) {
        this.contractProgress = contractProgress;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getOfferPriceFlag() {
        return offerPriceFlag;
    }

    public void setOfferPriceFlag(Integer offerPriceFlag) {
        this.offerPriceFlag = offerPriceFlag;
    }

//    public List<Map<String, Object>> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Map<String, Object>> productList) {
//        this.productList = productList;
//    }


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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
