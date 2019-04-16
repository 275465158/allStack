package com.ecms.service;

import com.ecms.bean.TtTdaInfo;
import com.ecms.dal.entity.TtTdaEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.TtTdaManager;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TtTdaService extends BaseService<TtTdaEntity, TtTdaInfo> {
    @Resource
    private TtTdaManager ttTdaManager;

    @Override
    protected BaseManager<TtTdaEntity> getManager() {
        return ttTdaManager;
    }
}