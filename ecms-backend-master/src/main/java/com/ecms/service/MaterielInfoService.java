package com.ecms.service;

import com.ecms.bean.MaterielEntityInfo;
import com.ecms.dal.entity.MaterielInfoEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielInfoManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/8/28 14:32
 */
@Service
public class MaterielInfoService extends BaseService<MaterielInfoEntity, MaterielEntityInfo>  {

    @Resource
    private MaterielInfoManager materielInfoManager;

    @Override
    protected BaseManager<MaterielInfoEntity> getManager() {
        return materielInfoManager;
    }
}
