package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.ProductionLogEntity;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("productionLog")
public class ProductionLogController extends BaseController<ProductionLogEntity,ProductionLogInfo> {
    @Resource
    private ProductionLogService productionLogService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProductionPlanService productionPlanService;

    @Resource
    private MaterialProcessService materialProcessService;

    @Resource
    private ProductionLogDetailService productionLogDetailService;

    @Resource
    private ProductionCheckLogService productionCheckLogService;

    @RequestMapping("getProductionLog")
    public Result<Pager<ProductionLogInfo>> getProductionLog(int pageNum,int pageSize, ProductionLogInfo productionLogInfo) {
        ExchangeUtils.convertBlank2Null(productionLogInfo);
        Pager<ProductionLogInfo> pager = productionLogService.getModelListByLike(pageNum,pageSize,productionLogInfo,false);
        List<ProductionLogInfo> pliList = pager.getList();
        if(pliList.size() != 0){
            for(ProductionLogInfo pli : pliList){
                Integer materialId;
                Float completedQty = 0f;
                materialId = pli.getMaterialId();

                MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
                materialProcessInfo.setMaterialId(materialId);
                List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
                if(list.size() != 0){
                    //获取最后一个制程的完成数量
                    MaterialProcessInfo mpi = list.get(list.size() - 1);
                    completedQty = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),pli.getProductionNumber(),mpi.getId(),null);
                }
                pli.setCompletedQty(completedQty);
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                if(mbe != null){
                    pli.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                }
                ProductionPlanEntity ppe = productionPlanService.getEntity(pli.getProductionPlanId());
                if(ppe != null){
                    pli.setOrderCode(ppe.getOrderCode());
                    pli.setContractId(ppe.getContractId());
                }
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("getProductionEntity")
    public Result<ProductionLogInfo> getProductionEntity(Integer id) {
        ProductionLogInfo pli = productionLogService.getModel(id);
        return ResultUtils.success(pli);
    }

    @RequestMapping("saveProductQty")
    public Result<Void> completeProduce(ProductionLogInfo productionLogInfo) {
        productionLogService.completeProduce(productionLogInfo);
        return ResultUtils.success();
    }

    @RequestMapping("/saveCheck")
    public Result<Void> saveCheck(ProductionLogInfo productionLogInfo){
        ExchangeUtils.convertBlank2Null(productionLogInfo);
        Integer checkNumber = productionCheckLogService.getNextNumber();
        productionLogService.saveCheck(productionLogInfo,checkNumber);
        return ResultUtils.success();
    }

    /**
     * 完成
     * @param info
     * @return
     */
    @RequestMapping("getCompletedQty")
    public Result<List<ProductionLogInfo>> getCompletedQty(ProductionLogInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        List<ProductionLogInfo> pliList = productionLogService.getListBy(info);
        return ResultUtils.success(pliList);
    }

    /**
     * 开始完成按钮
     * @param
     * @return
     */
    @RequestMapping("/startProduct")
    public Result<Void> startProduct(ProductionLogInfo productionLogInfo){
        productionLogService.startProduct(productionLogInfo);
        return ResultUtils.success();
    }

    /**
     * 完成按钮
     * @param
     * @return
     */
    /*@RequestMapping("/saveProductQty")
    public Result<Void> saveProductQty(ProductionLogInfo productionLogInfo){
        productionLogService.saveProductQty(productionLogInfo);
        return ResultUtils.success();
    }*/

    @Override
    protected BaseService<ProductionLogEntity, ProductionLogInfo> getBaseService() {
        return productionLogService;
    }
}
