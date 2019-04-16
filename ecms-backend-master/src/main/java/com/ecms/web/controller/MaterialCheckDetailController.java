package com.ecms.web.controller;

import com.ecms.bean.MaterialCheckDetailInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialCheckDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialCheckDetailService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Richie
 * @version 2018/11/8 13:48
 */
@RestController
@RequestMapping("materialCheckDetail")
public class MaterialCheckDetailController extends BaseController<MaterialCheckDetailEntity,MaterialCheckDetailInfo> {

    @Resource
    private MaterialCheckDetailService materialCheckDetailService;
    @Override
    protected BaseService<MaterialCheckDetailEntity, MaterialCheckDetailInfo> getBaseService() {
        return materialCheckDetailService;
    }

    /**
     * 插入盘点
     * @param materialCheckDetailInfo
     */
    @RequestMapping("insertCheck")
    public Result<Void> insertCheck(MaterialCheckDetailInfo materialCheckDetailInfo){
        materialCheckDetailService.insertCheck(materialCheckDetailInfo);
        return ResultUtils.success();
    }
}
