package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.WarehousingEntryDetailEntity;
import com.ecms.dal.mapper.WarehousingEntryDetailMapper;

@Component
public class WarehousingEntryDetailManager extends BaseManager<WarehousingEntryDetailEntity> {

	@Resource
	private WarehousingEntryDetailMapper mapper;
	@Override
	protected BasicMapper<WarehousingEntryDetailEntity> getMapper() {
		return mapper;
	}

}
