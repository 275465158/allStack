package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialBomParamNameEntity;
import com.ecms.dal.mapper.MaterialBomParamNameMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialBomParamNameManager extends BaseManager<MaterialBomParamNameEntity> {

	@Resource
	private MaterialBomParamNameMapper materialBomParamNameMapper;
	@Override
	protected BasicMapper<MaterialBomParamNameEntity> getMapper() {
		return materialBomParamNameMapper;
	}
}
