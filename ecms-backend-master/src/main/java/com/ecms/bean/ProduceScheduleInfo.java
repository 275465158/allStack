package com.ecms.bean;

public class ProduceScheduleInfo extends BaseInfo {

	private Integer id;
	private Integer companyId;
	private String workMonth;
	private Integer workDays;
	private Double workHours;
	private Integer workWeekDay;
	private Integer workDayHours;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}

	public Integer getWorkDays() {
		return workDays;
	}

	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}

	public Double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}

	public Integer getWorkWeekDay() {
		return workWeekDay;
	}

	public void setWorkWeekDay(Integer workWeekDay) {
		this.workWeekDay = workWeekDay;
	}

	public Integer getWorkDayHours() {
		return workDayHours;
	}

	public void setWorkDayHours(Integer workDayHours) {
		this.workDayHours = workDayHours;
	}
}
