package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.InventoryStatusEntity;
import com.ecms.dal.mapper.InventoryStatusMapper;

@Component
public class InventoryStatusManager extends BaseManager<InventoryStatusEntity> {

	@Resource
	private InventoryStatusMapper mapper;
	@Override
	protected BasicMapper<InventoryStatusEntity> getMapper() {
		return mapper;
	}

}
