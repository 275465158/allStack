package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.*;
import com.ecms.manager.MaterialRepertoryIncomingCheckManager;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.*;

@RestController
@RequestMapping("productionPlan")
public class ProductionPlanController extends BaseController<ProductionPlanEntity, ProductionPlanInfo>  {

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProductionPlanService productionPlanService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Resource
    private MaterialBomParamSpecificationService materialBomParamSpecificationService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private MaterialRelationService materialRelationService;

    @Resource
    private ProductionLogService productionLogService;

    @Resource
    private SellerOrderService sellerOrderService;

    @Resource
    private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;

    @Resource
    private ProductionLogDetailService productionLogDetailService;

    @Override
    protected BaseService<ProductionPlanEntity, ProductionPlanInfo> getBaseService() {
        return productionPlanService;
    }

    /**
     * 获取生产计划列表
     *
     * @param pageNum 页数
     * @param ppi     ProductionPlanInfo
     * @return
     */
    @RequestMapping("productionPlanList")
    public Result<Pager<ProductionPlanInfo>> productionPlanList(int pageNum, ProductionPlanInfo ppi){
        ExchangeUtils.convertBlank2Null(ppi);
        ppi.setStatus(STATUS_DEFAULT);
        ppi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        ppi.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
        Pager<ProductionPlanInfo> pager = productionPlanService.getModelListByLike(pageNum, DEFAULT_PAGE_SIZE, ppi,false);
        if(pager.getList().size() != 0){
            for(ProductionPlanInfo productionPlanInfo : pager.getList()){
                if(productionPlanInfo.getMaterialId() != null && !"".equals(productionPlanInfo.getMaterialId())){
                    MaterialBomEntity mbe = materialBomService.getEntity(productionPlanInfo.getMaterialId());
                    productionPlanInfo.setMaterialCode(mbe.getMaterialCode());
                    productionPlanInfo.setOriginalMaterial(mbe.getOriginalMaterial());
                    productionPlanInfo.setMaterialParameter(getParamValue(productionPlanInfo.getMaterialId()));
                    productionPlanInfo.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    ProductTypeInfo productTypeInfo = new ProductTypeInfo();
                    productTypeInfo.setNumber(mbe.getNumber());
                    productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    productTypeInfo.setStatus(STATUS_DEFAULT);
                    List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
                    if(ptiList.size() != 0){
                        productionPlanInfo.setNumberType(ptiList.get(0).getType());
                    }
                    productionPlanInfo.setDrawingCode(mbe.getDrawingCode());
                }
                if(productionPlanInfo.getProductionNumber() != null){
                    ProductionLogInfo productionLogInfo = new ProductionLogInfo();
                    productionLogInfo.setProductionNumber(productionPlanInfo.getProductionNumber());
                    productionLogInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductionLogInfo> pliList = productionLogService.getListBy(productionLogInfo);
                    Float completedQty = 0.00f;
                    if(pliList.size() != 0){
                        for(ProductionLogInfo pli : pliList){
                            completedQty = pli.getCompletedQty();
                            productionPlanInfo.setCompletedQty(completedQty);
                        }
                    }
                    productionPlanInfo.setStayProductionNum(productionPlanInfo.getMaterialNum()-completedQty);
                }
            }
        }
        return ResultUtils.success(pager);
    }

    /**
     * 获取生产发料列表
     * @param pageNum
     * @param ppi
     * @return
     */
    @RequestMapping("getProductionIssueList")
    public Result<Pager<ProductionPlanInfo>> getProductionIssueList(int pageNum, ProductionPlanInfo ppi){
        //获取未完成待生产列表
        ExchangeUtils.convertBlank2Null(ppi);
        ppi.setIssueFlag(PRODUCE_PLAN);
        ppi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        ppi.setStatus(STATUS_DEFAULT);
        ppi.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
        Pager<ProductionPlanInfo> pager = productionPlanService.getModelListByLike(pageNum, DEFAULT_PAGE_SIZE, ppi,false);
        return ResultUtils.success(pager);
    }

    /**
     * 获取生产记录列表
     *
     * @param pageNum            页数
     * @param productionPlanInfo ProductionPlanInfo
     * @return
     */
    @RequestMapping("getProductionLogList")
    public Result<Pager<ProductionPlanInfo>> getProductionLogList(int pageNum, ProductionPlanInfo productionPlanInfo) {
        ExchangeUtils.convertBlank2Null(productionPlanInfo);
        productionPlanInfo.setProductionStatus(PRODUCTION_STATUS_COMPLETE);
        Pager<ProductionPlanInfo> pager = productionPlanService.getModelList(pageNum, DEFAULT_PAGE_SIZE, productionPlanInfo);
        return ResultUtils.success(pager);
    }

