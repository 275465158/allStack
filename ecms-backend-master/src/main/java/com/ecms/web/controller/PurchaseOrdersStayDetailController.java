package com.ecms.web.controller;

import com.ecms.bean.PurchaseOrdersStayDetailInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseOrdersStayDetailEntity;
import com.ecms.manager.MaterialRepertoryManager;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("purchaseOrdersStayDetail")
@RestController
public class PurchaseOrdersStayDetailController extends BaseController<PurchaseOrdersStayDetailEntity, PurchaseOrdersStayDetailInfo> {
    @Resource
    private PurchaseOrdersStayDetailService service;
    @Resource
    private PurchaseOrdersStayService purchaseOrdersStayService;
    @Resource
    private PurchaseOrdersService purchaseOrdersService;
    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Override
    protected BaseService<PurchaseOrdersStayDetailEntity, PurchaseOrdersStayDetailInfo> getBaseService() {
        return service;
    }

    @RequestMapping("/turnBack")
    public Result<Void> turnBack(Integer orderId){
        service.turnback(orderId);
        return ResultUtils.success();
    }
}
