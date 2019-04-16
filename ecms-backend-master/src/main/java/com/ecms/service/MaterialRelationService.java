package com.ecms.service;

import com.ecms.bean.MaterialRelationInfo;
import com.ecms.dal.entity.MaterialRelationEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRelationManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialRelationService extends BaseService<MaterialRelationEntity, MaterialRelationInfo> {

	@Resource
	private MaterialRelationManager materialRelationManager;

	@Override
	protected BaseManager<MaterialRelationEntity> getManager() {
		return materialRelationManager;
	}

}
