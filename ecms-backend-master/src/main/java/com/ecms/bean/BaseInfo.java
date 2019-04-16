package com.ecms.bean;

/**
 * 
 * @author linlvping
 * @Description 包含主键id的基类
 *
 */
public abstract class BaseInfo {
	//主键id字段，不可修改
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
