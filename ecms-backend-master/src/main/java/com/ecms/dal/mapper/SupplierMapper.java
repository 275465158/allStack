package com.ecms.dal.mapper;

import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SupplierEntity;

import java.util.List;

public interface SupplierMapper extends BasicMapper<SupplierEntity> {
	/*@Select("select * from supplier s left join supplier_material where material_id=#{materialId}")
	@Results(id="supplier",value= {
			@Result(column="supplier_code",property="supplierCode"),
			@Result(column="supplier_name",property="supplierName")
			})
	List<SupplierEntity> listByMaterial(Integer materialId);*/
	@Select("SELECT  id FROM `supplier` ORDER BY id DESC LIMIT 1")	 
	Integer getNextId();
	@Select("SELECT  supplier_code FROM `supplier`")
	 List<String> getAllCode();
}
