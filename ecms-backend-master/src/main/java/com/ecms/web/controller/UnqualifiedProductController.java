package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Constants;
import com.ecms.common.DisposalMeasure;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.UnqualifiedProductEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.utils.StringUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ecms.common.BusinessConstants.PRODUCTION_MATERIAL;
import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

@RestController
@RequestMapping("product/unqualified")
public class UnqualifiedProductController extends BaseController<UnqualifiedProductEntity, UnqualifiedProductInfo> {
    protected String workflowKey = "UnqualifiedProductReview";
    @Resource
    private UnqualifiedProductService service;
    @Resource
    private ProductionCheckLogService productionCheckLogService;
    @Resource
    private ProductionLogService logService;
    @Resource
    private ProductionPlanService planService;
    @Resource
    private UnqualifiedTypeListService typeListService;
    @Resource
    private MaterialBomService bomService;
    @Resource
    private MaterialRepertoryIncomingCheckService materialRepertoryIncomingCheckService;
    @Resource
    private IncomingUnqualifiedService incomingUnqualifiedService;
    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;
    @Resource
    private ProductionLogDetailService productionLogDetailService;
    @Resource
    private ActivitiWorkflowService tasksService;
    @Resource
    private ProductionPlanService productionPlanService;


    @Override
    protected BaseService<UnqualifiedProductEntity, UnqualifiedProductInfo> getBaseService() {
        return service;
    }

    @RequestMapping("listMeasure")
    public Result<String> listMeasure(){
        JSONArray list = new JSONArray();
        for(DisposalMeasure measure :DisposalMeasure.values()){
            JSONObject obj = new JSONObject();
            obj.element("id",measure.getId());
            obj.element("name", measure.getName());
            list.add(obj);

        }
        return ResultUtils.success(list.toString());
    }

    @RequestMapping("taskDetail")
    public Result<UnqualifiedProductInfo> taskDetail(UnqualifiedProductInfo info){
        ExchangeUtils.convertBlank2Null(info);
        UnqualifiedProductInfo unqualifiedProductInfo = service.getModel(info.getId());
        return ResultUtils.success(unqualifiedProductInfo);
    }

