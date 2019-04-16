package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SupplierContactEntity;
import com.ecms.dal.mapper.SupplierContactMapper;

@Component
public class SupplierContactManager extends BaseManager<SupplierContactEntity> {

	@Resource
	private SupplierContactMapper contactMapper;
	@Override
	protected BasicMapper<SupplierContactEntity> getMapper() {
		return contactMapper;
	}
	
	

}
