package com.ecms.bean;


public class DistrictInfo {
	
	private Integer id;
	//地区名称
	private String name;
	//上级行政区划
	private Integer parentId;

	private String initial;

	private String initials;

	private String pinyin;

	private String extra;

	private String suffix;

	private String code;

	//区号
	private String areaCode;

	private Integer order;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getInitial() {
		return initial;
	}

	public String getInitials() {
		return initials;
	}

	public String getPinyin() {
		return pinyin;
	}

	public String getExtra() {
		return extra;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getCode() {
		return code;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public Integer getOrder() {
		return order;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
