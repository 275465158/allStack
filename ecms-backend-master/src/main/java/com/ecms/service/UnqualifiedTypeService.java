package com.ecms.service;

import com.ecms.bean.UnqualifiedTypeInfo;
import com.ecms.dal.entity.UnqualifiedTypeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.UnqualifiedTypeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/19 16:47
 */
@Service
public class UnqualifiedTypeService extends BaseService<UnqualifiedTypeEntity,UnqualifiedTypeInfo> {
    @Resource
    private UnqualifiedTypeManager unqualifiedTypeManager;
    @Override
    protected BaseManager<UnqualifiedTypeEntity> getManager() {
        return unqualifiedTypeManager;
    }
}
