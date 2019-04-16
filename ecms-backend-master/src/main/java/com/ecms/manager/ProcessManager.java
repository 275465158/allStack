package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessEntity;
import com.ecms.dal.mapper.ProcessMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProcessManager extends BaseManager<ProcessEntity>{
    @Resource
    private ProcessMapper processMapper;

    @Override
    protected BasicMapper<ProcessEntity> getMapper() {
        return processMapper;
    }

    public void updateDefaultProcess(){
        processMapper.updateDefaultProcess();
    }
}
