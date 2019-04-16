package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.ecms.bean.DistrictInfo;
import com.ecms.common.LogCapability;
import com.ecms.dal.entity.DistrictEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.DistrictManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

@Component
public class DistrictService implements LogCapability{
	protected Logger logger=getLogger();

	@Resource
	private DistrictManager manager;
	
	public DistrictInfo getModel(Integer id) {
		DistrictEntity entity = manager.getEntityById(id);
		if(null== entity) {
			throw new CommonBusinessException("记录不存在");
		}
		return ExchangeUtils.exchangeObject(entity, DistrictInfo.class);
	}
	
	public List<DistrictInfo> getListByCondition(Condition condition){
		List<DistrictEntity> list = manager.getByCondition(condition);
		return ExchangeUtils.exchangeList(list, DistrictInfo.class);
	}
	
	public List<DistrictInfo> getProvinceList(){
		Condition condition = new Condition(DistrictEntity.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("parentId",0);
		List<DistrictEntity> list = manager.getByCondition(condition);
		return ExchangeUtils.exchangeList(list, DistrictInfo.class);
	}
	
	public List<DistrictInfo> getCityListByParentId(Integer parentId){
		Condition condition = new Condition(DistrictEntity.class);
		Example.Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("parentId",parentId);
		List<DistrictEntity> list = manager.getByCondition(condition);
		return ExchangeUtils.exchangeList(list, DistrictInfo.class);
	}
	
}
