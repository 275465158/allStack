package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductStructEntity;
import com.ecms.dal.mapper.ProductStructMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStructManager extends BaseManager<ProductStructEntity> {
    @Resource
    private ProductStructMapper mapper;
    @Override
    protected BasicMapper<ProductStructEntity> getMapper() {
        return mapper;
    }
}
