package com.ecms.bean;


/**
 * 
 * @author zhengli
 * 2018年10月11日
 * @Description 物料-参数规格
 */
public class MaterialBomParamSpecificationInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//物料参数规格名称id
	private Integer materialParamSpecificationId;

	//物料参数规格值
	private String materialParamSpecificationValue;

	private ProductParameterSpecificationInfo productParameterSpecificationInfo;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialParamSpecificationId() {
		return materialParamSpecificationId;
	}

	public void setMaterialParamSpecificationId(Integer materialParamSpecificationId) {
		this.materialParamSpecificationId = materialParamSpecificationId;
	}

	public String getMaterialParamSpecificationValue() {
		return materialParamSpecificationValue;
	}

	public void setMaterialParamSpecificationValue(String materialParamSpecificationValue) {
		this.materialParamSpecificationValue = materialParamSpecificationValue;
	}

	public ProductParameterSpecificationInfo getProductParameterSpecificationInfo() {
		return productParameterSpecificationInfo;
	}

	public void setProductParameterSpecificationInfo(ProductParameterSpecificationInfo productParameterSpecificationInfo) {
		this.productParameterSpecificationInfo = productParameterSpecificationInfo;
	}
}
