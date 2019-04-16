package com.ecms.dal.entity;

import org.checkerframework.checker.units.qual.C;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "seller_order")
public class SellerOrderEntity extends BaseCompanyAndStatusEntity {

    @Column(name = "contract_id")
    private String contractId;
    @Column(name = "order_code")
    private String orderCode;
    private String owner;
    @Column(name = "create_date")
    private String createDate;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "contacter_name")
    private String contacterName;
    @Column(name = "delivery_address")
    private String deliveryAddress;
    @Column(name = "delivery_date")
    private String deliveryDate;
    @Column(name = "tax_include")
    private Integer taxInclude;
    @Column(name = "product_number")
    private Integer productNumber;
    private Integer freight;
    @Column(name = "total_money")
    private Integer totalMoney;
    @Column(name = "pay_type")
    private String payType;
    @Column(name = "pay_rule")
    private String payRule;
    private String invoice;
    private String delivery;
    private Integer progress;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "trade_mark")
    private String tradeMark;
    @Column(name = "seller_order_status")
    private Integer sellerOrderStatus;


    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContacterName() {
        return contacterName;
    }

    public void setContacterName(String contacterName) {
        this.contacterName = contacterName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public Integer getSellerOrderStatus() {
        return sellerOrderStatus;
    }

    public void setSellerOrderStatus(Integer sellerOrderStatus) {
        this.sellerOrderStatus = sellerOrderStatus;
    }
}
