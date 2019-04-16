package com.ecms.service;

import com.ecms.bean.MaterialRepertoryOutboundDetailInfo;
import com.ecms.dal.entity.MaterialRepertoryOutboundDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRepertoryOutboundDetailManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/10/29 16:05
 */
@Service
public class MaterialRepertoryOutboundDetailService extends BaseService<MaterialRepertoryOutboundDetailEntity,MaterialRepertoryOutboundDetailInfo> {

    @Resource
    private MaterialRepertoryOutboundDetailManager materialRepertoryOutboundDetailManager;

    @Override
    protected BaseManager<MaterialRepertoryOutboundDetailEntity> getManager() {
        return materialRepertoryOutboundDetailManager;
    }
}
