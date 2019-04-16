package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialSendEntity;
import com.ecms.dal.mapper.MaterialSendMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/29 13:45
 */
@Component
public class MaterialSendManager extends BaseManager<MaterialSendEntity> {

    @Resource
    private MaterialSendMapper materialSendMapper;

    @Override
    protected BasicMapper<MaterialSendEntity> getMapper() {
        return materialSendMapper;
    }
}
