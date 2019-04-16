package com.ecms.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PurchaseInvoiceTypeEntity;

public interface PurchaseInvoiceTypeMapper extends BasicMapper<PurchaseInvoiceTypeEntity> {
	@Select("select * from purchase_invoice_type")
	@Results(id = "purchaseInvoiceTypeMap", value = { @Result(column = "type_name", property = "typeName"),
			@Result(column = "tax_rate", property = "taxRate"), @Result(column = "remark", property = "remark"),
			@Result(column = "status", property = "status") })
	List<PurchaseInvoiceTypeEntity> tt();

	@Select("select * from purchase_invoice_type where type_name=#{name}")
	@ResultMap("purchaseInvoiceTypeMap")
	PurchaseInvoiceTypeEntity selectByTypeName(String name);
}
