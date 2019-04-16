package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author alan.wu
 * 2018年8月1日
 * @Description 产品部件实体
 */
public class MaterialPartInfo extends BaseInfo {

	//上级产品id
	private Integer parentId;
	
	//上级产品实体
	private MaterialBomInfo parentBom;
	
	//部件物料id
	private Integer materialBomId;
	
	//部件物料实体
	private MaterialBomInfo materialBom;
	
	//下级部件列表
	private List<MaterialPartInfo> childParts;
	
	//原材料编码
	private String originalCode;
	
	//原材料名称
	private String originalName;
	
	//需求数量
	private Float consumeQty;
	
	//计量单位
	private String originalUnit;
	
	//采购来源
	private Integer purchaseType;
	
	//通用性
	private String reusable;

	private Integer status;

	private Integer companyId;

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public MaterialBomInfo getParentBom() {
		return parentBom;
	}
	public void setParentBom(MaterialBomInfo parentBom) {
		this.parentBom = parentBom;
	}
	public Integer getMaterialBomId() {
		return materialBomId;
	}
	public void setMaterialBomId(Integer materialBomId) {
		this.materialBomId = materialBomId;
	}
	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}
	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}
	public List<MaterialPartInfo> getChildParts() {
		return childParts;
	}
	public void setChildParts(List<MaterialPartInfo> childParts) {
		this.childParts = childParts;
	}
	public String getOriginalCode() {
		return originalCode;
	}
	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public Float getConsumeQty() {
		return consumeQty;
	}
	public void setConsumeQty(Float consumeQty) {
		this.consumeQty = consumeQty;
	}
	public String getOriginalUnit() {
		return originalUnit;
	}
	public void setOriginalUnit(String originalUnit) {
		this.originalUnit = originalUnit;
	}
	public Integer getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
	}
	public String getReusable() {
		return reusable;
	}
	public void setReusable(String reusable) {
		this.reusable = reusable;
	}
}
