package com.ecms.service;

import com.ecms.bean.UnqualifiedHandleInfo;
import com.ecms.dal.entity.UnqualifiedHandleEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.UnqualifiedHandleManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:16
 */
@Service
public class UnqualifiedHandleService extends BaseService<UnqualifiedHandleEntity,UnqualifiedHandleInfo> {
    @Resource
    private UnqualifiedHandleManager unqualifiedHandleManager;

    @Override
    protected BaseManager<UnqualifiedHandleEntity> getManager() {
        return unqualifiedHandleManager;
    }
}
