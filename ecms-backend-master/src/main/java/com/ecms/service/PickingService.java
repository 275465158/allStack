package com.ecms.service;

import javax.annotation.Resource;

import com.ecms.common.Pager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import com.ecms.bean.PickingInfo;
import com.ecms.dal.entity.PickingEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PickingManager;

import java.util.List;

@Service
public class PickingService extends BaseService<PickingEntity, PickingInfo> {

	@Resource
	private PickingManager manager;
	@Override
	protected BaseManager<PickingEntity> getManager() {
		return manager;
	}

	public Pager<PickingInfo> getPickList(int pageNum, int pageSize, PickingInfo pickingInfo) {
		PickingEntity pickingEntity = ExchangeUtils.exchangeObject(pickingInfo, PickingEntity.class);
		List<PickingEntity> list = manager.getPickList(pageNum, pageSize, pickingEntity);
		long total = manager.getTotal(pickingEntity);
		List<PickingInfo> userList = ExchangeUtils.exchangeList(list, PickingInfo.class);
		Pager<PickingInfo> pager = new Pager<PickingInfo>(pageNum, pageSize, total, userList);
		return pager;
	}
}
