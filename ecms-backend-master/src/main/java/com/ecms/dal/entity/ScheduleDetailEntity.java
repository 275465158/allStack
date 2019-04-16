package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "schedule_detail")
public class ScheduleDetailEntity extends BaseCompanyAndStatusEntity {

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "schedule_id")
	private String scheduleId;

	@Column(name = "schedule_date")
	private String scheduleDate;

	@Column(name = "schedule_week")
	private String scheduleWeek;

	@Column(name = "work_status")
	private Integer workStatus;

	@Column(name = "work_hours")
	private Double workHours;

	@Column(name = "schedule_year")
	private Integer scheduleYear;

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getScheduleWeek() {
		return scheduleWeek;
	}

	public void setScheduleWeek(String scheduleWeek) {
		this.scheduleWeek = scheduleWeek;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public Double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(Double workHours) {
		this.workHours = workHours;
	}

	public Integer getScheduleYear() {
		return scheduleYear;
	}

	public void setScheduleYear(Integer scheduleYear) {
		this.scheduleYear = scheduleYear;
	}
}
