package com.ecms.bean;

import java.util.List;

/***
 * 
 * @author xuefu
 * @Description 多级选择器后台基类
 */
public class CascaderModal {
	//显示标签
	private String label;
	//返回值
	private String value;
	//下级菜单
	private List<CascaderModal> children;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<CascaderModal> getChildren() {
		return children;
	}
	public void setChildren(List<CascaderModal> children) {
		this.children = children;
	}
	
	
}
