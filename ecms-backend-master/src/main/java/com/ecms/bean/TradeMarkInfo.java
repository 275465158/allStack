/**
 * 
 */
package com.ecms.bean;


/**
 * @author zl
 * 2019年3月11日
 * @Description 商标
 */
public class TradeMarkInfo extends BaseCompanyAndStatusInfo {

	//商标名称
	private String tradeMark;
	
	//备注
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
