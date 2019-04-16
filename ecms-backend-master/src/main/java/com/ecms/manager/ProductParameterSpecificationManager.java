package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductParameterSpecificationEntity;
import com.ecms.dal.mapper.ProductParameterSpecificationMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductParameterSpecificationManager extends BaseManager<ProductParameterSpecificationEntity> {
    @Resource
    private ProductParameterSpecificationMapper productParameterSpecificationMapper;

    @Override
    protected BasicMapper<ProductParameterSpecificationEntity> getMapper() {
        return productParameterSpecificationMapper;
    }
}