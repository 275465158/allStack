package com.ecms.service;

import com.ecms.bean.ProcessInfo;
import com.ecms.dal.entity.ProcessEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcessManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProcessService extends BaseService<ProcessEntity,ProcessInfo>{

    @Resource
    private ProcessManager processManager;

    @Override
    protected BaseManager<ProcessEntity> getManager(){
        return processManager;
    }

    public void updateDefaultProcess(){
        processManager.updateDefaultProcess();
    }
}
