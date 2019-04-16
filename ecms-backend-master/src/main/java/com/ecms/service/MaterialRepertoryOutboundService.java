package com.ecms.service;

import com.ecms.bean.MaterialRepertoryOutboundInfo;
import com.ecms.dal.entity.MaterialRepertoryOutboundEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRepertoryOutboundManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 出库记录
 * @author Richie
 * @version 2018/10/29 10:19
 */
@Service
public class MaterialRepertoryOutboundService extends BaseService<MaterialRepertoryOutboundEntity,MaterialRepertoryOutboundInfo> {

    @Resource
    private MaterialRepertoryOutboundManager materialRepertoryOutboundManager;

    @Override
    protected BaseManager<MaterialRepertoryOutboundEntity> getManager() {
        return materialRepertoryOutboundManager;
    }
}
