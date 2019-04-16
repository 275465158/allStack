package com.ecms.bean;

import java.util.List;

public class SellerCustomerAdditional {

    private List<SellerCustomerContacterInfo> contacterInfos;
    private List<SellerCustomerDeliveryAddressInfo> addressInfos;

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
}
