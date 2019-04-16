package com.ecms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialParameterEntity;
import com.ecms.dal.mapper.MaterialParameterMapper;

@Component
public class MaterialParameterManager extends BaseManager<MaterialParameterEntity> {

	@Resource
	private MaterialParameterMapper mapper;
	@Override
	protected BasicMapper<MaterialParameterEntity> getMapper() {
		return mapper;
	}

	//按当前公司id获取相对应的产品属性列表
	public List<MaterialParameterEntity> getListParameterName(Integer companyId){
		return mapper.getListParameterName(companyId);
	}
}
