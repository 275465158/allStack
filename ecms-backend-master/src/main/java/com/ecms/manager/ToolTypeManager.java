package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ToolEntity;
import com.ecms.dal.entity.ToolTypeEntity;
import com.ecms.dal.mapper.ToolMapper;
import com.ecms.dal.mapper.ToolTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ToolTypeManager extends BaseManager<ToolTypeEntity>{
    @Resource
    private ToolTypeMapper toolTypeMapper;

    @Override
    protected BasicMapper<ToolTypeEntity> getMapper() {
        return toolTypeMapper;
    }
}
