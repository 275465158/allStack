/**
 * 
 */
package com.ecms.common;

/**
 * @author alan.wu 2018年8月27日
 * @Description
 */
public enum PickingStatus {
	WAITING(0, "等待中"), COMPLETED(1, "完成");

	protected Integer id;
	protected String name;

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

	PickingStatus(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
}
