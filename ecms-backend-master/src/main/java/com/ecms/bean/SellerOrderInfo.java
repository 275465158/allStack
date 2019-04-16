package com.ecms.bean;

import java.util.List;

public class SellerOrderInfo extends BaseCompanyAndStatusInfo {

    private Integer id;
    private String contractId;
    private String orderCode;
    private String owner;
    private Integer customerId;
    private String customerName;
    private String contacterName;
    private String deliveryAddress;
    private String deliveryDate;
    private Integer taxInclude;
    private Integer productNumber;
    private Integer freight;
    private Integer totalMoney;
    private String payType;
    private String payRule;
    private String invoice;
    private String delivery;
    private Integer progress;
    private String createDate;
    private String progressDisplay;
    private String totalMoneyDisplay;
    private String freightDisplay;
    private List<String> dateParam;
    private String fileName;
    private Integer flag;
    private String tradeMark;
    private String sellerStatus;
    private List<Integer> sellerOrderProductIds;
    //0bom制作中  1bom已完成
    private Integer sellerOrderStatus;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProgressDisplay() {
        return progressDisplay;
    }

    public void setProgressDisplay(String progressDisplay) {
        this.progressDisplay = progressDisplay;
    }

    public String getTotalMoneyDisplay() {
        return totalMoneyDisplay;
    }

    public void setTotalMoneyDisplay(String totalMoneyDisplay) {
        this.totalMoneyDisplay = totalMoneyDisplay;
    }

    public String getFreightDisplay() {
        return freightDisplay;
    }

    public void setFreightDisplay(String freightDisplay) {
        this.freightDisplay = freightDisplay;
    }

    public List<String> getDateParam() {
        return dateParam;
    }

    public void setDateParam(List<String> dateParam) {
        this.dateParam = dateParam;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public String getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(String sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public List<Integer> getSellerOrderProductIds() {
        return sellerOrderProductIds;
    }

    public void setSellerOrderProductIds(List<Integer> sellerOrderProductIds) {
        this.sellerOrderProductIds = sellerOrderProductIds;
    }

    public Integer getSellerOrderStatus() {
        return sellerOrderStatus;
    }

    public void setSellerOrderStatus(Integer sellerOrderStatus) {
        this.sellerOrderStatus = sellerOrderStatus;
    }
}
