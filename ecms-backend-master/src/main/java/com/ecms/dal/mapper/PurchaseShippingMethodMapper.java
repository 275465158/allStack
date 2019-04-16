package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseShippingMethodEntity;

public interface PurchaseShippingMethodMapper extends BasicMapper<PurchaseShippingMethodEntity> {
	@Select("select * from purchase_shipping_method")
	@Results(id = "purchaseShippingMethodMap", value = { @Result(column = "method_name", property = "methodName"),
			@Result(column = "remark", property = "remark"), @Result(column = "status", property = "status") })
	List<PurchaseShippingMethodEntity> tt();

	@Select("select * from purchase_shipping_method where method_name=#{name}")
	@ResultMap("purchaseShippingMethodMap")
	PurchaseShippingMethodEntity selectByName(String name);

}
