package com.ecms.service;

import com.ecms.bean.MaterialRepertoryInboundlogInfo;
import com.ecms.dal.entity.MaterialRepertoryInboundlogEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRepertoryInboundlogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/5 14:32
 */
@Service
public class MaterialRepertoryInboundlogService extends BaseService<MaterialRepertoryInboundlogEntity,MaterialRepertoryInboundlogInfo> {
    @Resource
    private MaterialRepertoryInboundlogManager materialRepertoryInboundlogManager;
    @Override
    protected BaseManager<MaterialRepertoryInboundlogEntity> getManager() {
        return materialRepertoryInboundlogManager;
    }
}
