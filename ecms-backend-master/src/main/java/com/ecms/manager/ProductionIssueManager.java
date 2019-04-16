package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductionIssueEntity;
import com.ecms.dal.mapper.ProductionIssueMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/9/16 17:06
 */
@Component
public class ProductionIssueManager extends BaseManager<ProductionIssueEntity> {
    @Resource
    private ProductionIssueMapper productionIssueMapper;

    @Override
    protected BasicMapper<ProductionIssueEntity> getMapper() {
        return productionIssueMapper;
    }

    public Float getIssueQty(Integer materialId){
        return productionIssueMapper.getIssueQty(materialId);
    }
}
