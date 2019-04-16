package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import com.ecms.dal.mapper.ProductAcceptanceMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductAcceptanceManager extends BaseManager<ProductAcceptanceEntity> {
    @Resource
    private ProductAcceptanceMapper productAcceptanceMapper;

    @Override
    protected BasicMapper<ProductAcceptanceEntity> getMapper() {
        return productAcceptanceMapper;
    }
}