package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UnqualifiedProductEntity;
import com.ecms.dal.mapper.UnqualifiedProductMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UnqualifiedProductManager extends BaseManager<UnqualifiedProductEntity> {
    @Resource
    private UnqualifiedProductMapper mapper;
    @Override
    protected BasicMapper<UnqualifiedProductEntity> getMapper() {
        return mapper;
    }
}
