package com.ecms.service;

import com.ecms.bean.ToolTypeInfo;
import com.ecms.dal.entity.ToolTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ToolTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ToolTypeService extends BaseService<ToolTypeEntity,ToolTypeInfo>{

    @Resource
    private ToolTypeManager toolTypeManager;

    @Override
    protected BaseManager<ToolTypeEntity> getManager(){
        return toolTypeManager;
    }
}
