package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielInventoryEntity;
import com.ecms.dal.mapper.MaterielInventoryMapper;

@Component
public class MaterielInventoryManager extends BaseManager<MaterielInventoryEntity> {

	@Resource
	private MaterielInventoryMapper mapper;
	@Override
	protected BasicMapper<MaterielInventoryEntity> getMapper() {
		return mapper;
	}

}
