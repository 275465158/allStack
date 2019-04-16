package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "product")
public class ProductEntity extends AbstractEntity {

	private Integer id; //id
	@Column(name = "check_number")
	private String checkNumber;  //检测编号
	@Column(name = "production_number")
	private String productionNumber;  //生产编号
	@Column(name = "check_date")
	private String checkDate;  //检测日期
	@Column(name = "product_name")
	private String productName;	 //物料名称
	@Column(name = "product_number")
	private String productNumber;  //物料编号
	@Column(name = "product_type")
	private String productType;  //产品类型
	@Column(name = "contract_number")
	private String contractNumber;	//合同编号
	@Column(name = "production_batch")
	private String productionBatch;  //生产项次
	@Column(name = "product_num")
	private Integer productNum;	 //数量
	@Column(name = "qualified_num")
	private Integer qualifiedNum;  //合格数
	@Column(name = "unqualified_num")
	private Integer unqualifiedNum;  //不合格数
	@Column(name = "qualified_rate")
	private String qualifiedRate;  //合格率
	@Column(name = "check_man")
	private String checkMan;  //检测人
	@Column(name="company_id")
	private Integer companyId;  //公司id
	@Column(name="finish_product_number")
	private String finishProductNumber;//成品编号
	@Column(name="original_materiel")
	private String originalMateriel; //原题材料
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;
	private String parameter5;
	@Column(name = "check_flag")
	private Integer checkFlag; //检测状态 0未检测 1已检测

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getProductionNumber() {
		return productionNumber;
	}

	public void setProductionNumber(String productionNumber) {
		this.productionNumber = productionNumber;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getProductionBatch() {
		return productionBatch;
	}

	public void setProductionBatch(String productionBatch) {
		this.productionBatch = productionBatch;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {this.productNum = productNum; }

	public Integer getQualifiedNum() {return qualifiedNum;}

	public void setQualifiedNum(Integer qualifiedNum) {
		this.qualifiedNum = qualifiedNum;
	}

	public Integer getUnqualifiedNum() {
		return unqualifiedNum;
	}

	public void setUnqualifiedNum(Integer unqualifiedNum) {
		this.unqualifiedNum = unqualifiedNum;
	}

	public String getQualifiedRate() {
		return qualifiedRate;
	}

	public void setQualifiedRate(String qualifiedRate) {
		this.qualifiedRate = qualifiedRate;
	}

	public String getCheckMan() {
		return checkMan;
	}

	public void setCheckMan(String checkMan) {
		this.checkMan = checkMan;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getFinishProductNumber() {return finishProductNumber;}

	public void setFinishProductNumber(String finishProductNumber) { this.finishProductNumber = finishProductNumber;}

	public String getOriginalMateriel() {return originalMateriel; }

	public void setOriginalMateriel(String originalMateriel) { this.originalMateriel = originalMateriel; }

	public String getParameter1() { return parameter1; }

	public void setParameter1(String parameter1) { this.parameter1 = parameter1; }

	public String getParameter2() { return parameter2; }

	public void setParameter2(String parameter2) { this.parameter2 = parameter2; }

	public String getParameter3() { return parameter3; }

	public void setParameter3(String parameter3) { this.parameter3 = parameter3; }

	public String getParameter4() { return parameter4; }

	public void setParameter4(String parameter4) { this.parameter4 = parameter4; }

	public String getParameter5() { return parameter5; }

	public void setParameter5(String parameter5) { this.parameter5 = parameter5; }

	public Integer getCheckFlag() { return checkFlag; }

	public void setCheckFlag(Integer checkFlag) { this.checkFlag = checkFlag; }
}
