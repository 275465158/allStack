package com.ecms.service;

import com.ecms.bean.ProcessFormatInfo;
import com.ecms.dal.entity.ProcessFormatEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcessFormatManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProcessFormatService extends BaseService<ProcessFormatEntity,ProcessFormatInfo>{

    @Resource
    private ProcessFormatManager processFormatManager;

    @Override
    protected BaseManager<ProcessFormatEntity> getManager(){
        return processFormatManager;
    }
}
