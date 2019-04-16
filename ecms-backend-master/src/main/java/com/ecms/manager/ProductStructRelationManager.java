package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductStructRelationEntity;
import com.ecms.dal.mapper.ProductStructRelationMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStructRelationManager extends BaseManager<ProductStructRelationEntity> {
    @Resource
    private ProductStructRelationMapper mapper;
    @Override
    protected BasicMapper<ProductStructRelationEntity> getMapper() {
        return mapper;
    }
}
