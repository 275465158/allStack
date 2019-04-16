package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductStructParameterEntity;
import com.ecms.dal.mapper.ProductStructParameterMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStructParameterManager extends BaseManager<ProductStructParameterEntity> {

    @Resource
    private ProductStructParameterMapper mapper;
    @Override
    protected BasicMapper<ProductStructParameterEntity> getMapper() {
        return mapper;
    }
}
