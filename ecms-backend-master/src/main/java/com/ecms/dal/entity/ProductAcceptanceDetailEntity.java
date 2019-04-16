package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product_acceptance_detail")
public class ProductAcceptanceDetailEntity extends AbstractEntity {
    @Column(name = "acceptance_id")
    private Integer acceptanceId;

    @Column(name = "check_name")
    private String checkName;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "equipment_specification")
    private String equipmentSpecification;

    private String unit;

    @Column(name = "min_value")
    private String minValue;

    @Column(name = "max_value")
    private String maxValue;

    //设备程序
    @Column(name = "check_method")
    private String checkMethod;

	public Integer getAcceptanceId() {
        return acceptanceId;
    }

    public void setAcceptanceId(Integer acceptanceId) {
        this.acceptanceId = acceptanceId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentSpecification() {
        return equipmentSpecification;
    }

    public void setEquipmentSpecification(String equipmentSpecification) {
        this.equipmentSpecification = equipmentSpecification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }
}