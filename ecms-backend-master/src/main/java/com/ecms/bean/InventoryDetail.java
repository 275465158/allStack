/**
 * 
 */
package com.ecms.bean;

/**
 * @author alan.wu
 * 2018年8月6日
 * @Description
 */
public class InventoryDetail {
	Class<?> briefClass;	
	Class<?> detailClass;
	BaseCompanyAndStatusInfo brief,detail;
	MaterialBomModal materialBom;
	MaterielInventoryInfo inventoryDetail;

	public Class<?> getBriefClass() {
		return briefClass;
	}
	public void setBriefClass(Class<?> briefClass) {
		this.briefClass = briefClass;
	}
	public Class<?> getDetailClass() {
		return detailClass;
	}
	public void setDetailClass(Class<?> detailClass) {
		this.detailClass = detailClass;
	}
	public BaseCompanyAndStatusInfo getBrief() {
		return brief;
	}
	public void setBrief(BaseCompanyAndStatusInfo brief) {
		this.brief = brief;
	}
	public BaseCompanyAndStatusInfo getDetail() {
		return detail;
	}
	public void setDetail(BaseCompanyAndStatusInfo detail) {
		this.detail = detail;
	}
	public MaterialBomModal getMaterialBom() {
		return materialBom;
	}
	public void setMaterialBom(MaterialBomModal materialBom) {
		this.materialBom = materialBom;
	}
	public MaterielInventoryInfo getInventoryDetail() {
		return inventoryDetail;
	}
	public void setInventoryDetail(MaterielInventoryInfo inventoryDetail) {
		this.inventoryDetail = inventoryDetail;
	}
	
}
