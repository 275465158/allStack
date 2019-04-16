package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年12月26日
 * @Description 检测明细
 */

@Table(name="check_log")
public class CheckLogEntity extends BaseCompanyAndStatusEntity {

	//验收标准明细id
	@Column(name="acceptance_detail_id")
	private Integer acceptanceDetailId;

	//检测id
	@Column(name="check_id")
	private Integer checkId;

	//检测flag:0:来料检测，1：生产检测
	@Column(name="check_flag")
	private Integer checkFlag;

	//实测结果
	@Column(name="check_result")
	private String checkResult;

	public Integer getAcceptanceDetailId() {
		return acceptanceDetailId;
	}

	public void setAcceptanceDetailId(Integer acceptanceDetailId) {
		this.acceptanceDetailId = acceptanceDetailId;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
}
