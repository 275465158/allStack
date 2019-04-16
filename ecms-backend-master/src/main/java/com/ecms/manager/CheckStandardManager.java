package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.CheckStandardEntity;
import com.ecms.dal.mapper.CheckStandardMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CheckStandardManager extends BaseManager<CheckStandardEntity>{
    @Resource
    private CheckStandardMapper checkStandardMapper;

    @Override
    protected BasicMapper<CheckStandardEntity> getMapper() {
        return checkStandardMapper;
    }
}
