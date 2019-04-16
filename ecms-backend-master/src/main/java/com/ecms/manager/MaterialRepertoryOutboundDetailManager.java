package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryOutboundDetailEntity;
import com.ecms.dal.mapper.MaterialRepertoryOutboundDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/10/29 16:06
 */
@Component
public class MaterialRepertoryOutboundDetailManager extends BaseManager<MaterialRepertoryOutboundDetailEntity> {

    @Resource
    private MaterialRepertoryOutboundDetailMapper materialRepertoryOutboundDetailMapper;

    @Override
    protected BasicMapper<MaterialRepertoryOutboundDetailEntity> getMapper() {
        return materialRepertoryOutboundDetailMapper;
    }
}
