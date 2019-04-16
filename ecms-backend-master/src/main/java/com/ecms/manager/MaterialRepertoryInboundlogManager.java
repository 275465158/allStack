package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryInboundlogEntity;
import com.ecms.dal.mapper.MaterialRepertoryInboundlogMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/2 16:07
 */
@Component
public class MaterialRepertoryInboundlogManager extends BaseManager<MaterialRepertoryInboundlogEntity> {

    @Resource
    private MaterialRepertoryInboundlogMapper materialRepertoryInboundlogMapper;

    @Override
    protected BasicMapper<MaterialRepertoryInboundlogEntity> getMapper() {
        return materialRepertoryInboundlogMapper;
    }
}
