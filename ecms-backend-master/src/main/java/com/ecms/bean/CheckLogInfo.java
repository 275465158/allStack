/**
 * 
 */
package com.ecms.bean;


/**
 * @author zl
 * 2018年8月13日
 * @Description 检测明细
 */
public class CheckLogInfo extends BaseCompanyAndStatusInfo {

	//验收标准明细id
	private Integer acceptanceDetailId;
	
	//检测id
	private Integer checkId;
	
	//检测flag:0:来料检测，1：生产检测
	private Integer checkFlag;
	
	//实测结果
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
