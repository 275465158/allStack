package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialProcessingEntity;
import com.ecms.dal.mapper.MaterialProcessingMapper;

@Component
public class MaterialProcessingManager extends BaseManager<MaterialProcessingEntity> {

	@Resource
	private MaterialProcessingMapper mapper;
	@Override
	protected BasicMapper<MaterialProcessingEntity> getMapper() {
		return mapper;
	}

}
