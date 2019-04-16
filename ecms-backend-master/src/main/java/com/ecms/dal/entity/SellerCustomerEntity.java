package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "seller_customer")
public class SellerCustomerEntity extends BaseCompanyAndStatusEntity {

    @Column(name="customer_name")
    private String customerName;
    @Column(name="tax_number")
    private String taxNumber;
    @Column(name="address_province")
    private String addressProvince;
    @Column(name="address_city")
    private String addressCity;
    @Column(name="address_county")
    private String addressCounty;
    @Column(name="address_detail")
    private String addressDetail;
    @Column(name="tax_include")
    private Integer taxInclude;
    @Column(name="contacter_name")
    private String contacterName;
    @Column(name="contacter_phone")
    private String contacterPhone;
    @Column(name="customer_phone")
    private String customerPhone;
    @Column(name="pay_type")
    private String payType;
    @Column(name="pay_rule")
    private String payRule;
    private String invoice;
    private String delivery;
    @Column(name="sell_customer_number")
    private Integer sellCustomerNumber;

    public Integer getSellCustomerNumber() {
        return sellCustomerNumber;
    }

    public void setSellCustomerNumber(Integer sellCustomerNumber) {
        this.sellCustomerNumber = sellCustomerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Integer getTaxInclude() {
        return taxInclude;
    }

    public void setTaxInclude(Integer taxInclude) {
        this.taxInclude = taxInclude;
    }

    public String getContacterName() {
        return contacterName;
    }

    public void setContacterName(String contacterName) {
        this.contacterName = contacterName;
    }

    public String getContacterPhone() {
        return contacterPhone;
    }

    public void setContacterPhone(String contacterPhone) {
        this.contacterPhone = contacterPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayRule() {
        return payRule;
    }

    public void setPayRule(String payRule) {
        this.payRule = payRule;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
