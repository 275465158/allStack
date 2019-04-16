package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterialCheckDetailEntity;
import com.ecms.dal.mapper.MaterialCheckDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/8 13:52
 */
@Component
public class MaterialCheckDetailManager extends BaseManager<MaterialCheckDetailEntity> {
    @Resource
    private MaterialCheckDetailMapper materialCheckDetailMapper;
    @Override
    protected BasicMapper<MaterialCheckDetailEntity> getMapper() {
        return materialCheckDetailMapper;
    }
}
