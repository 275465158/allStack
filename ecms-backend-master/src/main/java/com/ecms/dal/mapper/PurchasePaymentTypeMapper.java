package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchasePaymentTypeEntity;

public interface PurchasePaymentTypeMapper extends BasicMapper<PurchasePaymentTypeEntity> {
	@Select("select * from purchase_payment_type")
	@Results(id = "purchasePaymentMap", value = { @Result(column = "payment_name", property = "paymentName"),
			@Result(column = "remark", property = "remark"), @Result(column = "status", property = "status") })
	List<PurchasePaymentTypeEntity> tt();

	@Select("select * from purchase_payment_type where payment_name=#{name}")
	@ResultMap("purchasePaymentMap")
	PurchasePaymentTypeEntity selectByName(String name);

}
