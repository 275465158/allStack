package com.ecms.dal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import com.ecms.dal.entity.SupplierMaterialEntity;

public interface SupplierMaterialMapper extends BasicMapper<SupplierMaterialEntity> {
	@Delete({"DELETE  FROM `supplier_material` WHERE supplier_id=#{supplierId}"})
	void deleteBySupplierId(Integer supplierId);
}
