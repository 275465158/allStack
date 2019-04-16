package com.ecms.service;

import com.ecms.bean.MaterialCheckInfo;
import com.ecms.dal.entity.MaterialCheckEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialCheckManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialCheckService extends BaseService<MaterialCheckEntity, MaterialCheckInfo> {

	@Resource
	private MaterialCheckManager materialCheckManager;

	@Override
	protected BaseManager<MaterialCheckEntity> getManager() {
		return materialCheckManager;
	}

}
