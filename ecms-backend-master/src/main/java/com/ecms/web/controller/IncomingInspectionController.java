package com.ecms.web.controller;

import com.ecms.bean.IncomingInspection;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.IncomingInspectionEntity;
import com.ecms.service.BaseService;
import com.ecms.service.IncomingInspectionService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("incomingInspection")
public class IncomingInspectionController extends BaseController<IncomingInspectionEntity,IncomingInspection> {

    @Resource
    private IncomingInspectionService incomingInspectionService;

    @Override
    protected BaseService<IncomingInspectionEntity, IncomingInspection> getBaseService() {
        return incomingInspectionService;
    }

    @RequestMapping("/incomingList")
    public Result<Pager<IncomingInspection>> getIncomingData(int pageNum, IncomingInspection incomingInspection) {
        ExchangeUtils.convertBlank2Null(incomingInspection);
        Pager<IncomingInspection> pager = incomingInspectionService.getModelList(pageNum, DEFAULT_PAGE_SIZE, incomingInspection);
        return ResultUtils.success(pager);
    }

    @RequestMapping("/incomingSave")
    public void saveIncomingInspection(){

    }
    @RequestMapping("/incomingUpdate")
    public Result<Void> updateIncomingInspection(IncomingInspection incomingInspection) {
        incomingInspectionService.updateIncomingInspection(incomingInspection);
        return ResultUtils.success();
    }
}
