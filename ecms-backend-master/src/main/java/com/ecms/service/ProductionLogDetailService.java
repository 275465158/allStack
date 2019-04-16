package com.ecms.service;

import com.ecms.bean.ProductionLogDetailInfo;
import com.ecms.dal.entity.ProductionLogDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductionLogDetailManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2019/1/7 16:32
 */
@Service
public class ProductionLogDetailService extends BaseService<ProductionLogDetailEntity,ProductionLogDetailInfo> {

    @Resource
    private ProductionLogDetailManager productionLogDetailManager;

    @Override
    protected BaseManager<ProductionLogDetailEntity> getManager() {
        return productionLogDetailManager;
    }

    public Float getCompleteQty(Integer companyId,String productionNumber,Integer processId,Integer checkFlag){
        return productionLogDetailManager.getCompleteQty(companyId,productionNumber,processId,checkFlag);
    }

    public Float getProductionCompleteQty(Integer companyId,String productionNumber,Integer materialId){
        return productionLogDetailManager.getProductionCompleteQty(companyId,productionNumber,materialId);
    }
}
