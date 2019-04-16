/**
 * 
 */
package com.ecms.bean;

import java.util.List;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description Bom制作任务
 */
public class BomTaskInfo extends BaseCompanyAndStatusInfo {

	//订单编号
	private Integer orderId;
	
	//合同编号
	private String purchaseId;
	
	//任务进度
	private String taskProgress;
	
	//制作人
	private String draftsman;
	
	//开始时间
	private String startDate;
	
	//完成时间
	private String completedDate;
	
	//明细列表
	private List<BomTaskDetailInfo> taskDetail;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getTaskProgress() {
		return taskProgress;
	}

	public void setTaskProgress(String taskProgress) {
		this.taskProgress = taskProgress;
	}

	public String getDraftsman() {
		return draftsman;
	}

	public void setDraftsman(String draftsman) {
		this.draftsman = draftsman;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public List<BomTaskDetailInfo> getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(List<BomTaskDetailInfo> taskDetail) {
		this.taskDetail = taskDetail;
	}
	
}
