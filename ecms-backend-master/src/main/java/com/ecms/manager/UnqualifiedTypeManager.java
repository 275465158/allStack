package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.UnqualifiedTypeEntity;
import com.ecms.dal.mapper.UnqualifiedTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/19 16:46
 */
@Component
public class UnqualifiedTypeManager extends BaseManager<UnqualifiedTypeEntity> {
    @Resource
    private UnqualifiedTypeMapper unqualifiedTypeMapper;

    @Override
    protected BasicMapper<UnqualifiedTypeEntity> getMapper() {
        return unqualifiedTypeMapper;
    }
}
