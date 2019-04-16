package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialDrawingEntity;
import com.ecms.dal.mapper.MaterialDrawingMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialDrawingManager extends BaseManager<MaterialDrawingEntity> {

	@Resource
	private MaterialDrawingMapper materialDrawingMapper;
	@Override
	protected BasicMapper<MaterialDrawingEntity> getMapper() {
		return materialDrawingMapper;
	}
}