    @RequestMapping("listByLike")
    public Result<Pager<UnqualifiedProductInfo>> listByLike(int pageNum, int pageSize, UnqualifiedProductInfo info){
        ExchangeUtils.convertBlank2Null(info);
        Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
        ProductionCheckLogInfo checkLogInfo = new ProductionCheckLogInfo();
        checkLogInfo.setCheckNumber(info.getOrderNo());
        Pager<ProductionCheckLogInfo> pager = productionCheckLogService.getModelListByLike(pageNum, pageSize, checkLogInfo, false);
        List<ProductionCheckLogInfo> logList = pager.getList();
        for(ProductionCheckLogInfo logInfo : logList){
            UnqualifiedProductInfo searchUnqualified = new UnqualifiedProductInfo();
            searchUnqualified.setCheckLogId(logInfo.getId());

            if(!service.isExist(searchUnqualified)){
                //检查订单信息
                ProductionLogDetailInfo searchLogInfo = new ProductionLogDetailInfo();
                searchLogInfo.setId(logInfo.getProductionLogId());
                List<ProductionLogDetailInfo> existProductionLog = productionLogDetailService.getListBy(searchLogInfo);
                if(existProductionLog.size()>0){
                    ProductionPlanInfo searchPlan = new ProductionPlanInfo();
                    searchPlan.setProductionNumber(existProductionLog.get(0).getProductionNumber());
                    List<ProductionPlanInfo> existPlans = planService.getListBy(searchPlan);
                    if(existPlans.size()>0){
                        searchUnqualified.setPurchaseOrderNo(existPlans.get(0).getContractId());
                    }

                    UnqualifiedTypeListInfo searchTypeList = new UnqualifiedTypeListInfo();
                    searchTypeList.setCheckId(logInfo.getId());
                    List<UnqualifiedTypeListInfo> existTypeList = typeListService.getListBy(searchTypeList);
                    if(existTypeList.size()>0){
                        JSONArray typeList = new JSONArray();
                        for(UnqualifiedTypeListInfo typeListInfo :existTypeList){
                            JSONObject type = new JSONObject();
                            type.element("id",typeListInfo.getId());
                            type.element("typeName", typeListInfo.getTypeName());
                            typeList.add(type);
                        }
                        //不合格类型
                        searchUnqualified.setUnqualifiedType(typeList.toString());
                    }
                }
                searchUnqualified.setProductionResponsible(logInfo.getProductPersonName());
                searchUnqualified.setProductionDate(logInfo.getProductDate());
                searchUnqualified.setProductionNo(logInfo.getProductionNumber());
                searchUnqualified.setProductionItemNo(logInfo.getProductionBatch());
                searchUnqualified.setMaterialName(logInfo.getMaterialName());
                searchUnqualified.setMaterialNumber(logInfo.getMaterialCode());
                //检查物料信息
                MaterialBomInfo searchBomInfo = new MaterialBomInfo();
                List<MaterialBomInfo> existMaterial = bomService.getListBy(searchBomInfo);
                if(existMaterial.size()>0) {
                    searchUnqualified.setOriginMaterial(existMaterial.get(0).getOriginalMaterial());
                    searchUnqualified.setParameters(existMaterial.get(0).getMaterialBomParamValueStr());
                }
                searchUnqualified.setQty(logInfo.getMaterialNum());
                searchUnqualified.setUnqualifiedQty(logInfo.getUnQualifiedNum());
                searchUnqualified.setUnqualifiedDescription(logInfo.getUnQualifiedRemark());
                searchUnqualified.setInspector(logInfo.getCheckPersonName());
                searchUnqualified.setInspectDate(logInfo.getCheckDate());
                Integer searchUnqualifiedNumber = service.getNextNumber();
                searchUnqualified.setOrderNo(StringUtils.frontCompWithZore(searchUnqualifiedNumber,11));
                searchUnqualified.setAttachmentFiles(logInfo.getFileUrl());
                service.insertBy(searchUnqualified);
            }
        }
        Pager<UnqualifiedProductInfo> reportPager = service.getModelListByLike(pageNum, pageSize, info, true);
        return ResultUtils.success(reportPager);
    }

    @RequestMapping("executeMeasure")
    public Result<UnqualifiedProductInfo> executeMeasure(UnqualifiedProductInfo info){
        ExchangeUtils.convertBlank2Null(info);
        if(info.getEditable()==null||info.getEditable()==0) {
            info.setEditable(1);
            info = getBaseService().saveOrUpdate(info);
        }
        DisposalMeasure measure = DisposalMeasure.getById(info.getDisposalHandleId());
        switch (measure){
            case DOANGIN:
                doangin(info);
                break;
            case REPAIR:
                repair(info);
                break;
            case COMPROMISE:
                compromise(info);
                break;
            case REJECTED:
                rejected(info);
                break;
            case CHANGED:
                changed(info);
                break;
        }
        return ResultUtils.success(info);
    }

