package com.ecms.bean;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SellerCustomerInfo extends BaseStatusInfo {

    private String customerName;
    private String taxNumber;
    private String addressProvince;
    private String addressCity;
    private String addressCounty;
    private String addressDetail;
    private Integer taxInclude;
    private String TemplatetaxInclude;
    private String contacterName;
    private String contacterPhone;
    private String customerPhone;
    private String payType;
    private String payRule;
    private String invoice;
    private String delivery;
    private List<String> address;
    private Integer sellCustomerNumber;

    public Integer getSellCustomerNumber() {
        return sellCustomerNumber;
    }

    public void setSellCustomerNumber(Integer sellCustomerNumber) {
        this.sellCustomerNumber = sellCustomerNumber;
    }

    public String getTemplatetaxInclude() {
		return TemplatetaxInclude;
	}

	public void setTemplatetaxInclude(String templatetaxInclude) {
		TemplatetaxInclude = templatetaxInclude;
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

    public List<String> getAddress() {
        if (CollectionUtils.isEmpty(address) && StringUtils.isNotBlank(addressProvince)) {
            address = new ArrayList<>();
            address.add(addressProvince);
            address.add(addressCity);
            address.add(addressCounty);
        }
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

}
