package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessFormatEntity;
import com.ecms.dal.mapper.ProcessFormatMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProcessFormatManager extends BaseManager<ProcessFormatEntity>{
    @Resource
    private ProcessFormatMapper processFormatMapper;

    @Override
    protected BasicMapper<ProcessFormatEntity> getMapper() {
        return processFormatMapper;
    }
}
