package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionLogDetailEntity;
import com.ecms.dal.mapper.ProductionLogDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2019/1/7 14:34
 */
@Component
public class ProductionLogDetailManager extends BaseManager<ProductionLogDetailEntity> {
    @Resource
    private ProductionLogDetailMapper productionLogDetailMapper;

    @Override
    protected BasicMapper<ProductionLogDetailEntity> getMapper() {
        return productionLogDetailMapper;
    }

    public Float getCompleteQty(Integer companyId,String productionNumber,Integer processId,Integer checkFlag){
        return productionLogDetailMapper.getCompleteQty(companyId,productionNumber,processId,checkFlag);
    }

    public Float getProductionCompleteQty(Integer companyId,String productionNumber,Integer materialId){
        return productionLogDetailMapper.getProductionCompleteQty(companyId,productionNumber,materialId);
    }
}
