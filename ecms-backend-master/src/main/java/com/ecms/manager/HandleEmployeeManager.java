package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.HandleEmployeeEntity;
import com.ecms.dal.mapper.HandleEmployeeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:29
 */
@Component
public class HandleEmployeeManager extends BaseManager<HandleEmployeeEntity> {
    @Resource
    private HandleEmployeeMapper handleEmployeeMapper;
    @Override
    protected BasicMapper<HandleEmployeeEntity> getMapper() {
        return handleEmployeeMapper;
    }
}
