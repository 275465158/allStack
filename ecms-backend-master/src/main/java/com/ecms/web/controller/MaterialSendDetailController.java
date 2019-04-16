package com.ecms.web.controller;

import com.ecms.bean.MaterialSendDetailInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialSendDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialSendDetailService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/29 13:47
 */
@RestController
@RequestMapping("materialSendDetail")
public class MaterialSendDetailController extends BaseController<MaterialSendDetailEntity,MaterialSendDetailInfo> {
    @Resource
    private MaterialSendDetailService materialSendDetailService;
    @Override
    protected BaseService<MaterialSendDetailEntity, MaterialSendDetailInfo> getBaseService() {
        return materialSendDetailService;
    }

    /**
     * 发货保存
     * @param materialSendDetailInfo
     * @return
     */
    @RequestMapping("materialSend")
    public Result<Void> materialSend(MaterialSendDetailInfo materialSendDetailInfo){
        materialSendDetailService.materialSend(materialSendDetailInfo);
        return ResultUtils.success();
    }

}
