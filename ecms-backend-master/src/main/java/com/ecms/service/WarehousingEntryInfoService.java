package com.ecms.service;

import com.ecms.bean.WarehousingEntryBeanInfo;
import com.ecms.dal.entity.WarehousingEntryInfoEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.WarehousingEntryInfoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WarehousingEntryInfoService extends BaseService<WarehousingEntryInfoEntity, WarehousingEntryBeanInfo> {

	@Resource
	private WarehousingEntryInfoManager warehousingEntryInfoManager;

	@Override
	protected BaseManager<WarehousingEntryInfoEntity> getManager() {
		return warehousingEntryInfoManager;
	}
}
