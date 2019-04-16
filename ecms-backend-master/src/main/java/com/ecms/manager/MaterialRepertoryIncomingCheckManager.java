package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialRepertoryIncomingCheckEntity;
import com.ecms.dal.mapper.MaterialRepertoryIncomingCheckMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 仓库物料明细manager
 * @author Richie
 * @version 2018/10/25 17:25
 */
@Component
public class MaterialRepertoryIncomingCheckManager extends BaseManager<MaterialRepertoryIncomingCheckEntity> {

    @Resource
    private MaterialRepertoryIncomingCheckMapper materialRepertoryIncomingCheckMapper;

    @Override
    protected BasicMapper<MaterialRepertoryIncomingCheckEntity> getMapper() {
        return materialRepertoryIncomingCheckMapper;
    }

}
