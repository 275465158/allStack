package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Richie
 * @version 2018/11/29 13:36
 */
@Table(name = "material_send")
public class MaterialSendEntity extends BaseCompanyAndStatusEntity {
    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "order_code")
    private String orderCode;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
