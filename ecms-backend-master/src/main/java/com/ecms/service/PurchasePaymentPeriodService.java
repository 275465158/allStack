package com.ecms.service;

import com.ecms.bean.PurchasePaymentPeriodInfo;
import com.ecms.common.RedisCache;
import com.ecms.dal.entity.PurchasePaymentPeriodEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchasePaymentPeriodManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PurchasePaymentPeriodService extends BaseService<PurchasePaymentPeriodEntity, PurchasePaymentPeriodInfo> {

	@Resource
	private PurchasePaymentPeriodManager paymentManager;
	@Resource
	private RedisCache redisCache;

	@PostConstruct
	public void init() {
		List<PurchasePaymentPeriodEntity> list = getValidStatusList();
		redisCache.refresh(list);
	}

	@Override
	protected void doAfterUpdate(PurchasePaymentPeriodEntity entity) {
		redisCache.update(entity);
	}

	@Override
	protected BaseManager<PurchasePaymentPeriodEntity> getManager() {
		return paymentManager;
	}

	public void updateStatus(int id, int status) {
		PurchasePaymentPeriodEntity paymentEntity = new PurchasePaymentPeriodEntity();
		paymentEntity.setId(id);
		paymentEntity.setStatus(status);
		paymentEntity.setUpdated(new Date());
		paymentManager.updateByPrimaryKeySelective(paymentEntity);
		doAfterUpdate(paymentEntity);
	}
}
