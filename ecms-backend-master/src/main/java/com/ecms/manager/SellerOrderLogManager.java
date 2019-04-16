package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.CheckLogEntity;
import com.ecms.dal.entity.SellerOrderLogEntity;
import com.ecms.dal.mapper.CheckLogMapper;
import com.ecms.dal.mapper.SellerOrderLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SellerOrderLogManager extends BaseManager<SellerOrderLogEntity> {

	@Resource
	private SellerOrderLogMapper sellerOrderLogMapper;

    @Override
    protected BasicMapper<SellerOrderLogEntity> getMapper() {
        return sellerOrderLogMapper;
    }

}
