package com.ecms.service;

import com.ecms.bean.ProcessToolTypeInfo;
import com.ecms.dal.entity.ProcessToolTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcessToolTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProcessToolTypeService extends BaseService<ProcessToolTypeEntity,ProcessToolTypeInfo>{

    @Resource
    private ProcessToolTypeManager processToolTypeManager;

    @Override
    protected BaseManager<ProcessToolTypeEntity> getManager(){
        return processToolTypeManager;
    }
}
