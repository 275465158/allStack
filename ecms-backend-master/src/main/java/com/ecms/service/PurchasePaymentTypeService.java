package com.ecms.service;

import com.ecms.bean.PurchasePaymentTypeInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.PurchasePaymentTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchasePaymentTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PurchasePaymentTypeService extends BaseService<PurchasePaymentTypeEntity, PurchasePaymentTypeInfo> {

	@Resource
	private RedisCache redisCache;
	@Resource
	private PurchasePaymentTypeManager paymentManager;

	@PostConstruct
	public void init() {
		List<PurchasePaymentTypeEntity> list = getValidStatusList();
		redisCache.refresh(list);
	}

	@Override
	protected void doAfterUpdate(PurchasePaymentTypeEntity entity) {
		redisCache.update(entity);
	}

	@Override
	protected BaseManager<PurchasePaymentTypeEntity> getManager() {
		return paymentManager;
	}

	public void updateStatus(int id, int status) {
		PurchasePaymentTypeEntity paymentEntity = new PurchasePaymentTypeEntity();
		paymentEntity.setId(id);
		paymentEntity.setStatus(status);
		paymentEntity.setUpdated(new Date());
		paymentManager.updateByPrimaryKeySelective(paymentEntity);
		doAfterUpdate(paymentEntity);
	}
}