    /**
     * 自动补零
     * @param sourceDate 参数
     * @param formatLength 长度
     * @return
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        /**
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }

    /**
     * 根据物料id获取生产项次
     * @param materialId 物料id
     * @return
     */
    @RequestMapping("getProductionBatchList")
    public Result<List<ProductionPlanInfo>> getProductionBatchList(int materialId){
        List<ProductionPlanInfo> ppiList = productionPlanService.getProductionBatchList(materialId);
        return ResultUtils.success(ppiList);
    }

    /**
     * 根据合同号和物料编号获取生产单号
     * @param productionPlanInfo
     * @return
     */
    @RequestMapping("getProductionNumberList")
    public Result<List<ProductionPlanInfo>> getProductionNumberList(ProductionPlanInfo productionPlanInfo){
        List<ProductionPlanInfo> ppiList = productionPlanService.getProductionNumberList(productionPlanInfo.getMaterialId(),productionPlanInfo.getContractId());
        return ResultUtils.success(ppiList);
    }

    @RequestMapping("materialList")
    public Result<List<ProductionPlanInfo>> getMaterialList(int materialId){
        List<ProductionPlanInfo> pager = productionPlanService.getProductionBatchList(materialId);
        return ResultUtils.success(pager);
    }

    @RequestMapping("getProductionByProductionNumber")
    public Result<List<ProductionPlanInfo>> getProductionByProductionNumber(String parentProductionNumber,Integer materialId){
        ProductionPlanInfo productionPlanInfo = new ProductionPlanInfo();
        productionPlanInfo.setParentProductionNumber(parentProductionNumber);
        List<ProductionPlanInfo> ppiList = productionPlanService.getListBy(productionPlanInfo);
        if(ppiList.size() != 0){
            productionPlanInfo.setMaterialId(materialId);
            for(ProductionPlanInfo ppi : ppiList){
                MaterialBomEntity mbe = materialBomService.getEntity(ppi.getMaterialId());
                ppi.setMaterialCode(mbe.getMaterialCode());
                ppi.setOriginalMaterial(mbe.getOriginalMaterial());
                ProductTypeInfo productTypeInfo = new ProductTypeInfo();
                productTypeInfo.setNumber(mbe.getNumber());
                productTypeInfo.setStatus(STATUS_DEFAULT);
                productTypeInfo.setCompanyId(mbe.getCompanyId());
                List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
                if(ptiList.size() != 0){
                    ppi.setMaterialType(ptiList.get(0).getType());
                }
                ppi.setMaterialParam(getParamValue(mbe.getId()));

                MaterialRelationInfo mri = new MaterialRelationInfo();
                mri.setMaterialParentId(productionPlanInfo.getMaterialId());
                mri.setMaterialChildId(mbe.getId());
                mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mri.setStatus(STATUS_DEFAULT);
                List<MaterialRelationInfo> mriList = materialRelationService.getListBy(mri);
                if(mriList.size() != 0){
                    ppi.setQuantity(mriList.get(0).getQuantity());
                }
            }
        }
        return ResultUtils.success(ppiList);
    }

    /**
     * 生产发料
     * @param info
     */
    @RequestMapping("insertIssue")
    public Result insertIssue(ProductionPlanInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        productionPlanService.updateByProductionNumber(info);
        return ResultUtils.success();
    }

    /**
     * 根据id查询详情
     * @param productionPlanInfo
     * @return
     */
    @RequestMapping("/addProductionPlan")
    public Result<Void> addProductionPlan(ProductionPlanInfo productionPlanInfo){
        Integer planNumber = productionPlanService.getNextNumber();
        productionPlanService.addProductionPlan(productionPlanInfo,planNumber);
        return ResultUtils.success();
    }

