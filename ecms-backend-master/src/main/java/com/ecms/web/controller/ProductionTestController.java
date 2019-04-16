package com.ecms.web.controller;

import com.ecms.bean.IncomingInspection;
import com.ecms.common.BusinessConstants;
import com.ecms.bean.ProductionTestInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.service.IncomingInspectionService;
import com.ecms.service.ProductionTestService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("productionTest")
public class ProductionTestController {

    @Resource
    private IncomingInspectionService incomingInspectionService;
    @Resource
    private ProductionTestService productionTestService;

    @RequestMapping("list")
    public Result<Pager<IncomingInspection>> getIncomingData(int pageNum, IncomingInspection incomingInspection) {
        ExchangeUtils.convertBlank2Null(incomingInspection);
        Pager<IncomingInspection> pager = incomingInspectionService.getIncomingList(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE, incomingInspection);
        return ResultUtils.success(pager);
    }

    @RequestMapping("productionTestList")
    public Result<Pager<ProductionTestInfo>> productionTestList(int pageNum, ProductionTestInfo productionTestInfo) {
        ExchangeUtils.convertBlank2Null(productionTestInfo);
        Pager<ProductionTestInfo> pager = productionTestService.getModelList(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE, productionTestInfo);
        return ResultUtils.success(pager);
    }
}
