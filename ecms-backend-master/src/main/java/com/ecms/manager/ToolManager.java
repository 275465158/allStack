package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ToolEntity;
import com.ecms.dal.mapper.ToolMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ToolManager extends BaseManager<ToolEntity>{
    @Resource
    private ToolMapper toolMapper;

    @Override
    protected BasicMapper<ToolEntity> getMapper() {
        return toolMapper;
    }
}
