package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "seller_customer_contacter")
public class SellerCustomerContacterEntity extends AbstractEntity {

    @Column(name="customer_id")
    private Integer customerId;
    private String name;
    private String department;
    @Column(name="post_name")
    private String postName;
    private String phone;
    @Column(name="fax_number")
    private String faxNumber;
    private String email;
    private Integer status;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
