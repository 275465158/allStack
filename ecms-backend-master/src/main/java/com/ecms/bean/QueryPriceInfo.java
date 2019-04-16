package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class QueryPriceInfo extends BaseStatusInfo {

    private String queryCode;
    private String deliveryDate;
    private String queryDate;
    private String offerDeadline;
    private Integer taxInclude;
    private Integer productSum;
    private String otherRequire;
    private boolean pickUpByCustomer;
    private List<ProductListInfo> productListInfos;
//    List<Map<String, Object>>
    private List<Map<String,Object>> productList;
    private Integer companyId;
    private String validityDate;
    //这个是销售方的当前账号使用人员
    private String salesRepresentative;

//    private String offerDateline;
    //询价单状态  是否关闭
    private Integer queryPriceFlag;
    //询价进度
    private Integer offerPriceFlag;
    //记录发起询价的用户
    private Integer supplierId;
    private Integer addressId;
    private String detailAddress;

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

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

    private Integer customerId;
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

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getSalesRepresentative() {
        return salesRepresentative;
    }

    public void setSalesRepresentative(String salesRepresentative) {
        this.salesRepresentative = salesRepresentative;
    }

//    public String getOfferDateline() {
//        return offerDateline;
//    }
//
//    public void setOfferDateline(String offerDateline) {
//        this.offerDateline = offerDateline;
//    }

//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCounty() {
//        return county;
//    }
//
//    public void setCounty(String county) {
//        this.county = county;
//    }
//
//    public String getDetailAddress() {
//        return detailAddress;
//    }
//
//    public void setDetailAddress(String detailAddress) {
//        this.detailAddress = detailAddress;
//    }

    public List<Map<String, Object>> getProductList() {
        return productList;
    }

    public void setProductList(List<Map<String, Object>> productList) {
        this.productList = productList;
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

//    public List<String> getDeliveryAddress() {
//        return deliveryAddress;
//    }
//
//    public void setDeliveryAddress(List<String> deliveryAddress) {
//        this.deliveryAddress = deliveryAddress;
//    }

    public List<ProductListInfo> getProductListInfos() {
        return productListInfos;
    }

    public void setProductListInfos(List<ProductListInfo> productListInfos) {
        this.productListInfos = productListInfos;
    }

    public boolean isPickUpByCustomer() {
        return pickUpByCustomer;
    }

    public void setPickUpByCustomer(boolean pickUpByCustomer) {
        this.pickUpByCustomer = pickUpByCustomer;
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

//    public String getDeliveryAddress() {
//        return deliveryAddress;
//    }
//
//    public void setDeliveryAddress(String deliveryAddress) {
//        this.deliveryAddress = deliveryAddress;
//    }
}
