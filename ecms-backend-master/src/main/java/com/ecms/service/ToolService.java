package com.ecms.service;

import com.ecms.bean.ToolInfo;
import com.ecms.dal.entity.ToolEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ToolManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ToolService extends BaseService<ToolEntity,ToolInfo>{

    @Resource
    private ToolManager toolManager;

    @Override
    protected BaseManager<ToolEntity> getManager(){
        return toolManager;
    }
}
