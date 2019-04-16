package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺格式
 */

@Table(name="process_format")
public class ProcessFormatEntity extends BaseCompanyAndStatusEntity {

	//工艺格式编号
	@Column(name="id")
	private Integer id;

	//工艺编号
	@Column(name="process_id")
	private Integer processId;

	//工艺格式名称
	@Column(name="process_format_name")
	private String processFormatName;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getProcessFormatName() {
		return processFormatName;
	}

	public void setProcessFormatName(String processFormatName) {
		this.processFormatName = processFormatName;
	}
}
