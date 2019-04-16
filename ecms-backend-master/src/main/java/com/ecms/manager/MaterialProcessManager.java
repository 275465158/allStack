package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialProcessEntity;
import com.ecms.dal.mapper.MaterialProcessMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialProcessManager extends BaseManager<MaterialProcessEntity> {

	@Resource
	private MaterialProcessMapper materialProcessMapper;
	@Override
	protected BasicMapper<MaterialProcessEntity> getMapper() {
		return materialProcessMapper;
	}

}
