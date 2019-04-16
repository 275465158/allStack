package com.ecms.bean;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SellerOrderIndex {

    private SellerOrderInfo orderInfo;
    private List<SellerCustomerInfo> customerInfos;
    private List<SellerOrderProductInfo> productInfos;
    private List<SellerPayTypeInfo> payTypeInfos;
    private List<SellerPayRuleInfo> payRuleInfos;
    private List<SellerInvoiceInfo> invoiceInfos;
    private List<SellerDeliveryInfo> deliveryInfos;
    private List<SellerOrderLogInfo> logInfos;
    private List<MaterialBomInfo> materialBomInfos;
    private Integer currentId;
    private MultipartFile file;

    public SellerOrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(SellerOrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<SellerCustomerInfo> getCustomerInfos() {
        return customerInfos;
    }

    public void setCustomerInfos(List<SellerCustomerInfo> customerInfos) {
        this.customerInfos = customerInfos;
    }

    public List<SellerOrderProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<SellerOrderProductInfo> productInfos) {
        this.productInfos = productInfos;
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

    public List<SellerOrderLogInfo> getLogInfos() {
        return logInfos;
    }

    public void setLogInfos(List<SellerOrderLogInfo> logInfos) {
        this.logInfos = logInfos;
    }

    public List<MaterialBomInfo> getMaterialBomInfos() {
        return materialBomInfos;
    }

    public void setMaterialBomInfos(List<MaterialBomInfo> materialBomInfos) {
        this.materialBomInfos = materialBomInfos;
    }

    public Integer getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
