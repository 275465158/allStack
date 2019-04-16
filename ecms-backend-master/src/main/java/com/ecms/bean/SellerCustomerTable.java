package com.ecms.bean;

import com.ecms.common.Pager;

import java.util.List;

public class SellerCustomerTable {

    private List<String> provinces;
    private Pager<SellerCustomerInfo> pager;

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public Pager<SellerCustomerInfo> getPager() {
        return pager;
    }

    public void setPager(Pager<SellerCustomerInfo> pager) {
        this.pager = pager;
    }
}
