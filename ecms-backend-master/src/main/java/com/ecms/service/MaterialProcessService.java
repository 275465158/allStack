package com.ecms.service;

import com.ecms.bean.MaterialProcessInfo;
import com.ecms.dal.entity.MaterialProcessEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialProcessManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialProcessService extends BaseService<MaterialProcessEntity, MaterialProcessInfo> {

	@Resource
	private MaterialProcessManager materialProcessManager;
	@Override
	protected BaseManager<MaterialProcessEntity> getManager() {
		return materialProcessManager;
	}

}
