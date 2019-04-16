package com.ecms.service;

import com.ecms.bean.ProductionCheckLogInfo;
import com.ecms.dal.entity.ProductionCheckLogEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductionCheckLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/12 15:24
 */
@Service
public class ProductionCheckLogService extends BaseService<ProductionCheckLogEntity,ProductionCheckLogInfo> {
    @Resource
    private ProductionCheckLogManager productionCheckLogManager;
    @Override
    protected BaseManager<ProductionCheckLogEntity> getManager() {
        return productionCheckLogManager;
    }

    public Float getQualifiedNum(Integer productionLogDetailId,Integer companyId){
        return productionCheckLogManager.getQualifiedNum( productionLogDetailId, companyId);
    }

    public Float getCompleteQty(Integer companyId,String productionNumber,Integer processId){
        return productionCheckLogManager.getCompleteQty(companyId,productionNumber,processId);
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_log_product_";
    }
}
