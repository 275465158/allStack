package com.ecms.web.controller;

import com.ecms.bean.UnqualifiedHandleInfo;
import com.ecms.dal.entity.UnqualifiedHandleEntity;
import com.ecms.service.BaseService;
import com.ecms.service.UnqualifiedHandleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/12/18 10:17
 */
@RestController
@RequestMapping("unqualifiedHandle")
public class UnqualifiedHandleController extends BaseController<UnqualifiedHandleEntity,UnqualifiedHandleInfo> {
    @Resource
    private UnqualifiedHandleService unqualifiedHandleService;
    @Override
    protected BaseService<UnqualifiedHandleEntity, UnqualifiedHandleInfo> getBaseService() {
        return unqualifiedHandleService;
    }
}
