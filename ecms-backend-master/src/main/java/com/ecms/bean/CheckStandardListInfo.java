/**
 * 
 */
package com.ecms.bean;


import java.math.BigDecimal;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 验收标准明细实体
 */
public class CheckStandardListInfo extends BaseInfo {

    //验收明细标号
    private Integer id;

    //验收编号
    private Integer checkId;

    //检测名称
    private String detectionName;

    //验收设备
    private String checkEquipment;

    //设备规格
    private String equipmentSpec;

    //单位
    private String unit;

    //最小值
    private BigDecimal minMum;

    //最大值
    private BigDecimal maxMum;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public String getDetectionName() {
        return detectionName;
    }

    public void setDetectionName(String detectionName) {
        this.detectionName = detectionName;
    }

    public String getCheckEquipment() {
        return checkEquipment;
    }

    public void setCheckEquipment(String checkEquipment) {
        this.checkEquipment = checkEquipment;
    }

    public String getEquipmentSpec() {
        return equipmentSpec;
    }

    public void setEquipmentSpec(String equipmentSpec) {
        this.equipmentSpec = equipmentSpec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getMinMum() {
        return minMum;
    }

    public void setMinMum(BigDecimal minMum) {
        this.minMum = minMum;
    }

    public BigDecimal getMaxMum() {
        return maxMum;
    }

    public void setMaxMum(BigDecimal maxMum) {
        this.maxMum = maxMum;
    }
}
