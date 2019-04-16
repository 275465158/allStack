package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.TtTdaEntity;
import com.ecms.dal.mapper.TtTdaMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TtTdaManager extends BaseManager<TtTdaEntity> {
    @Resource
    private TtTdaMapper ttTdaMapper;

    @Override
    protected BasicMapper<TtTdaEntity> getMapper() {
        return ttTdaMapper;
    }
}