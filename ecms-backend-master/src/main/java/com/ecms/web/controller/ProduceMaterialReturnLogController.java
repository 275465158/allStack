package com.ecms.web.controller;

import com.ecms.bean.ProduceMaterialReturnLogInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.ProduceMaterialReturnLogEntity;
import com.ecms.dal.entity.RepertoryEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2019/1/9 14:01
 */
@RestController
@RequestMapping("produceMaterialReturnLog")
public class ProduceMaterialReturnLogController extends BaseController<ProduceMaterialReturnLogEntity,ProduceMaterialReturnLogInfo> {

    @Resource
    private ProduceMaterialReturnLogService produceMaterialReturnLogService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private RepertoryService repertoryService;

    @Override
    protected BaseService<ProduceMaterialReturnLogEntity, ProduceMaterialReturnLogInfo> getBaseService() {
        return produceMaterialReturnLogService;
    }

    @RequestMapping("insertInfo")
    public Result<Void> insertBy(ProduceMaterialReturnLogInfo produceMaterialReturnLogInfo) {
        produceMaterialReturnLogService.returnMaterial(produceMaterialReturnLogInfo);
        return ResultUtils.success();
    }

    @RequestMapping("getReturnLogList")
    public Result<List<ProduceMaterialReturnLogInfo>> getReturnLogList(ProduceMaterialReturnLogInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        List<ProduceMaterialReturnLogInfo> pmrliList = produceMaterialReturnLogService.getListBy(info);
        if(pmrliList.size() != 0){
            for(ProduceMaterialReturnLogInfo pmrli : pmrliList){
                if(pmrli.getOperatingPersonnelId() != null){
                    EmployeeEntity ee = employeeService.getEntity(pmrli.getOperatingPersonnelId());
                    pmrli.setOperatingPersonnelName(ee.getName());
                }
                if(pmrli.getMaterialId() != null){
                    MaterialBomEntity mbe = materialBomService.getEntity(pmrli.getMaterialId());
                    pmrli.setMaterialCode(mbe.getMaterialCode());
                    pmrli.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    pmrli.setSource(mbe.getSource());
                }
                if(pmrli.getRepertoryId() != null){
                    RepertoryEntity pr = repertoryService.getEntity(pmrli.getRepertoryId());
                    pmrli.setRepertoryName(pr.getRepertoryName());
                }
            }
        }
        return ResultUtils.success(pmrliList);
    }
}
