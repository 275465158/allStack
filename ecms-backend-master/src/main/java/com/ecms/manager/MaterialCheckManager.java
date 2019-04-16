package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialCheckEntity;
import com.ecms.dal.entity.MaterialDrawingEntity;
import com.ecms.dal.mapper.MaterialCheckMapper;
import com.ecms.dal.mapper.MaterialDrawingMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialCheckManager extends BaseManager<MaterialCheckEntity> {

	@Resource
	private MaterialCheckMapper materialCheckMapper;
	@Override
	protected BasicMapper<MaterialCheckEntity> getMapper() {
		return materialCheckMapper;
	}
}
