package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.BpmDefinitionEntity;
import com.ecms.dal.mapper.BpmDefinitionMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BpmDefinitionManager extends BaseManager<BpmDefinitionEntity> {
    @Resource
    private BpmDefinitionMapper mapper;
    @Override
    protected BasicMapper<BpmDefinitionEntity> getMapper() {
        return mapper;
    }
}
