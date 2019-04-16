package com.ecms.service;


import com.ecms.bean.RepertoryInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.RepertoryEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.RepertoryManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class RepertoryService extends BaseService<RepertoryEntity, RepertoryInfo> {

	@Resource
	private RepertoryManager repertoryManager;
	@Resource
	private RedisCache redisCache;

	@Override
	protected BaseManager<RepertoryEntity> getManager() {
		return repertoryManager;
	}

	@PostConstruct
	public void init() {
		List<RepertoryEntity> list = getValidStatusList();
		redisCache.refresh(list);
	}

	@Override
	protected void doAfterUpdate(RepertoryEntity entity) {
		redisCache.update(entity);
	}

	@Override
	public RepertoryInfo saveOrUpdate(RepertoryInfo info) {
		RepertoryEntity entity = ExchangeUtils.exchangeObject(info, eClass);
		if (null != info.getId()) {
			Condition condition = new Condition(eClass);
			Example.Criteria criteria = condition.createCriteria();
			criteria.andEqualTo("id", info.getId());
			List<RepertoryEntity> list = repertoryManager.getByCondition(condition);
			if (list.size() > 0) {
				entity = repertoryManager.updateByPrimaryKeySelective(entity);
			}
		} else {
			entity = repertoryManager.insertBy(entity);
		}
		doAfterUpdate(entity);
		return ExchangeUtils.exchangeObject(entity, RepertoryInfo.class);
	}
}
