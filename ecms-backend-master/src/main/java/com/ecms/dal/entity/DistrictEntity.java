package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="district")
public class DistrictEntity {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	/**
	 * 行政区划名称
	 */
	private String name;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	private String initial;
	
	private String initials;
	
	private String pinyin;
	
	private String extra;
	
	private String suffix;
	
	private String code;
	
	@Column(name="area_code")
	private String areaCode;
	
	@Column(name="order_by")
	private Integer order;

	public Integer getId() {
		return id;
	}

	/**
	 * 获取行政区划名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取上级区划编号
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 获取地区拼音首字母
	 * @return
	 */
	public String getInitial() {
		return initial;
	}

	/**
	 * 获取地区拼音首字母集合
	 * @return
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * 获取地区全称拼音
	 * @return
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * 获取附加说明
	 * @return
	 */
	public String getExtra() {
		return extra;
	}

	/**
	 * 获取行政级别
	 * @return
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * 获取行政代码
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 获取行政区号
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * 获取排序
	 * @return
	 */
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
