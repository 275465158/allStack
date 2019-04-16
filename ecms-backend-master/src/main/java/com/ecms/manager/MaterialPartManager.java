package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialPartEntity;
import com.ecms.dal.mapper.MaterialPartMapper;

@Component
public class MaterialPartManager extends BaseManager<MaterialPartEntity> {

	@Resource
	private MaterialPartMapper mapper;
	@Override
	protected BasicMapper<MaterialPartEntity> getMapper() {
		return mapper;
	}

}
