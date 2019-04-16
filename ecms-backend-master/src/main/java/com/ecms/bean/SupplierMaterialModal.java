package com.ecms.bean;

import java.util.List;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 供应商供货实体相关类
 */
public class SupplierMaterialModal {
	
	//物料清单
	private List<SupplierMaterialInfo> materialList;

	public List<SupplierMaterialInfo> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<SupplierMaterialInfo> materialList) {
		this.materialList = materialList;
	}
	
}
