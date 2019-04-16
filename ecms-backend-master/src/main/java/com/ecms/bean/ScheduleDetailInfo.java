package com.ecms.bean;

import java.util.List;
import java.util.Map;

public class ScheduleDetailInfo extends BaseInfo {

	/**主键id**/
	private Integer id;
	/**公司id**/
	private Integer companyId;
	/**月份标识**/
	private String scheduleId;
	/**日期**/
	private String scheduleDate;
	/**星期**/
	private String scheduleWeek;
	/**状态：1：上班，2:休息**/
	private Integer workStatus;
	/**工时**/
	private Double workHours;
	/**工作日**/
	private String workDays;
	/**月份**/
	private String scheduleMonth;
	/**年**/
	private Integer scheduleYear;
	/**详情列表**/
	private List<Map<String,Object>> detailList;

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	public String getScheduleMonth() {
		return scheduleMonth;
	}

	public void setScheduleMonth(String scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}

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

	public List<Map<String, Object>> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Map<String, Object>> detailList) {
		this.detailList = detailList;
	}
}
