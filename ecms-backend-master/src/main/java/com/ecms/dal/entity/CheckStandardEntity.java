package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2018年9月3日
 * @Description 验收标准
 */

@Table(name="check_standard")
public class CheckStandardEntity extends BaseCompanyAndStatusEntity {

	//验收编号
	@Column(name="id")
	private Integer id;

	//验收名称
	@Column(name="check_name")
	private String checkName;

	//制作人
	@Column(name="producer")
	private String producer;

	//制作日期
	@Column(name="production_date")
	private String productionDate;

	//可用状态 1:可用 2:不可用
	@Column(name="status")
	private Integer status;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}
}
