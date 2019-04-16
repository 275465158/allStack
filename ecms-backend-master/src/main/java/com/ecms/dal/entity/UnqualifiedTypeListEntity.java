package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年12月26日
 * @Description 不合格类型明细记录
 */

@Table(name="unqualified_type_list")
public class UnqualifiedTypeListEntity extends BaseCompanyAndStatusEntity {
	//检测id
	@Column(name="check_id")
	private Integer checkId;

	//检测单来源：0:来料，1：生产
	@Column(name="check_source")
	private Integer checkSource;

	//不合格类型名称
	@Column(name="type_name")
	private String typeName;

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getCheckSource() {
		return checkSource;
	}

	public void setCheckSource(Integer checkSource) {
		this.checkSource = checkSource;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
