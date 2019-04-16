package com.ecms.web.controller;

import com.ecms.bean.MaterialSendInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialSendEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialSendService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/29 13:47
 */
@RestController
@RequestMapping("materialSend")
public class MaterialSendController extends BaseController<MaterialSendEntity,MaterialSendInfo> {
    @Resource
    private MaterialSendService materialSendService;
    @Override
    protected BaseService<MaterialSendEntity, MaterialSendInfo> getBaseService() {
        return materialSendService;
    }

    @RequestMapping("saveSendMaterial")
    public Result<Void> saveSendMaterial(MaterialSendInfo materialSendInfo){
        materialSendService.saveSendMaterial(materialSendInfo);
        return ResultUtils.success();
    }

    @RequestMapping("materialSend")
    public Result<Void> materialSend(MaterialSendInfo materialSendInfo){
        materialSendService.materialSend(materialSendInfo);
        return ResultUtils.success();
    }

}
