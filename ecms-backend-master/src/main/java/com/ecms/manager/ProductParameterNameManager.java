package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductParameterNameEntity;
import com.ecms.dal.mapper.ProductParameterNameMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductParameterNameManager extends BaseManager<ProductParameterNameEntity> {
    @Resource
    private ProductParameterNameMapper productParameterNameMapper;

    @Override
    protected BasicMapper<ProductParameterNameEntity> getMapper() {
        return productParameterNameMapper;
    }
}