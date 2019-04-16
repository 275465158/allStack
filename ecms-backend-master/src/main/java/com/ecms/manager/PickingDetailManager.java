package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PickingDetailEntity;
import com.ecms.dal.mapper.PickingDetailMapper;

@Component
public class PickingDetailManager extends BaseManager<PickingDetailEntity> {

	@Resource
	private PickingDetailMapper mapper;
	@Override
	protected BasicMapper<PickingDetailEntity> getMapper() {
		return mapper;
	}

}
