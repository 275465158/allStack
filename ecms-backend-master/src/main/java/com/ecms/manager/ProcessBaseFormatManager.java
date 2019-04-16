package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProcessBaseFormatEntity;
import com.ecms.dal.mapper.ProcessBaseFormatMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProcessBaseFormatManager extends BaseManager<ProcessBaseFormatEntity> {
    @Resource
    private ProcessBaseFormatMapper processBaseFormatMapper;

    @Override
    protected BasicMapper<ProcessBaseFormatEntity> getMapper() {
        return processBaseFormatMapper;
    }
}