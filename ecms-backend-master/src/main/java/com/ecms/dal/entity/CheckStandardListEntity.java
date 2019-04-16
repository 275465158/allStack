package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 验收标准明细
 */

@Table(name="check_standard_list")
public class CheckStandardListEntity extends BaseCompanyAndStatusEntity {

    //验收明细编号
    @Column(name="id")
    private Integer id;

    //验收编号
    @Column(name="check_id")
    private Integer checkId;

    //检测名称
    @Column(name="detection_name")
    private String detectionName;

    //验收设备
    @Column(name="check_equipment")
    private String checkEquipment;

    //设备规格
    @Column(name="equipment_spec")
    private String equipmentSpec;

    //单位
    @Column(name="unit")
    private String unit;

    //最小值
    @Column(name="min_mum")
    private BigDecimal minMum;

    //最大值
    @Column(name="max_mum")
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
