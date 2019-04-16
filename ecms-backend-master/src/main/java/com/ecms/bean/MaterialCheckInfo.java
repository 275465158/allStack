package com.ecms.bean;


/**
 * 
 * @author zhengli
 * 2018年9月27日
 * @Description 物料-验收标准
 */
public class MaterialCheckInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//验收标准id
	private Integer checkId;

	//验收标准实体
	private ProductAcceptanceInfo productAcceptanceInfo;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public ProductAcceptanceInfo getProductAcceptanceInfo() {
		return productAcceptanceInfo;
	}

	public void setProductAcceptanceInfo(ProductAcceptanceInfo productAcceptanceInfo) {
		this.productAcceptanceInfo = productAcceptanceInfo;
	}
}
