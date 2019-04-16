/**
 * 
 */
package com.ecms.bean;


/**
 * @author zhengli
 * 2018年9月3日
 * @Description 工具实体
 */
public class ToolInfo extends BaseCompanyAndStatusInfo {

	//工具编号
	private Integer id;

	//工具编号在页面展示的字段
	private String idToPage;

	//工具类型编号
	private Integer toolTypeId;

	//工具编号
	private String toolCode;

	//工具名称
	private String toolName;

	//工具型号
	private String toolModelNumber;

	//工具最小规格
	private Integer toolMinSpec;

	//工具最大规格
	private Integer toolMaxSpec;

	//前端规格显示字段
	private String toolSpec;

	//前端显示的工具类型字段
	private String toolTypeName;

	//备注
	private String remark;

	//可用状态 1:可用 0:不可用
	private Integer status;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToolTypeId() {
		return toolTypeId;
	}

	public String getIdToPage() { return idToPage; }

	public void setIdToPage(String idToPage) { this.idToPage = idToPage; }

	public void setToolTypeId(Integer toolTypeId) {
		this.toolTypeId = toolTypeId;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolModelNumber() {
		return toolModelNumber;
	}

	public void setToolModelNumber(String toolModelNumber) {
		this.toolModelNumber = toolModelNumber;
	}

	public Integer getToolMinSpec() {
		return toolMinSpec;
	}

	public void setToolMinSpec(Integer toolMinSpec) {
		this.toolMinSpec = toolMinSpec;
	}

	public Integer getToolMaxSpec() {
		return toolMaxSpec;
	}

	public void setToolMaxSpec(Integer toolMaxSpec) {
		this.toolMaxSpec = toolMaxSpec;
	}

	public String getToolSpec() { return toolSpec; }

	public void setToolSpec(String toolSpec) { this.toolSpec = toolSpec; }

	public String getToolTypeName() { return toolTypeName; }

	public void setToolTypeName(String toolTypeName) { this.toolTypeName = toolTypeName; }

	public String getRemark() { return remark; }

	public void setRemark(String remark) { this.remark = remark; }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
