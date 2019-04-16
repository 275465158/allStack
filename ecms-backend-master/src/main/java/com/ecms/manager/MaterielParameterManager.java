package com.ecms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielParameterEntity;
import com.ecms.dal.mapper.MaterielParameterMapper;

@Component
public class MaterielParameterManager extends BaseManager<MaterielParameterEntity> {
	@Resource
	private MaterielParameterMapper paramMapper;

	@Override
	protected BasicMapper<MaterielParameterEntity> getMapper() {
		return paramMapper;
	}

	//按当前公司id获取相对应的产品属性列表
	public List<MaterielParameterEntity> getListParameterName(Integer companyId){
		return paramMapper.getListParameterName(companyId);
	}
}
