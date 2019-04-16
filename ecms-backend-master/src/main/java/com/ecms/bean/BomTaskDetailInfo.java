/**
 * 
 */
package com.ecms.bean;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description Bom制作明细
 */
public class BomTaskDetailInfo extends BaseCompanyAndStatusInfo {

	//需要制作bom的产品名称，不需要和产品完全对等
	private String draftName;
	
	//技术部选择的对应产品记录
	private Integer materialId;
	
	//Bom实体
	private MaterialBomInfo materialBom;
	
	//对应的任务id
	private Integer taskId;
	
	//对应的任务实体
	private BomTaskInfo task;

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

	public MaterialBomInfo getMaterialBom() {
		return materialBom;
	}

	public void setMaterialBom(MaterialBomInfo materialBom) {
		this.materialBom = materialBom;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public BomTaskInfo getTask() {
		return task;
	}

	public void setTask(BomTaskInfo task) {
		this.task = task;
	}
	
	
}
