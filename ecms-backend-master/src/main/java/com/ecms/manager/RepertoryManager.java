package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.RepertoryEntity;
import com.ecms.dal.mapper.RepertoryMapper;

@Component
public class RepertoryManager extends BaseManager<RepertoryEntity> {

	@Resource
	private RepertoryMapper repertoryMapper;

	@Override
	protected BasicMapper<RepertoryEntity> getMapper() {
		return repertoryMapper;
	}

	
}
