package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductStructSelectorEntity;
import com.ecms.dal.mapper.ProductStructSelectorMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductStructSelectorManager extends BaseManager<ProductStructSelectorEntity> {

    @Resource
    private ProductStructSelectorMapper mapper;

    @Override
    protected BasicMapper<ProductStructSelectorEntity> getMapper() {
        return mapper;
    }
}
