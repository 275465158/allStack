package com.ecms.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class EquipmentManageInfo extends BaseInfo {

    /**主键**/
    private Integer id;
    /**设备编号**/
    private Integer equipmentNumber;
    /**设备名称**/
    private String equipmentName;
    /**设备型号**/
    private String equipmentModel;
    /**厂家**/
    private String vender;
    /**所属部门关联id**/
    private Integer deptId;
    /**设备状态，0:正常，1:保养，2:维修，3:停用 **/
    private Integer equipmentStatus;
    /**公司id**/
    private Integer companyId;
    /**出厂日期**/
    private String produceDate;
    /**岗位id**/
    private Integer postId;
    /**设备价格**/
    private BigDecimal equipmentPrice;
    /**用户list**/
    private List<Map<String,Object>> userList;

    public List<Map<String, Object>> getUserList() {
        return userList;
    }

    public void setUserList(List<Map<String, Object>> userList) {
        this.userList = userList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(Integer equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(Integer equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public BigDecimal getEquipmentPrice() {
        return equipmentPrice;
    }

    public void setEquipmentPrice(BigDecimal equipmentPrice) {
        this.equipmentPrice = equipmentPrice;
    }
}
