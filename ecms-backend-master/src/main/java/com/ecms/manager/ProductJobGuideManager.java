package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductJobGuideEntity;
import com.ecms.dal.mapper.ProductJobGuideMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductJobGuideManager extends BaseManager<ProductJobGuideEntity> {
    @Resource
    private ProductJobGuideMapper productJobGuideMapper;

    @Override
    protected BasicMapper<ProductJobGuideEntity> getMapper() {
        return productJobGuideMapper;
    }
}