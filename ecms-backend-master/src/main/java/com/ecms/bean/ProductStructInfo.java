package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ProductStructInfo extends BaseCompanyAndStatusInfo {

    private String productName;

    private Integer productTypeId;

    private ProductTypeInfo productTypeInfo;

    private String unitName;

    private List<Map<String, String>> params;

    private List<ProductStructParameterInfo> parameters;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public ProductTypeInfo getProductTypeInfo() {
        return productTypeInfo;
    }

    public void setProductTypeInfo(ProductTypeInfo productTypeInfo) {
        this.productTypeInfo = productTypeInfo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<Map<String, String>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }

    public List<ProductStructParameterInfo> getParameters() {
        return parameters;
    }

    public void setParameters(List<ProductStructParameterInfo> parameters) {
        this.parameters = parameters;
    }
}
