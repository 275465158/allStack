package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.WarehousingEntryEntity;
import com.ecms.dal.mapper.WarehousingEntryMapper;

@Component
public class WarehousingEntryManager extends BaseManager<WarehousingEntryEntity> {

	@Resource
	private WarehousingEntryMapper mapper;
	@Override
	protected BasicMapper<WarehousingEntryEntity> getMapper() {
		return mapper;
	}

}
