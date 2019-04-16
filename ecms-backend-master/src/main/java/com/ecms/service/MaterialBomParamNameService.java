package com.ecms.service;

import com.ecms.bean.MaterialBomParamNameInfo;
import com.ecms.dal.entity.MaterialBomParamNameEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialBomParamNameManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialBomParamNameService extends BaseService<MaterialBomParamNameEntity, MaterialBomParamNameInfo> {

	@Resource
	private MaterialBomParamNameManager materialBomParamNameManager;

	@Override
	protected BaseManager<MaterialBomParamNameEntity> getManager() {
		return materialBomParamNameManager;
	}

}
