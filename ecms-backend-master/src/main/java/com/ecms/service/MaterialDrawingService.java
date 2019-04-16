package com.ecms.service;

import com.ecms.bean.MaterialDrawingInfo;
import com.ecms.dal.entity.MaterialDrawingEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialDrawingManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialDrawingService extends BaseService<MaterialDrawingEntity, MaterialDrawingInfo> {

	@Resource
	private MaterialDrawingManager materialDrawingManager;

	@Override
	protected BaseManager<MaterialDrawingEntity> getManager() {
		return materialDrawingManager;
	}

}
