package com.ecms.bean;

import java.util.List;

public class SellerCustomerIndex {

    private SellerCustomerInfo customerInfo;
    private List<SellerCustomerContacterInfo> contacterInfos;
    private List<SellerCustomerDeliveryAddressInfo> addressInfos;
    private List<SellerPayTypeInfo> payTypeInfos;
    private List<SellerPayRuleInfo> payRuleInfos;
    private List<SellerInvoiceInfo> invoiceInfos;
    private List<SellerDeliveryInfo> deliveryInfos;

    public SellerCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(SellerCustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<SellerCustomerContacterInfo> getContacterInfos() {
        return contacterInfos;
    }

    public void setContacterInfos(List<SellerCustomerContacterInfo> contacterInfos) {
        this.contacterInfos = contacterInfos;
    }

    public List<SellerCustomerDeliveryAddressInfo> getAddressInfos() {
        return addressInfos;
    }

    public void setAddressInfos(List<SellerCustomerDeliveryAddressInfo> addressInfos) {
        this.addressInfos = addressInfos;
    }

    public List<SellerPayTypeInfo> getPayTypeInfos() {
        return payTypeInfos;
    }

    public void setPayTypeInfos(List<SellerPayTypeInfo> payTypeInfos) {
        this.payTypeInfos = payTypeInfos;
    }

    public List<SellerPayRuleInfo> getPayRuleInfos() {
        return payRuleInfos;
    }

    public void setPayRuleInfos(List<SellerPayRuleInfo> payRuleInfos) {
        this.payRuleInfos = payRuleInfos;
    }

    public List<SellerInvoiceInfo> getInvoiceInfos() {
        return invoiceInfos;
    }

    public void setInvoiceInfos(List<SellerInvoiceInfo> invoiceInfos) {
        this.invoiceInfos = invoiceInfos;
    }

    public List<SellerDeliveryInfo> getDeliveryInfos() {
        return deliveryInfos;
    }

    public void setDeliveryInfos(List<SellerDeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
    }
}
