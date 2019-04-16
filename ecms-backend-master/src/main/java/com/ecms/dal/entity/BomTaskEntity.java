/**
 * 
 */
package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description Bom制作任务
 */
@Table(name="bom_task")
public class BomTaskEntity extends BaseCompanyAndStatusEntity {
	
	@Column(name="order_id")
	private Integer orderId;
	
	@Column(name="purchase_id")
	private String purchaseId;
	
	@Column(name="task_progress")
	private String taskProgress;
	
	@Column(name="draftsman")
	private String draftsman;
	
	@Column(name="start_date")
	private String startDate;
	
	@Column(name="completed_date")
	private String completedDate;

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
	
}
