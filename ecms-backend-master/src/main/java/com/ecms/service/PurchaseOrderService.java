package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.PurchaseOrderInfo;
import com.ecms.dal.entity.PurchaseOrderEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchaseOrderManager;

@Service
public class PurchaseOrderService extends BaseService<PurchaseOrderEntity, PurchaseOrderInfo> {

	@Resource
	private PurchaseOrderManager manager;
	@Override
	protected BaseManager<PurchaseOrderEntity> getManager() {
		return manager;
	}
	//更新状态
	public void updateArrived(Integer id,Boolean arreived) {
		PurchaseOrderEntity entity = new PurchaseOrderEntity();
		entity.setId(id);
		entity.setArrived(arreived);
		manager.updateByPrimaryKeySelective(entity);

	}
	public void updateApproved(Integer id, Boolean approved) {
		PurchaseOrderEntity entity = new PurchaseOrderEntity();
		entity.setId(id);
		entity.setApproval(approved);
		manager.updateByPrimaryKeySelective(entity);
	}

}
