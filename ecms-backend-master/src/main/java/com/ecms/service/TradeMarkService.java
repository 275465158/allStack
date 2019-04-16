package com.ecms.service;

import com.ecms.bean.TradeMarkInfo;
import com.ecms.dal.entity.TradeMarkEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.TradeMarkManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TradeMarkService extends BaseService<TradeMarkEntity, TradeMarkInfo> {

	@Resource
	private TradeMarkManager tradeMarkManager;
	@Override
	protected BaseManager<TradeMarkEntity> getManager() {
		return tradeMarkManager;
	}

}
