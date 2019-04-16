package com.ecms.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceDetailEntity;
import com.ecms.dal.entity.SupplierMaterialEntity;
import com.ecms.dal.mapper.SupplierMaterialMapper;

@Component
public class SupplierMaterialManager extends BaseManager<SupplierMaterialEntity> {

	@Resource
	private SupplierMaterialMapper mapper;
	@Override
	protected BasicMapper<SupplierMaterialEntity> getMapper() {
		return mapper;
	}
	public void deleteBySupplierId(Integer supplierId) {
		mapper.deleteBySupplierId(supplierId);
	}
}
