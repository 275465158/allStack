package com.ecms.web.controller;

import com.ecms.bean.EmployeeInfo;
import com.ecms.bean.MaterialRepertoryInboundlogInfo;
import com.ecms.bean.SupplierInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialRepertoryInboundlogEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2018/11/5 14:24
 */
@RestController
@RequestMapping("materialRepertoryInboundlog")
public class MaterialRepertoryInboundlogController extends BaseController<MaterialRepertoryInboundlogEntity,MaterialRepertoryInboundlogInfo> {

    @Resource
    private MaterialRepertoryInboundlogService materialRepertoryInboundlogService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    SupplierService supplierService;

    @Override
    protected BaseService<MaterialRepertoryInboundlogEntity, MaterialRepertoryInboundlogInfo> getBaseService() {
        return materialRepertoryInboundlogService;
    }

    /**
     * 获取入库记录
     * @param pageNum
     * @param pageSize
     * @param info
     * @return
     */
    @RequestMapping("like")
    public Result<Pager<MaterialRepertoryInboundlogInfo>> listByLike(int pageNum, int pageSize, MaterialRepertoryInboundlogInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<MaterialRepertoryInboundlogInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<MaterialRepertoryInboundlogInfo> mriliList = pager.getList();
        if(mriliList.size() != 0){
            for(MaterialRepertoryInboundlogInfo mrili : mriliList){
                if(mrili.getOperatingPersonnelId() != null){
                    EmployeeInfo employeeInfo = employeeService.getModel(mrili.getOperatingPersonnelId());
                    mrili.setUserName(employeeInfo.getName());
                }
                if(mrili.getSupplierId() != null){
                    SupplierInfo si = supplierService.getModel(mrili.getSupplierId());
                    mrili.setSupplierName(si.getSupplierName());
                }
            }
        }
        return ResultUtils.success(pager);
    }
}
