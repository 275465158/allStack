package com.ecms.service;

import com.ecms.bean.CheckStandardInfo;
import com.ecms.dal.entity.CheckStandardEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.CheckStandardManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CheckStandardService extends BaseService<CheckStandardEntity,CheckStandardInfo>{

    @Resource
    private CheckStandardManager checkStandardManager;

    @Override
    protected BaseManager<CheckStandardEntity> getManager(){
        return checkStandardManager;
    }
}
