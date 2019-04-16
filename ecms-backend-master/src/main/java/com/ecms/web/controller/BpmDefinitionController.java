package com.ecms.web.controller;

import com.ecms.bean.BpmDefinitionInfo;
import com.ecms.dal.entity.BpmDefinitionEntity;
import com.ecms.service.BaseService;
import com.ecms.service.BpmDefinitionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("bpm")
public class BpmDefinitionController extends BaseController<BpmDefinitionEntity, BpmDefinitionInfo> {
    @Resource
    private BpmDefinitionService service;
    @Override
    protected BaseService<BpmDefinitionEntity, BpmDefinitionInfo> getBaseService() {
        return service;
    }
}
