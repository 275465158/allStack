package com.ecms.service;

import com.ecms.bean.MaterialRepertoryOutboundlogInfo;
import com.ecms.dal.entity.MaterialRepertoryOutboundlogEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRepertoryOutboundlogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/5 14:32
 */
@Service
public class MaterialRepertoryOutboundlogService extends BaseService<MaterialRepertoryOutboundlogEntity,MaterialRepertoryOutboundlogInfo> {
    @Resource
    private MaterialRepertoryOutboundlogManager materialRepertoryOutboundlogManager;
    @Override
    protected BaseManager<MaterialRepertoryOutboundlogEntity> getManager() {
        return materialRepertoryOutboundlogManager;
    }
}
