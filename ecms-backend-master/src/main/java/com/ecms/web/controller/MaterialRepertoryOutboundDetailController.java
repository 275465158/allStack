package com.ecms.web.controller;

import com.ecms.bean.MaterialRepertoryOutboundDetailInfo;
import com.ecms.dal.entity.MaterialRepertoryOutboundDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialRepertoryOutboundDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/10/29 16:03
 */
@RestController
@RequestMapping("materialRepertoryOutboundDetail")
public class MaterialRepertoryOutboundDetailController extends BaseController<MaterialRepertoryOutboundDetailEntity,MaterialRepertoryOutboundDetailInfo> {

    @Resource
    private MaterialRepertoryOutboundDetailService materialRepertoryOutboundDetailService;

    @Override
    protected BaseService<MaterialRepertoryOutboundDetailEntity, MaterialRepertoryOutboundDetailInfo> getBaseService() {
        return materialRepertoryOutboundDetailService;
    }
}
