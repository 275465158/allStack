package com.ecms.service;

import com.ecms.bean.CheckLogInfo;
import com.ecms.bean.PickingInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.CheckLogEntity;
import com.ecms.dal.entity.PickingEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.CheckLogManager;
import com.ecms.manager.PickingManager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckLogService extends BaseService<CheckLogEntity, CheckLogInfo> {

	@Resource
	private CheckLogManager checkLogManager;
	@Override
	protected BaseManager<CheckLogEntity> getManager() {
		return checkLogManager;
	}

}
