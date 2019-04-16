package com.ecms.bean;

import java.util.List;
import java.util.Map;

/**
 * @author Richie
 * @version 2019/1/21 10:45
 */
public class MaterialSendInfo extends BaseCompanyAndStatusInfo {
    //客户名称
    private String customerName;
    //合同id
    private String contractId;
    //发货数组
    private List<Map<String,Object>> materialSendList;
    //订单编号
    private String orderCode;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public List<Map<String, Object>> getMaterialSendList() {
        return materialSendList;
    }

    public void setMaterialSendList(List<Map<String, Object>> materialSendList) {
        this.materialSendList = materialSendList;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