    private void doangin(UnqualifiedProductInfo info){
        //生成生产计划单
        Format today = new SimpleDateFormat(Constants.DATE_FORMATTER);
        ProductionPlanInfo planInfo = new ProductionPlanInfo();
        planInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        planInfo.setStatus(1);
        MaterialBomInfo searchBom = new MaterialBomInfo();
        searchBom.setMaterialCode(info.getMaterialNumber());
        searchBom.setMaterialName(info.getMaterialName());
        List<MaterialBomInfo> bomInfos = bomService.getValidListByInfo(searchBom);
        if(bomInfos.size()>0) {
            planInfo.setMaterialBomParamValueStr(bomInfos.get(0).getMaterialBomParamValueStr());
            planInfo.setDrawingCode(bomInfos.get(0).getDrawingCode());
            planInfo.setFactoryMaterialCode(bomInfos.get(0).getFactoryMaterialCode());
            String numberValue = null;
            numberValue = materialRepertoryDetailService.getNumberValue(bomInfos.get(0).getNumber(), numberValue);
            planInfo.setNumber(numberValue);
            planInfo.setSource(bomInfos.get(0).getSource());
            planInfo.setProductBatch(info.getPurchaseOrderNo());
            planInfo.setMaterialCode(bomInfos.get(0).getMaterialCode());
            planInfo.setMaterialName(bomInfos.get(0).getMaterialName());
            planInfo.setMaterialUnit(bomInfos.get(0).getMaterialUnit());
            planInfo.setOriginalMaterial(bomInfos.get(0).getOriginalMaterial());
            planInfo.setMaterialNum(info.getUnqualifiedQty());
            planInfo.setMaterialId(bomInfos.get(0).getId());
            planInfo.setPlanProductDate(today.format(new Date()));
            planInfo.setLatestProductDate(today.format(new Date()));
            planInfo.setProductionType(3);
            planInfo.setIssueFlag(2);
            Integer planNumber = productionPlanService.getNextNumber();
            planInfo.setProductionNumber(frontCompWithZore(planNumber,11));
            planService.addProductionPlan(planInfo,planNumber);
        }else {
            throw new CommonBusinessException("不存在产品");
        }

    }

    private void repair(UnqualifiedProductInfo info){
        //生成生产计划单
        Format today = new SimpleDateFormat(Constants.DATE_FORMATTER);
        ProductionPlanInfo planInfo = new ProductionPlanInfo();
        planInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        planInfo.setStatus(1);
        MaterialBomInfo searchBom = new MaterialBomInfo();
        searchBom.setMaterialCode(info.getMaterialNumber());
        searchBom.setMaterialName(info.getMaterialName());
        List<MaterialBomInfo> bomInfos = bomService.getValidListByInfo(searchBom);
        if(bomInfos.size()>0) {
            planInfo.setMaterialBomParamValueStr(bomInfos.get(0).getMaterialBomParamValueStr());
            planInfo.setDrawingCode(bomInfos.get(0).getDrawingCode());
            planInfo.setFactoryMaterialCode(bomInfos.get(0).getFactoryMaterialCode());
            String numberValue = null;
            numberValue = materialRepertoryDetailService.getNumberValue(bomInfos.get(0).getNumber(), numberValue);
            planInfo.setNumber(numberValue);
            planInfo.setSource(bomInfos.get(0).getSource());
            planInfo.setProductBatch(info.getPurchaseOrderNo());
            planInfo.setMaterialCode(bomInfos.get(0).getMaterialCode());
            planInfo.setMaterialName(bomInfos.get(0).getMaterialName());
            planInfo.setMaterialUnit(bomInfos.get(0).getMaterialUnit());
            planInfo.setOriginalMaterial(bomInfos.get(0).getOriginalMaterial());
            planInfo.setMaterialNum(info.getUnqualifiedQty());
            planInfo.setMaterialId(bomInfos.get(0).getId());
            planInfo.setPlanProductDate(today.format(new Date()));
            planInfo.setLatestProductDate(today.format(new Date()));
            planInfo.setProductionType(4);
            planInfo.setIssueFlag(2);
            Integer planNumber = productionPlanService.getNextNumber();
            planInfo.setProductionNumber(frontCompWithZore(planNumber,11));
            planService.addProductionPlan(planInfo,planNumber);
            //生产计划代码完成


        }else {
            throw new CommonBusinessException("不存在产品");
        }
    }

