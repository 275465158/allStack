package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="material_bom")
public class MaterialBomEntity extends BaseCompanyAndStatusEntity {

	@Column(name="material_code")
	private String materialCode;
	
	@Column(name="end_product_code")
	private String endProductCode;

	@Column(name="material_name")
	private String materialName;
	
	@Column(name="material_unit")
	private String materialUnit;
	
	private String author;
	
	private Boolean editable;

	@Column(name="original_material")
	private String originalMaterial;
	
	@Column(name="material_bom_created")
	private String materialBomCreated;

	@Column(name="status")
	private Integer status;

	@Column(name="company_id")
	private Integer companyId;

	@Column(name="flag")
	private Integer flag;

	@Column(name="source")
	private String source;

	@Column(name="process_id")
	private Integer processId;

	@Column(name="factory_material_code")
	private String factoryMaterialCode;

	@Column(name="number")
	private String number;

	@Column(name="type")
	private String type;

	@Column(name="material_bom_param_value_str")
	private String materialBomParamValueStr;

	@Column(name="drawing_code")
	private String drawingCode;

	@Column(name="check_id")
	private Integer checkId;

	@Column(name="material_number")
	private Integer materialNumber;

	//审核状态0:审核通过，1:审核中，2:待审核，3:审核不通过 4:停用
	@Column(name = "auditing_status")
	private Integer auditingStatus;

	//版本号
	@Column(name = "version_num")
	private Integer versionNum;

	//审核人
	@Column(name = "auditing_person_id")
	private Integer auditingPersonId;

	//来料检
	@Column(name = "incoming_flag")
	private Integer incomingFlag;

//	//
//	@Column(name = "name_code")
//	private String nameCode;
//
//	//
//	@Column(name = "original_code")
//	private String originalCode;
//
//	//
//	@Column(name = "unit_code")
//	private String unitCode;

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getEndProductCode() {
		return endProductCode;
	}

	public void setEndProductCode(String endProductCode) {
		this.endProductCode = endProductCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialUnit() {
		return materialUnit;
	}

	public void setMaterialUnit(String materialUnit) {
		this.materialUnit = materialUnit;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getMaterialBomCreated() {
		return materialBomCreated;
	}

	public void setMaterialBomCreated(String materialBomCreated) {
		this.materialBomCreated = materialBomCreated;
	}

	public String getOriginalMaterial() {
		return originalMaterial;
	}

	public void setOriginalMaterial(String originalMaterial) {
		this.originalMaterial = originalMaterial;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public String getFactoryMaterialCode() {

		return factoryMaterialCode;
	}

	public void setFactoryMaterialCode(String factoryMaterialCode) {
		this.factoryMaterialCode = factoryMaterialCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterialBomParamValueStr() {
		return materialBomParamValueStr;
	}

	public void setMaterialBomParamValueStr(String materialBomParamValueStr) {
		this.materialBomParamValueStr = materialBomParamValueStr;
	}

	public String getDrawingCode() {
		return drawingCode;
	}

	public void setDrawingCode(String drawingCode) {
		this.drawingCode = drawingCode;
	}

	public Integer getCheckId() {
		return checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(Integer materialNumber) {
		this.materialNumber = materialNumber;
	}

	public Integer getAuditingStatus() {
		return auditingStatus;
	}

	public void setAuditingStatus(Integer auditingStatus) {
		this.auditingStatus = auditingStatus;
	}

	public Integer getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	public Integer getAuditingPersonId() {
		return auditingPersonId;
	}

	public void setAuditingPersonId(Integer auditingPersonId) {
		this.auditingPersonId = auditingPersonId;
	}
	//	public String getNameCode() {
//		return nameCode;
//	}
//
//	public void setNameCode(String nameCode) {
//		this.nameCode = nameCode;
//	}
//
//	public String getOriginalCode() {
//		return originalCode;
//	}
//
//	public void setOriginalCode(String originalCode) {
//		this.originalCode = originalCode;
//	}
//
//	public String getUnitCode() {
//		return unitCode;
//	}
//
//	public void setUnitCode(String unitCode) {
//		this.unitCode = unitCode;
//	}

	public Integer getIncomingFlag() {
		return incomingFlag;
	}

	public void setIncomingFlag(Integer incomingFlag) {
		this.incomingFlag = incomingFlag;
	}
}
