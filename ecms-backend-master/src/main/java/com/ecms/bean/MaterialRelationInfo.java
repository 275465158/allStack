package com.ecms.bean;


import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author zhengli
 * 2018年9月27日
 * @Description 物料->物料
 */
public class MaterialRelationInfo extends BaseCompanyAndStatusInfo {

	//父物料id
	private Integer materialParentId;

	//子物料id
	private Integer materialChildId;

	//所需数量
	private Float quantity;

	//页面展示参数值字段
	private String materialBomParamValueStr;

	//页面展示参数值字段
	private String materialBomParamNameStr;

	//物料实体
	private MaterialBomInfo materialBomInfo;

	//参数实体
	private List<MaterialBomParamNameInfo> materialBomParamNameInfoList;

	//参数规格实体
	private List<MaterialBomParamSpecificationInfo> materialBomParamSpecificationInfoList;

	//已发料数量
	private Float issueQty;

    //待发料数量
    private Float stayIssueQty;

    //已退料数量
	private Float returnedQty;

	//可生产数量
	private Float produceQty;

	//已使用数量
	private Float usedQty;

	//物料类型
	private String numberType;

	//应领数量
	private Float receiveNum;

	public Integer getMaterialParentId() {
		return materialParentId;
	}

	public void setMaterialParentId(Integer materialParentId) {
		this.materialParentId = materialParentId;
	}

	public Integer getMaterialChildId() {
		return materialChildId;
	}

	public void setMaterialChildId(Integer materialChildId) {
		this.materialChildId = materialChildId;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public MaterialBomInfo getMaterialBomInfo() {
		return materialBomInfo;
	}

	public void setMaterialBomInfo(MaterialBomInfo materialBomInfo) {
		this.materialBomInfo = materialBomInfo;
	}

	public List<MaterialBomParamNameInfo> getMaterialBomParamNameInfoList() {
		return materialBomParamNameInfoList;
	}

	public void setMaterialBomParamNameInfoList(List<MaterialBomParamNameInfo> materialBomParamNameInfoList) {
		this.materialBomParamNameInfoList = materialBomParamNameInfoList;
	}

	public List<MaterialBomParamSpecificationInfo> getMaterialBomParamSpecificationInfoList() {
		return materialBomParamSpecificationInfoList;
	}

	public void setMaterialBomParamSpecificationInfoList(List<MaterialBomParamSpecificationInfo> materialBomParamSpecificationInfoList) {
		this.materialBomParamSpecificationInfoList = materialBomParamSpecificationInfoList;
	}

	public String getMaterialBomParamNameStr() {
		return materialBomParamNameStr;
	}

	public void setMaterialBomParamNameStr(String materialBomParamNameStr) {
		this.materialBomParamNameStr = materialBomParamNameStr;
	}

	public Float getIssueQty() {
		return issueQty;
	}

	public void setIssueQty(Float issueQty) {
		this.issueQty = issueQty;
	}

    public Float getStayIssueQty() {
        return stayIssueQty;
    }

    public void setStayIssueQty(Float stayIssueQty) {
        this.stayIssueQty = stayIssueQty;
    }

	public Float getReturnedQty() {
		return returnedQty;
	}

	public void setReturnedQty(Float returnedQty) {
		this.returnedQty = returnedQty;
	}

	public Float getProduceQty() {
		return produceQty;
	}

	public void setProduceQty(Float produceQty) {
		this.produceQty = produceQty;
	}

	public Float getUsedQty() {
		return usedQty;
	}

	public void setUsedQty(Float usedQty) {
		this.usedQty = usedQty;
	}

	public String getNumberType() {
		return numberType;
	}

	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}

	public Float getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Float receiveNum) {
		this.receiveNum = receiveNum;
	}
}
