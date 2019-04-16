package com.ecms.dal.entity;


import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_process")
public class MaterialProcessEntity extends BaseCompanyAndStatusEntity {

	//物料id
	@Column(name="material_id")
	private Integer materialId;

	//制程名称
	@Column(name="process_name")
	private String processName;

	//人数
	@Column(name="people")
	private Integer people;

	//额定工时
	@Column(name="rated_hour")
	private Double ratedHour;

	//作业指导书
	@Column(name="job_guide")
	private String jobGuide;

	//不定参数json串
	@Column(name="json_param")
	private String jsonParam;

	//是否需要检测 0:不需要 1:需要
	@Column(name="check_status")
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
