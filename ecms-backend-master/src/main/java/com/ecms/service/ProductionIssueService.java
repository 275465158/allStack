package com.ecms.service;

import com.ecms.bean.ProductionIssueInfo;
import com.ecms.dal.entity.ProductionIssueEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProductionIssueManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/9/16 17:05
 */
@Service
public class ProductionIssueService extends BaseService<ProductionIssueEntity,ProductionIssueInfo> {
    @Resource
    private ProductionIssueManager productionIssueManager;

    @Override
    protected BaseManager<ProductionIssueEntity> getManager() {
        return productionIssueManager;
    }

    public Float getIssueQty(Integer materialId){
        return productionIssueManager.getIssueQty(materialId);
    }

}
