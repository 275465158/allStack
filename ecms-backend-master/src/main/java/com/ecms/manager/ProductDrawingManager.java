package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductDrawingEntity;
import com.ecms.dal.mapper.ProductDrawingMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductDrawingManager extends BaseManager<ProductDrawingEntity> {
    @Resource
    private ProductDrawingMapper productDrawingMapper;

    @Override
    protected BasicMapper<ProductDrawingEntity> getMapper() {
        return productDrawingMapper;
    }
}