package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchasePaymentPeriodEntity;

public interface PurchasePaymentPeriodMapper extends BasicMapper<PurchasePaymentPeriodEntity> {
	@Select("select * from purchase_payment_period")
	@Results(id = "purchasePeriodMap", value = { @Result(column = "period_name", property = "periodName"),
			@Result(column = "period_days", property = "periodDays"), @Result(column = "remark", property = "remark"),
			@Result(column = "status", property = "status") })
	List<PurchasePaymentPeriodEntity> tt();

	@Select("select * from purchase_payment_period where period_name=#{name}")
	@ResultMap("purchasePeriodMap")
	PurchasePaymentPeriodEntity selectByName(String name);

}
