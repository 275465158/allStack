package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "produce_schedule")
public class ProduceScheduleEntity extends BaseCompanyAndStatusEntity {

	private Integer id;
	@Column(name = "company_id")
	private Integer companyId;
	@Column(name = "work_month")
	private String workMonth;
	@Column(name = "work_days")
	private Integer workDays;
	@Column(name = "work_hours")
	private Double workHours;
	@Column(name = "work_week_day")
	private Integer workWeekDay;
	@Column(name = "work_day_hours")
	private Integer workDayHours;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
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
