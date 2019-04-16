package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.InventoryStatusInfo;
import com.ecms.dal.entity.InventoryStatusEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.InventoryStatusManager;

@Service
public class InventoryStatusService extends BaseService<InventoryStatusEntity, InventoryStatusInfo> {

	@Resource
	private InventoryStatusManager manager;
	@Override
	protected BaseManager<InventoryStatusEntity> getManager() {
		return manager;
	}

}
