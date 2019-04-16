package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="query_price")
public class QueryPriceEntity extends AbstractEntity {
    @Column(name="query_code")
    private String queryCode;

    @Column(name="delivery_date")
    private String deliveryDate;
    @Column(name="tax_include")
    private Integer taxInclude;
    @Column(name="province")
    private String province;
    @Column(name="city")
    private String city;
    @Column(name="county")
    private String county;
    @Column(name="detailAddress")
    private String detailAddress;
    @Column(name="query_date")
    private String queryDate;
    @Column(name="offer_deadline")
    private String offerDeadline;

    @Column(name="product_sum")
    private Integer productSum;
    @Column(name="other_require")
    private String otherRequire;

    @Column(name="delivery_address")
    private String deliveryAddress;

    @Column(name="status")
    private Integer status;

    @Column(name="company_id")
    private Integer companyId;
    @Column(name="query_price_flag")
    private Integer queryPriceFlag;
    @Column(name="offer_price_flag")
    private Integer offerPriceFlag;

    //记录发起询价的用户
    @Column(name="customer_id")
    private Integer customerId;

    @Column(name="supplier_id")
    private Integer supplierId;

    @Column(name="address_id")
    private Integer addressId;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getQueryPriceFlag() {
        return queryPriceFlag;
    }

    public void setQueryPriceFlag(Integer queryPriceFlag) {
        this.queryPriceFlag = queryPriceFlag;
    }

    public Integer getOfferPriceFlag() {
        return offerPriceFlag;
    }

    public void setOfferPriceFlag(Integer offerPriceFlag) {
        this.offerPriceFlag = offerPriceFlag;
    }


    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getOfferDeadline() {
        return offerDeadline;
    }

    public void setOfferDeadline(String offerDeadline) {
        this.offerDeadline = offerDeadline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Integer taxInclude) {
        this.taxInclude = taxInclude;
    }

    public Integer getProductSum() {
        return productSum;
    }

    public void setProductSum(Integer productSum) {
        this.productSum = productSum;
    }

    public String getOtherRequire() {
        return otherRequire;
    }

    public void setOtherRequire(String otherRequire) {
        this.otherRequire = otherRequire;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
