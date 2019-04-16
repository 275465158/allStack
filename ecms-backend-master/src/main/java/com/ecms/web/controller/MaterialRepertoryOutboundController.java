package com.ecms.web.controller;

import com.ecms.bean.MaterialRepertoryOutboundInfo;
import com.ecms.dal.entity.MaterialRepertoryOutboundEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialRepertoryOutboundService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 出库记录
 * @author Richie
 * @version 2018/10/29 10:17
 */
@RestController
@RequestMapping("materialRepertoryOutbound")
public class MaterialRepertoryOutboundController extends BaseController<MaterialRepertoryOutboundEntity,MaterialRepertoryOutboundInfo> {

    @Resource
    private MaterialRepertoryOutboundService materialRepertoryOutboundService;

    @Override
    protected BaseService<MaterialRepertoryOutboundEntity, MaterialRepertoryOutboundInfo> getBaseService() {
        return materialRepertoryOutboundService;
    }
}
