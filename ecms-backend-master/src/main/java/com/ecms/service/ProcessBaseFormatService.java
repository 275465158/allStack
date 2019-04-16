package com.ecms.service;

import com.ecms.bean.ProcessBaseFormatInfo;
import com.ecms.dal.entity.ProcessBaseFormatEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProcessBaseFormatManager;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProcessBaseFormatService extends BaseService<ProcessBaseFormatEntity, ProcessBaseFormatInfo> {
    @Resource
    private ProcessBaseFormatManager processBaseFormatManager;

    @Override
    protected BaseManager<ProcessBaseFormatEntity> getManager() {
        return processBaseFormatManager;
    }
}