    /**
     * 获取生产计划实体
     * @param id 生产计划列表id
     * @return
     */
    @RequestMapping("getEntityById")
    public Result<ProductionPlanInfo> getEntityById(Integer id){
        ProductionPlanInfo pri = productionPlanService.getModel(id);
        if(pri.getId() != null){
            MaterialBomEntity mbe = materialBomService.getEntity(pri.getMaterialId());
            pri.setMaterialCode(mbe.getMaterialCode());
            pri.setOriginalMaterial(mbe.getOriginalMaterial());
            pri.setMaterialParameter(getParamValue(pri.getMaterialId()));
            if(pri.getProductionType() == 1){
                pri.setProductionTypeName("计划单");
            }else{
                pri.setProductionTypeName("手工添加");
            }

            if(pri.getProductionNumber() != null){
                MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
                mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrice.setStatus(STATUS_DEFAULT);
                mrice.setMaterialSource(2);
                mrice.setPurchaseReason(pri.getProductionNumber());
                List<MaterialRepertoryIncomingCheckEntity> mriceList = materialRepertoryIncomingCheckManager.getListBy(mrice);
                Float completedQty = 0f;
                if(mriceList.size() != 0){
                    for(MaterialRepertoryIncomingCheckEntity mricEntity : mriceList){
                        completedQty += mricEntity.getInventoryQty();
                    }
                }
                pri.setCompletedQty(completedQty);
                pri.setStayProductionNum(pri.getMaterialNum()-completedQty);
            }
            if(pri.getProductBatch() != null){
                SellerOrderInfo soi = new SellerOrderInfo();
                soi.setCompanyId(pri.getCompanyId());
                soi.setOrderCode(pri.getProductBatch());
                List<SellerOrderInfo> soiList = sellerOrderService.getListBy(soi);
                if(soiList.size() != 0){
                    pri.setTradeMark(soiList.get(0).getTradeMark());
                }
            }
        }
        return ResultUtils.success(pri);
    }

    @RequestMapping("getUnfinishedProductList")
    public Result<List<ProductionPlanInfo>> getUnfinishedProductList(Integer materialId){
        ProductionPlanInfo ppi = new ProductionPlanInfo();
        ppi.setMaterialId(materialId);
        List<ProductionPlanInfo> ppiList = productionPlanService.getListBy(ppi);
        return ResultUtils.success(ppiList);
    }


    /**
     * 获取拼接的参数值
     * @param id
     * @return
     */
    public String getParamValue(Integer id){
        //根据子id去查部件-参数表
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialId(id);
        materialBomParamNameInfo.setStatus(1);
        List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
        //根据子id去查部件-参数规格表
        MaterialBomParamSpecificationInfo materialBomParamSpecificationInfo = new MaterialBomParamSpecificationInfo();
        materialBomParamSpecificationInfo.setMaterialId(id);
        materialBomParamSpecificationInfo.setStatus(1);
        List<MaterialBomParamSpecificationInfo> mbpsiList =  materialBomParamSpecificationService.getListBy(materialBomParamSpecificationInfo);
        String materialBomParamNameValueStr = "";
        if (mbpniList.size()!=0){
            for (int j = 0;j < mbpniList.size(); j++){
                if (j<mbpniList.size()-1) {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
                }else {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
                }
            }
        }
        String materialBomParamSpecificationValueStr = "";
        if (mbpsiList.size()!=0){
            for (int k = 0;k < mbpsiList.size();k++){
                if (k<mbpsiList.size()-1){
                    materialBomParamSpecificationValueStr += mbpsiList.get(k).getMaterialParamSpecificationValue() + ",";
                }else {
                    materialBomParamSpecificationValueStr += mbpsiList.get(k).getMaterialParamSpecificationValue();
                }
            }
        }
        if (materialBomParamNameValueStr != "" && materialBomParamSpecificationValueStr != ""){
            return (materialBomParamNameValueStr+","+materialBomParamSpecificationValueStr);
        }else if (materialBomParamNameValueStr != "" && materialBomParamSpecificationValueStr == ""){
            return (materialBomParamNameValueStr);
        }else if (materialBomParamNameValueStr == "" && materialBomParamSpecificationValueStr != ""){
            return (materialBomParamSpecificationValueStr);
        }else {
            return (null);
        }
    }

    @RequestMapping("getStayProductionList")
    public Result<Pager<ProductionPlanInfo>> getStayProductionList(int pageNum, ProductionPlanInfo productionPlanInfo) {
        ExchangeUtils.convertBlank2Null(productionPlanInfo);
        productionPlanInfo.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
        Pager<ProductionPlanInfo> pager = productionPlanService.getModelList(pageNum, DEFAULT_PAGE_SIZE, productionPlanInfo);
        List<ProductionPlanInfo> ppiList = pager.getList();
        if(ppiList.size() != 0){
            Float completeQty = 0f;
            for(ProductionPlanInfo ppi : ppiList){
                completeQty = productionLogDetailService.getProductionCompleteQty(ppi.getCompanyId(),ppi.getProductionNumber(),ppi.getMaterialId());
                if(completeQty == null){
                    completeQty = 0f;
                }
                ppi.setStayProductionQty(ppi.getMaterialNum() - completeQty);
            }
        }
        return ResultUtils.success(pager);
    }

    /**
     * 自动发料
     * @param productionPlanInfo
     * @return
     */
    @RequestMapping("/autoIssue")
    public Result<Void> autoIssue(ProductionPlanInfo productionPlanInfo){
        ExchangeUtils.convertBlank2Null(productionPlanInfo);
        productionPlanService.autoIssue(productionPlanInfo);
        return ResultUtils.success();
    }
}
