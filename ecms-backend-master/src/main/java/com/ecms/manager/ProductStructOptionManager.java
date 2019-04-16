package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductStructOptionEntity;
import com.ecms.dal.mapper.ProductStructOptionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStructOptionManager extends BaseManager<ProductStructOptionEntity> {
    @Resource
    private ProductStructOptionMapper mapper;
    @Override
    protected BasicMapper<ProductStructOptionEntity> getMapper() {
        return mapper;
    }
}
