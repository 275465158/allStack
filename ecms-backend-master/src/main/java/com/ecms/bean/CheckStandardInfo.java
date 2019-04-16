/**
 * 
 */
package com.ecms.bean;


/**
 * @author zhengli
 * 2018年9月3日
 * @Description 验收标准实体
 */
public class CheckStandardInfo extends BaseInfo {

	//验收编号
	private Integer id;

	//验收名称
	private String checkName;

	//制作人
	private String producer;

	//制作日期
	private String productionDate;

	//验收明细
	private CheckStandardListInfo checkStandardListInfo;

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

	public CheckStandardListInfo getCheckStandardListInfo() {
		return checkStandardListInfo;
	}

	public void setCheckStandardListInfo(CheckStandardListInfo checkStandardListInfo) {
		this.checkStandardListInfo = checkStandardListInfo;
	}
}
