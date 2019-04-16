package com.ecms.manager;

import com.ecms.common.BasicMapper;

import com.ecms.dal.entity.MaterialRepertoryOutboundlogEntity;
import com.ecms.dal.mapper.MaterialRepertoryOutboundlogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/2 16:07
 */
@Component
public class MaterialRepertoryOutboundlogManager extends BaseManager<MaterialRepertoryOutboundlogEntity> {

    @Resource
    private MaterialRepertoryOutboundlogMapper materialRepertoryOutboundlogMapper;

    @Override
    protected BasicMapper<MaterialRepertoryOutboundlogEntity> getMapper() {
        return materialRepertoryOutboundlogMapper;
    }
}
