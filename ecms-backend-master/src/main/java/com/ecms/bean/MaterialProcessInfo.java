package com.ecms.bean;

/**
 * 
 * @author zhengli
 * 2018年9月27日
 * @Description 产品制程实体
 */
public class MaterialProcessInfo extends BaseCompanyAndStatusInfo {

	//物料id
	private Integer materialId;

	//制程名称
	private String processName;
	
	//人数
	private Integer people;

	//额定工时
	private Double ratedHour;

	//作业指导书
	private String jobGuide;

	//开始时间
	private String startDate;

	//不定参数json串
	private String jsonParam;

	//制程完成检测flag0:不检测，1：要检测
	private Integer checkStatus;

	//加工费
	private Float processPrice;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	public Double getRatedHour() {
		return ratedHour;
	}

	public void setRatedHour(Double ratedHour) {
		this.ratedHour = ratedHour;
	}

	public String getJobGuide() {
		return jobGuide;
	}

	public void setJobGuide(String jobGuide) {
		this.jobGuide = jobGuide;
	}

	public String getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Float getProcessPrice() {
		return processPrice;
	}

	public void setProcessPrice(Float processPrice) {
		this.processPrice = processPrice;
	}
}
