package com.ecms.bean;


/**
 * 
 * @author zhengli
 * 2018年10月11日
 * @Description 物料-参数
 */
public class MaterialBomParamNameInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//物料参数名称id
	private Integer materialParamNameId;

	//物料参数值
	private String materialParamNameValue;

	private ProductParameterNameInfo productParameterNameInfo;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialParamNameId() {
		return materialParamNameId;
	}

	public void setMaterialParamNameId(Integer materialParamNameId) {
		this.materialParamNameId = materialParamNameId;
	}

	public String getMaterialParamNameValue() {
		return materialParamNameValue;
	}

	public void setMaterialParamNameValue(String materialParamNameValue) {
		this.materialParamNameValue = materialParamNameValue;
	}

	public ProductParameterNameInfo getProductParameterNameInfo() {
		return productParameterNameInfo;
	}

	public void setProductParameterNameInfo(ProductParameterNameInfo productParameterNameInfo) {
		this.productParameterNameInfo = productParameterNameInfo;
	}
}
