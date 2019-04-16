package com.ecms.web.controller;

import com.ecms.bean.ProductionCheckLogInfo;
import com.ecms.bean.ProductionLogDetailInfo;
import com.ecms.bean.ProductionPlanInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.EmployeeEntity;
import com.ecms.dal.entity.ProductionCheckLogEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2018/11/12 15:25
 */
@RestController
@RequestMapping("productionCheckLog")
public class ProductionCheckLogController extends BaseController<ProductionCheckLogEntity,ProductionCheckLogInfo> {
    @Resource
    private ProductionCheckLogService productionCheckLogService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private ProductionPlanService productionPlanService;
    @Resource
    private ProductionLogDetailService productionLogDetailService;
    @Override
    protected BaseService<ProductionCheckLogEntity, ProductionCheckLogInfo> getBaseService() {
        return productionCheckLogService;
    }

    @RequestMapping("like")
    public Result<Pager<ProductionCheckLogInfo>> listByLike(int pageNum, int pageSize, ProductionCheckLogInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<ProductionCheckLogInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<ProductionCheckLogInfo> pcliList = pager.getList();
        if(pcliList.size() != 0){
            for(ProductionCheckLogInfo pcli : pcliList){
                Integer employeeId = pcli.getCheckPersonId();
                EmployeeEntity ee = employeeService.getEntity(employeeId);
                pcli.setCheckPersonName(ee.getName());
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("getByCheckId")
    public Result<ProductionCheckLogInfo> getById(Integer id){
        ProductionCheckLogInfo pcli = productionCheckLogService.getModel(id);
        String productionNumber = pcli.getProductionNumber();
        ProductionPlanInfo ppi = new ProductionPlanInfo();
        ppi.setProductionNumber(productionNumber);
        List<ProductionPlanInfo> ppeList = productionPlanService.getListBy(ppi);
        ProductionLogDetailInfo pldi = productionLogDetailService.getModel(pcli.getProductionLogId());
        if(ppeList.size() != 0){
            ProductionPlanInfo ppInfo = ppeList.get(0);
            pcli.setProductBatch(ppInfo.getProductBatch());
            pcli.setFactoryMaterialCode(ppInfo.getFactoryMaterialCode());
            pcli.setOriginalMaterial(ppInfo.getOriginalMaterial());
            pcli.setMaterialBomParamValueStr(ppInfo.getMaterialBomParamValueStr());
            pcli.setProduceDate(pldi.getProduceDate());
        }
        return ResultUtils.success(pcli);
    }
}
