package com.ecms.dal.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author zhengli
 * 2019年3月11日
 * @Description 商标
 */

@Table(name="trade_mark")
public class TradeMarkEntity extends BaseCompanyAndStatusEntity {

	//商标名称
	@Column(name="trade_mark")
	private String tradeMark;

	//备注
	@Column(name="remark")
	private String remark;

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
