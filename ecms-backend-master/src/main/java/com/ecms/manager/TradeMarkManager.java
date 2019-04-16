package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.TradeMarkEntity;
import com.ecms.dal.mapper.TradeMarkMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TradeMarkManager extends BaseManager<TradeMarkEntity> {

	@Resource
	private TradeMarkMapper tradeMarkMapper;

    @Override
    protected BasicMapper<TradeMarkEntity> getMapper() {
        return tradeMarkMapper;
    }

}
