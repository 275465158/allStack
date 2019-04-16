package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="storage")
public class StorageEntity extends BaseCompanyAndStatusEntity {

	@Column(name="storage_code")
	private String storageCode;
	
	@Column(name="storage_num")
	private String storageNum;
	
	@Column(name="storage_layer")
	private String storageLayer;
	
	@Column(name="storage_row")
	private String storageRow;
	
	@Column(name="storage_column")
	private String storageColumn;
	
	@Column(name="repertory_id")
	private Integer repertoryId;

	@Column(name="visible")
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

	public void setVisible(Integer visible) {
		this.visible = visible;
	}
	

}
