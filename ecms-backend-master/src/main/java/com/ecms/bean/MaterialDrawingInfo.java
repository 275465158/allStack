package com.ecms.bean;


/**
 * 
 * @author zhengli
 * 2018年9月27日
 * @Description 物料-图纸
 */
public class MaterialDrawingInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//图纸id
	private Integer drawingId;

	//图纸实体
	private ProductDrawingInfo productDrawingInfo;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(Integer drawingId) {
		this.drawingId = drawingId;
	}

	public ProductDrawingInfo getProductDrawingInfo() {
		return productDrawingInfo;
	}

	public void setProductDrawingInfo(ProductDrawingInfo productDrawingInfo) {
		this.productDrawingInfo = productDrawingInfo;
	}
}
