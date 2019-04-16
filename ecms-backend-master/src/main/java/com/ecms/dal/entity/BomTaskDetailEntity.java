/**
 * 
 */
package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description Bom制作任务明细
 */
@Table(name="bom_task_detail")
public class BomTaskDetailEntity extends BaseCompanyAndStatusEntity {
	
	@Column(name="draft_name")
	private String draftName;
	
	@Column(name="material_id")
	private Integer materialId;
	
	@Column(name="task_id")
	private Integer taskId;

	public String getDraftName() {
		return draftName;
	}

	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
}