    private void compromise(UnqualifiedProductInfo info){
        Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
        Integer incomingUnqualifiedNumber = incomingUnqualifiedService.getNextNumber();
        //修正当前检验记录中不合格数量至0，合格数量至全部
        //补进入库单
        ProductionCheckLogInfo searchCheckLog = new ProductionCheckLogInfo();
        searchCheckLog.setId(info.getCheckLogId());
        List<ProductionCheckLogInfo> existCheckLogs = productionCheckLogService.getListBy(searchCheckLog);
        if(existCheckLogs.size()>0){

            MaterialBomInfo searchBom = new MaterialBomInfo();
            searchBom.setMaterialCode(info.getMaterialNumber());
            searchBom.setMaterialName(info.getMaterialName());
            List<MaterialBomInfo> bomInfos = bomService.getValidListByInfo(searchBom);
            if(bomInfos.size()>0) {
                //调整检验记录
                ProductionCheckLogInfo currentCheckLogInfo = existCheckLogs.get(0);
                currentCheckLogInfo.setQualifiedNum(currentCheckLogInfo.getMaterialNum());
                currentCheckLogInfo.setUnQualifiedNum(0f);
                currentCheckLogInfo.setQualifiedPercent("100");
                currentCheckLogInfo.setCheckPercent("100");
                productionCheckLogService.updateByPrimaryKeySelective(currentCheckLogInfo);
                //补进入库单
                MaterialRepertoryIncomingCheckInfo incomeCheckInfo = new MaterialRepertoryIncomingCheckInfo();
                incomeCheckInfo.setOriginalMaterial(bomInfos.get(0).getOriginalMaterial());
                incomeCheckInfo.setMaterialCode(bomInfos.get(0).getMaterialCode());
                incomeCheckInfo.setMaterialId(bomInfos.get(0).getId());
                incomeCheckInfo.setMaterialName(bomInfos.get(0).getMaterialName());
                incomeCheckInfo.setQualifiedPercent("100");
                incomeCheckInfo.setQualifiedQty(info.getUnqualifiedQty());
                incomeCheckInfo.setUnqualifiedQty(0f);
                incomeCheckInfo.setCheckQty(info.getUnqualifiedQty());
                incomeCheckInfo.setCheckPercent("100");
                incomeCheckInfo.setMaterialSource(2);
                materialRepertoryIncomingCheckService.saveIncomingCheckout(incomeCheckInfo,incomingCheckNumber,incomingUnqualifiedNumber);
            }
        }else {
            throw new CommonBusinessException("检验记录不存在");
        }

    }

    private void rejected(UnqualifiedProductInfo info){
        Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
        Integer incomingUnqualifiedNumber = incomingUnqualifiedService.getNextNumber();
        Format today = new SimpleDateFormat(Constants.DATE_FORMATTER);
        ProductionPlanInfo planInfo = new ProductionPlanInfo();
        MaterialBomInfo searchBom = new MaterialBomInfo();
        searchBom.setMaterialCode(info.getMaterialNumber());
        searchBom.setMaterialName(info.getMaterialName());
        List<MaterialBomInfo> bomInfos = bomService.getValidListByInfo(searchBom);
        if(bomInfos.size()>0) {
            //补进入库单
            MaterialRepertoryIncomingCheckInfo incomeCheckInfo = new MaterialRepertoryIncomingCheckInfo();
            incomeCheckInfo.setOriginalMaterial(bomInfos.get(0).getOriginalMaterial());
            incomeCheckInfo.setMaterialCode(bomInfos.get(0).getMaterialCode());
            incomeCheckInfo.setMaterialId(bomInfos.get(0).getId());
            incomeCheckInfo.setMaterialName(bomInfos.get(0).getMaterialName());
            incomeCheckInfo.setQualifiedPercent("0");
            incomeCheckInfo.setUnqualifiedQty(info.getUnqualifiedQty());
            incomeCheckInfo.setQualifiedQty(0f);
            incomeCheckInfo.setCheckQty(info.getUnqualifiedQty());
            incomeCheckInfo.setCheckPercent("0");
            incomeCheckInfo.setMaterialSource(2);
            incomeCheckInfo.setUnqualifiedFlag(0);
            materialRepertoryIncomingCheckService.saveIncomingCheckout(incomeCheckInfo,incomingCheckNumber,incomingUnqualifiedNumber);
            //生成生产计划
            planInfo.setMaterialBomParamValueStr(bomInfos.get(0).getMaterialBomParamValueStr());
            planInfo.setDrawingCode(bomInfos.get(0).getDrawingCode());
            planInfo.setFactoryMaterialCode(bomInfos.get(0).getFactoryMaterialCode());
            String numberValue = null;
            numberValue = materialRepertoryDetailService.getNumberValue(bomInfos.get(0).getNumber(), numberValue);
            planInfo.setNumber(numberValue);
            planInfo.setSource(bomInfos.get(0).getSource());
            planInfo.setProductBatch(info.getPurchaseOrderNo());
            Integer planNumber = productionPlanService.getNextNumber();
            planInfo.setProductionNumber(frontCompWithZore(planNumber,11));
            planInfo.setMaterialCode(bomInfos.get(0).getMaterialCode());
            planInfo.setMaterialName(bomInfos.get(0).getMaterialName());
            planInfo.setMaterialUnit(bomInfos.get(0).getMaterialUnit());
            planInfo.setOriginalMaterial(bomInfos.get(0).getOriginalMaterial());
            planInfo.setMaterialNum(info.getUnqualifiedQty());
            planInfo.setMaterialId(bomInfos.get(0).getId());
            planInfo.setPlanProductDate(today.format(new Date()));
            planInfo.setLatestProductDate(today.format(new Date()));
            planInfo.setProductionType(5);

            planService.addProductionPlan(planInfo,planNumber);
        }
    }

