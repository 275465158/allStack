package com.ecms.service;

import com.ecms.bean.HandleEmployeeInfo;
import com.ecms.dal.entity.HandleEmployeeEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.HandleEmployeeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:30
 */
@Service
public class HandleEmployeeService extends BaseService<HandleEmployeeEntity,HandleEmployeeInfo> {
    @Resource
    private HandleEmployeeManager handleEmployeeManager;
    @Override
    protected BaseManager<HandleEmployeeEntity> getManager() {
        return handleEmployeeManager;
    }
}
