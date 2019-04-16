package com.ecms.service;

import com.ecms.bean.PurchaseShippingMethodInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.PurchaseShippingMethodEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchaseShippingMethodManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PurchaseShippingMethodService
		extends BaseService<PurchaseShippingMethodEntity, PurchaseShippingMethodInfo> {

	@Resource
	private RedisCache redisCache;

	@PostConstruct
	public void init() {
		List<PurchaseShippingMethodEntity> list = getValidStatusList();
		redisCache.refresh(list);
	}

	@Override
	protected void doAfterUpdate(PurchaseShippingMethodEntity entity) {
		redisCache.update(entity);
	}

	@Resource
	private PurchaseShippingMethodManager methodManager;

	@Override
	protected BaseManager<PurchaseShippingMethodEntity> getManager() {
		return methodManager;
	}

	public void updateStatus(int id, int status) {
		PurchaseShippingMethodEntity methodEntity = new PurchaseShippingMethodEntity();
		methodEntity.setId(id);
		methodEntity.setStatus(status);
		methodEntity.setUpdated(new Date());
		methodManager.updateByPrimaryKeySelective(methodEntity);
		doAfterUpdate(methodEntity);
	}
}
