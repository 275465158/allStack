package com.ecms.service;

import com.ecms.common.LogCapability;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivitiService implements LogCapability {
    private Logger logger = getLogger();

    @Resource
    public ProcessEngine processEngine;

    public ProcessEngine getProcessEngine() {
        return processEngine;
    }

    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public ActivitiService(){
        setProcessEngine(createActivitiEngine());
    }

    public ProcessEngine createActivitiEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

}
