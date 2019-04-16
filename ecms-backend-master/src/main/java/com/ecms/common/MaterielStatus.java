/**
 * 
 */
package com.ecms.common;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
public enum MaterielStatus {
	READY(1, "齐备"),
	WAIT(0,"缺货");
	
	MaterielStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
