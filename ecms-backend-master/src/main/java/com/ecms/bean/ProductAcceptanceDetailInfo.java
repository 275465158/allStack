package com.ecms.bean;

public class ProductAcceptanceDetailInfo extends BaseCompanyAndStatusInfo {
    private Integer acceptanceId;

    private String checkName;

    private String equipmentName;

    private String equipmentSpecification;

    private String unit;

    private String minValue;

    private String maxValue;

    //检测方式
    private String checkMethod;

    //检测结果值
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}