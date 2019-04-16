package com.ecms.service;

import com.ecms.bean.ProductionIssueDetailInfo;
import com.ecms.dal.entity.ProductionIssueDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductionIssueDetailManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/9/16 17:05
 */
@Service
public class ProductionIssueDetailService extends BaseService<ProductionIssueDetailEntity,ProductionIssueDetailInfo> {
    @Resource
    private ProductionIssueDetailManager productionIssueDetailManager;

    @Override
    protected BaseManager<ProductionIssueDetailEntity> getManager() {
        return productionIssueDetailManager;
    }

    public Float getIssueQty(Integer materialId,String productionNumber,Integer companyId){
        return productionIssueDetailManager.getIssueQty(materialId,productionNumber,companyId);
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "no_issuance_";
    }
}
