package com.ecms.service;

import org.springframework.stereotype.Service;

import com.ecms.bean.StructParameterInfo;
import com.ecms.dal.entity.StructParameterEntity;
import com.ecms.manager.BaseManager;
@Service
public class StructParameterService extends BaseService<StructParameterEntity, StructParameterInfo> {

	@Override
	protected BaseManager<StructParameterEntity> getManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
