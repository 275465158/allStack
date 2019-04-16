/**
 * 
 */
package com.ecms.bean;


import java.util.List;
import java.util.Map;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工艺实体
 */
public class ProcessInfo extends BaseCompanyAndStatusInfo {

	//工艺编号
	private Integer id;

	//工艺编号
	private String processCode;

	//工艺名称
	private String processName;

	//说明
	private String remark;

	//是否默认
	private Integer defaultProcess;

	//状态 1：启用 0：停用
	private Integer status;

	private List<Map<String,Object>> toolTypeSelectList;

	private List<String> processOtherFormatList;

	private List<ProcessFormatInfo> processFormatInfoList;

	private String contentJson;

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

	public String getRemark() { return remark; }

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDefaultProcess() {
		return defaultProcess;
	}

	public void setDefaultProcess(Integer defaultProcess) {
		this.defaultProcess = defaultProcess;
	}

	public Integer getStatus() { return status; }

	public void setStatus(Integer status) { this.status = status; }

	public List<Map<String, Object>> getToolTypeSelectList() {
		return toolTypeSelectList;
	}

	public void setToolTypeSelectList(List<Map<String, Object>> toolTypeSelectList) {
		this.toolTypeSelectList = toolTypeSelectList;
	}

	public List<String> getProcessOtherFormatList() {
		return processOtherFormatList;
	}

	public void setProcessOtherFormatList(List<String> processOtherFormatList) {
		this.processOtherFormatList = processOtherFormatList;
	}

	public List<ProcessFormatInfo> getProcessFormatInfoList() {
		return processFormatInfoList;
	}

	public void setProcessFormatInfoList(List<ProcessFormatInfo> processFormatInfoList) {
		this.processFormatInfoList = processFormatInfoList;
	}

	public String getContentJson() {
		return contentJson;
	}

	public void setContentJson(String contentJson) {
		this.contentJson = contentJson;
	}
}
