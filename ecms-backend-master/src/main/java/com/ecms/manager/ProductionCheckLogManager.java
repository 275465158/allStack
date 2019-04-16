package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionCheckLogEntity;
import com.ecms.dal.mapper.ProductionCheckLogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/12 15:23
 */
@Component
public class ProductionCheckLogManager extends BaseManager<ProductionCheckLogEntity> {
    @Resource
    private ProductionCheckLogMapper productionCheckLogMapper;
    @Override
    protected BasicMapper<ProductionCheckLogEntity> getMapper() {
        return productionCheckLogMapper;
    }

    public Float getQualifiedNum(Integer productionLogDetailId,Integer companyId){
        return productionCheckLogMapper.getQualifiedNum( productionLogDetailId, companyId);
    }

    public Float getCompleteQty(Integer companyId,String productionNumber,Integer processId){
        return productionCheckLogMapper.getCompleteQty(companyId,productionNumber,processId);
    }
}
