package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺类型
 */

@Table(name="process")
public class ProcessEntity extends BaseCompanyAndStatusEntity {

	//工艺id
	@Column(name="id")
	private Integer id;

	//工艺编号
	@Column(name="process_code")
	private String processCode;

	//工艺名称
	@Column(name="process_name")
	private String processName;

	//说明
	@Column(name="remark")
	private String remark;

	//是否是默认工艺
	@Column(name="default_process")
	private Integer defaultProcess;

	//工艺可用状态 1:可用 2:不可用
	@Column(name="status")
	private Integer status;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDefaultProcess() {
		return defaultProcess;
	}

	public void setDefaultProcess(Integer defaultProcess) {
		this.defaultProcess = defaultProcess;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}
}
