package com.ecms.service;

import com.ecms.bean.MaterialRepertoryInfo;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialRepertoryManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 仓库物料service
 * @author Richie
 * @version 2018/10/25 17:00
 */
@Service
public class MaterialRepertoryService extends BaseService<MaterialRepertoryEntity, MaterialRepertoryInfo> {

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Override
    protected BaseManager<MaterialRepertoryEntity> getManager() {
        return materialRepertoryManager;
    }
}
