package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.ProductionLogDetailEntity;
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

import static com.ecms.common.BusinessConstants.*;

/**
 * @author Richie
 * @version 2019/1/7 16:35
 */
@RestController
@RequestMapping("productionLogDetail")
public class ProductionLogDetailController extends BaseController<ProductionLogDetailEntity,ProductionLogDetailInfo> {

    @Resource
    private ProductionLogDetailService productionLogDetailService;

    @Resource
    private ProductionLogService productionLogService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProductionPlanService productionPlanService;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private ProductionCheckLogService productionCheckLogService;

    @Resource
    private MaterialProcessService materialProcessService;

    @Override
    protected BaseService<ProductionLogDetailEntity, ProductionLogDetailInfo> getBaseService() {
        return productionLogDetailService;
    }

    @RequestMapping("getLogList")
    public Result<List<ProductionLogDetailInfo>> getLogList(ProductionLogDetailInfo productionLogDetailInfo) {
        ExchangeUtils.convertBlank2Null(productionLogDetailInfo);
        List<ProductionLogDetailInfo> pldiList = productionLogDetailService.getListBy(productionLogDetailInfo);
        if(pldiList.size() != 0){
            Float qualifiedNum = 0f;
            for(ProductionLogDetailInfo pldi : pldiList){
                ProductionLogEntity ple = productionLogService.getEntity(pldi.getLogId());
                pldi.setProductBatch(ple.getProductBatch());
                pldi.setMaterialName(ple.getMaterialName());
                qualifiedNum = productionCheckLogService.getQualifiedNum(pldi.getId(),LoginFilter.getCurrentUser().getCompanyId());
                pldi.setQualifiedNum(qualifiedNum);
                if(pldi.getOperatId() != null){
                    EmployeeInfo ei = employeeService.getModel(pldi.getOperatId());
                    pldi.setOperatName(ei.getName());
                }
            }
        }
        return ResultUtils.success(pldiList);
    }

    @RequestMapping("getProductionLogDetail")
    public Result<Pager<ProductionLogDetailInfo>> getProductionLogDetail(int pageNum,int pageSize, ProductionLogDetailInfo productionLogDetailInfo) {
//        productionLogDetailInfo.setProcessCompleteFlag(PROCESS_COMPLETE);
        productionLogDetailInfo.setCheckFlag(PROCESS_NEED_CHECK);
        productionLogDetailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        ExchangeUtils.convertBlank2Null(productionLogDetailInfo);
        Pager<ProductionLogDetailInfo> pager = productionLogDetailService.getModelListByLike(pageNum,pageSize,productionLogDetailInfo,false);
        List<ProductionLogDetailInfo> pldiList = pager.getList();
        if(pldiList.size() != 0){
            for(ProductionLogDetailInfo pldi : pldiList){
                MaterialBomEntity mbe = materialBomService.getEntity(pldi.getMaterialId());
                if(mbe != null){
                    pldi.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    pldi.setMaterialName(mbe.getMaterialName());
                    pldi.setMaterialCode(mbe.getMaterialCode());
                    pldi.setOriginalMaterial(mbe.getOriginalMaterial());
                    pldi.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                }
                ProductionLogEntity ple = productionLogService.getEntity(pldi.getLogId());
                ProductionPlanEntity ppe = productionPlanService.getEntity(ple.getProductionPlanId());
                if(ppe != null){
                    pldi.setOrderCode(ppe.getOrderCode());
                    pldi.setContractId(ppe.getContractId());
                    pldi.setProductBatch(ppe.getProductBatch());
                    pldi.setProductionNumber(ppe.getProductionNumber());
                }
                if(pldi.getOperatId() != null){
                    EmployeeInfo ei = employeeService.getModel(pldi.getOperatId());
                    pldi.setOperatName(ei.getName());
                }
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("getProductionLog")
    public Result<Pager<ProductionLogDetailInfo>> getProductionLog(int pageNum,int pageSize, ProductionLogDetailInfo productionLogDetailInfo) {
        productionLogDetailInfo.setProcessCompleteFlag(1);
        ExchangeUtils.convertBlank2Null(productionLogDetailInfo);
        Pager<ProductionLogDetailInfo> pager = productionLogDetailService.getModelListByLike(pageNum,pageSize,productionLogDetailInfo,false);
        List<ProductionLogDetailInfo> pldiList = pager.getList();
        if(pldiList.size() != 0){
            for(ProductionLogDetailInfo pldi : pldiList){
                Integer materialId = pldi.getMaterialId();
                /*Float completedQty = 0f;
                MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
                materialProcessInfo.setMaterialId(materialId);
                List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
                if(list.size() != 0){
                    //获取最后一个制程的完成数量
                    MaterialProcessInfo mpi = list.get(list.size() - 1);
                    completedQty = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),pldi.getProductionNumber(),mpi.getId(),null);
                }
                pldi.setCompletedQty(completedQty);*/
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                if(mbe != null){
                    pldi.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    pldi.setMaterialName(mbe.getMaterialName());
                    pldi.setOriginalMaterial(mbe.getOriginalMaterial());
                    pldi.setMaterialUnit(mbe.getMaterialUnit());
                }
                ProductionPlanInfo ppi = new ProductionPlanInfo();
                ppi.setProductionNumber(pldi.getProductionNumber());
                ppi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                ppi.setStatus(STATUS_DEFAULT);
                List<ProductionPlanInfo> ppiList = productionPlanService.getListBy(ppi);
                if(ppiList != null){
                    pldi.setOrderCode(ppiList.get(0).getOrderCode());
                    pldi.setContractId(ppiList.get(0).getContractId());
                }
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("getStayCheckList")
    public Result<List<ProductionLogDetailInfo>> getStayCheckList(int pageNum,int pageSize, ProductionLogDetailInfo productionLogDetailInfo) {
        productionLogDetailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        productionLogDetailInfo.setStatus(STATUS_DEFAULT);
        productionLogDetailInfo.setMaterialId(productionLogDetailInfo.getMaterialId());
        productionLogDetailInfo.setCheckStatus(0);
        productionLogDetailInfo.setCheckFlag(PROCESS_NEED_CHECK);
        List<ProductionLogDetailInfo> stayCheckList = productionLogDetailService.getListBy(productionLogDetailInfo);
        if(stayCheckList.size() != 0){
            for(ProductionLogDetailInfo pldi : stayCheckList){
                ProductionPlanInfo ppi = new ProductionPlanInfo();
                ppi.setProductionNumber(pldi.getProductionNumber());
                ppi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionPlanInfo> ppiList = productionPlanService.getListBy(ppi);
                if(ppiList.size() != 0){
                    pldi.setContractId(ppiList.get(0).getContractId());
                    pldi.setSequenceNumber(ppiList.get(0).getSequenceNumber());
                    pldi.setOrderCode(ppiList.get(0).getOrderCode());
                    pldi.setCompletedQty(pldi.getCompleteQty());
                }
            }
        }
        return ResultUtils.success(stayCheckList);
    }
}
