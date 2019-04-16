package com.ecms.bean;

/**
 * 
 * @author alan.wu
 * 2018年8月2日
 * @Description 货架管理实体
 */
public class StorageInfo extends BaseCompanyAndStatusInfo {
	
	//货架编码
	private String storageCode;
	
	//货架号
	private String storageNum;
	
	//货架栏位
	private String storageLayer;
	
	//货架行位
	private String storageRow;
	
	//货架列位
	private String storageColumn;
	
	//所在仓库id
	private Integer repertoryId;
	
	//是否可用
	private Integer visible;
	
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getStorageNum() {
		return storageNum;
	}
	public void setStorageNum(String storageNum) {
		this.storageNum = storageNum;
	}
	public String getStorageLayer() {
		return storageLayer;
	}
	public void setStorageLayer(String storageLayer) {
		this.storageLayer = storageLayer;
	}
	public String getStorageRow() {
		return storageRow;
	}
	public void setStorageRow(String storageRow) {
		this.storageRow = storageRow;
	}
	public String getStorageColumn() {
		return storageColumn;
	}
	public void setStorageColumn(String storageColumn) {
		this.storageColumn = storageColumn;
	}
	public Integer getRepertoryId() {
		return repertoryId;
	}
	public void setRepertoryId(Integer repertoryId) {
		this.repertoryId = repertoryId;
	}
	public Integer getVisible() {
		return visible;
	}
	public void setVisable(int visible) {
		this.visible = visible;
	}
}
