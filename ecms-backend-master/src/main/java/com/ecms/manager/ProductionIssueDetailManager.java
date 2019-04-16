package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionIssueDetailEntity;
import com.ecms.dal.mapper.ProductionIssueDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/9/16 17:06
 */
@Component
public class ProductionIssueDetailManager extends BaseManager<ProductionIssueDetailEntity> {
    @Resource
    private ProductionIssueDetailMapper productionIssueDetailMapper;

    @Override
    protected BasicMapper<ProductionIssueDetailEntity> getMapper() {
        return productionIssueDetailMapper;
    }

    public Float getIssueQty(Integer materialId,String productionNumber, Integer companyId){
        return productionIssueDetailMapper.getIssueQty(materialId,productionNumber,companyId);
    }
}
