package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.bean.MaterielParameterInfo;
import com.ecms.dal.entity.MaterielParameterEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielParameterManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Example;

@Component
public class MaterielParameterService extends BaseService<MaterielParameterEntity, MaterielParameterInfo> {

	@Resource
	private MaterielParameterManager paramManager;

	@Override
	protected BaseManager<MaterielParameterEntity> getManager() {
		return paramManager;
	}

	public List<MaterielParameterInfo> getListByMaterielId(Integer materielId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materielId", materielId);
		return getByCondition();
	}

	public List<MaterielParameterInfo> getListIsVisible() {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("isVisible", 1);
		return getByCondition();
	}

	public List<MaterielParameterInfo> getListParameterName(Integer companyId) {
		return ExchangeUtils.exchangeList(paramManager.getListParameterName(companyId), MaterielParameterInfo.class);
	}
}
