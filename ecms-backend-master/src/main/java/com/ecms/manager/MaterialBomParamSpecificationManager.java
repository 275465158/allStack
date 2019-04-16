package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialBomParamSpecificationEntity;
import com.ecms.dal.mapper.MaterialBomParamSpecificationMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialBomParamSpecificationManager extends BaseManager<MaterialBomParamSpecificationEntity> {

	@Resource
	private MaterialBomParamSpecificationMapper materialBomParamSpecificationMapper;
	@Override
	protected BasicMapper<MaterialBomParamSpecificationEntity> getMapper() {
		return materialBomParamSpecificationMapper;
	}
}
