package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.SellerPayRuleEntity;
import com.ecms.dal.mapper.SellerPayRuleMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SellerPayRuleManager extends BaseManager<SellerPayRuleEntity> {

    @Resource
    private SellerPayRuleMapper sellerPayRuleMapper;

    @Override
    protected BasicMapper<SellerPayRuleEntity> getMapper() {
        return sellerPayRuleMapper;
    }
}
