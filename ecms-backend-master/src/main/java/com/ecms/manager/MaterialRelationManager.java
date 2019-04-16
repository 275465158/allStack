package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRelationEntity;
import com.ecms.dal.mapper.MaterialRelationMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MaterialRelationManager extends BaseManager<MaterialRelationEntity> {

	@Resource
	private MaterialRelationMapper materialRelationMapper;
	@Override
	protected BasicMapper<MaterialRelationEntity> getMapper() {
		return materialRelationMapper;
	}

	public void updateByMaterialChildIdAndCompanyId(Integer materialId,Integer companyId,Integer materialMergeId){
		materialRelationMapper.updateByMaterialChildIdAndCompanyId( materialId, companyId,materialMergeId);
	}
}
