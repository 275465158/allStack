package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryOutboundEntity;
import com.ecms.dal.mapper.MaterialRepertoryOutboundMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 出库记录
 * @author Richie
 * @version 2018/10/29 10:21
 */
@Component
public class MaterialRepertoryOutboundManager extends BaseManager<MaterialRepertoryOutboundEntity> {

    @Resource
    private MaterialRepertoryOutboundMapper materialRepertoryOutboundMapper;

    @Override
    protected BasicMapper<MaterialRepertoryOutboundEntity> getMapper() {
        return materialRepertoryOutboundMapper;
    }
}
