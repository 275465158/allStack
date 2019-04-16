package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionLogEntity;
import com.ecms.dal.mapper.ProductionLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductionLogManager extends BaseManager<ProductionLogEntity> {

	@Resource
	private ProductionLogMapper productionLogMapper;

    @Override
    protected BasicMapper<ProductionLogEntity> getMapper() {
        return productionLogMapper;
    }

    public Float getCompletedQty(String orderCode,Integer materialId,Integer companyId){
        return productionLogMapper.getCompletedQty(orderCode,materialId,companyId);
    }

}
