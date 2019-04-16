package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessToolTypeEntity;
import com.ecms.dal.mapper.ProcessToolTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProcessToolTypeManager extends BaseManager<ProcessToolTypeEntity>{
    @Resource
    private ProcessToolTypeMapper processToolTypeMapper;

    @Override
    protected BasicMapper<ProcessToolTypeEntity> getMapper() {
        return processToolTypeMapper;
    }
}