    private void changed(UnqualifiedProductInfo info){
        Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
        Integer incomingUnqualifiedNumber = incomingUnqualifiedService.getNextNumber();
        //调整调换的产品
        //补进不合格数量的产品入库单
        if(info.getChangedMaterialId()!=null){
            MaterialBomInfo searchBom = new MaterialBomInfo();
//            searchBom.setMaterialCode(info.getMaterialNumber());
//            searchBom.setMaterialName(info.getMaterialName());
//            List<MaterialBomInfo> bomInfos = bomService.getValidListByInfo(searchBom);
            searchBom = bomService.getModel(info.getChangedMaterialId());
//            if(bomInfos.size()>0) {
                //补进入库单
                MaterialRepertoryIncomingCheckInfo incomeCheckInfo = new MaterialRepertoryIncomingCheckInfo();
                incomeCheckInfo.setOriginalMaterial(searchBom.getOriginalMaterial());
                incomeCheckInfo.setMaterialCode(searchBom.getMaterialCode());
                incomeCheckInfo.setMaterialId(searchBom.getId());
                incomeCheckInfo.setMaterialName(searchBom.getMaterialName());
                incomeCheckInfo.setQualifiedPercent("100");
                incomeCheckInfo.setUnqualifiedQty(0f);
                incomeCheckInfo.setQualifiedQty(info.getUnqualifiedQty());
                incomeCheckInfo.setCheckQty(info.getUnqualifiedQty());
                incomeCheckInfo.setCheckPercent("100");
                incomeCheckInfo.setMaterialSource(2);
                incomeCheckInfo.setMaterialSource(PRODUCTION_MATERIAL);
                materialRepertoryIncomingCheckService.saveIncomingCheckout(incomeCheckInfo,incomingCheckNumber,incomingUnqualifiedNumber);
//            }
        }
    }

    @RequestMapping("startTask")
    public Result<UnqualifiedProductInfo> startTask(UnqualifiedProductInfo info){
        ExchangeUtils.convertBlank2Null(info);
        info = service.saveOrUpdate(info);
        String bussinessKey = String.valueOf(info.getId());
        tasksService.startWorkflow(this.workflowKey, bussinessKey,null);
        return ResultUtils.success(info);
    }

    @RequestMapping("completeTask")
    public Result<UnqualifiedProductInfo> completeTask(UnqualifiedProductInfo info, String taskId,String taskComment){
        ExchangeUtils.convertBlank2Null(info);
        info = service.saveOrUpdate(info);
        Map<String, Object> taskVars = new HashMap<>();
        taskVars.put("disposalHandleId", String.valueOf(info.getDisposalHandleId()));
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(taskComment)){
            taskVars.put("taskComment",taskComment);
        }
        tasksService.compileTask(taskId, taskVars);
        return ResultUtils.success(info);
    }
}
