package com.ecms.web.controller;

import com.ecms.bean.HandleEmployeeInfo;
import com.ecms.dal.entity.HandleEmployeeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.HandleEmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:31
 */
@RestController
@RequestMapping("handleEmployee")
public class HandleEmployeeController extends BaseController<HandleEmployeeEntity,HandleEmployeeInfo> {
    @Resource
    private HandleEmployeeService handleEmployeeService;
    @Override
    protected BaseService<HandleEmployeeEntity, HandleEmployeeInfo> getBaseService() {
        return handleEmployeeService;
    }
}
