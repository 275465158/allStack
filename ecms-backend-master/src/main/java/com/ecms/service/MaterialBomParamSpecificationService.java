package com.ecms.service;

import com.ecms.bean.MaterialBomParamSpecificationInfo;
import com.ecms.dal.entity.MaterialBomParamSpecificationEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialBomParamSpecificationManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialBomParamSpecificationService extends BaseService<MaterialBomParamSpecificationEntity, MaterialBomParamSpecificationInfo> {

	@Resource
	private MaterialBomParamSpecificationManager materialBomParamSpecificationManager;

	@Override
	protected BaseManager<MaterialBomParamSpecificationEntity> getManager() {
		return materialBomParamSpecificationManager;
	}

}
