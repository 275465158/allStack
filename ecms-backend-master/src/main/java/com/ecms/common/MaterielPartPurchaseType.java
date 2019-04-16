/**
 * 
 */
package com.ecms.common;

import com.ecms.exceptions.CommonBusinessException;

/**
 * @author alan.wu 2018年8月21日
 * @Description 产品部件来源
 */
public enum MaterielPartPurchaseType {
	SELFMAKE(0, "自制"), FROM_PURCHASE(1, "外购");
	private int id;
	private String name;

	MaterielPartPurchaseType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public static String getNameById(int id) {
		for(MaterielPartPurchaseType type: MaterielPartPurchaseType.values()) {
			if(id==type.getId()) {
				return type.getName();
			}
		}
		throw new CommonBusinessException("没找到对应的来源类型, id=" + id);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
