package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

/**
 * 仓库物料controller
 * @author Richie
 * @version 2018/10/25 16:51
 */
@RestController
@RequestMapping("materialRepertory")
public class MaterialRepertoryController extends BaseController<MaterialRepertoryEntity, MaterialRepertoryInfo> {

    @Resource
    private MaterialRepertoryService materialRepertoryService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Resource
    private MaterialBomParamSpecificationService materialBomParamSpecificationService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private ProductionIssueService productionIssueService;

    @Resource
    private ProduceMaterialReturnLogService produceMaterialReturnLogService;

    @Override
    protected BaseService<MaterialRepertoryEntity, MaterialRepertoryInfo> getBaseService() {
        return materialRepertoryService;
    }

    @RequestMapping("getListBylike")
    public Result<Pager<MaterialRepertoryInfo>> listByLike(int pageNum, int pageSize, MaterialRepertoryInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        info.setMaterialSource("外购");
        Pager<MaterialRepertoryInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<MaterialRepertoryInfo> mriList = pager.getList();
        if(mriList.size() != 0){
            for(MaterialRepertoryInfo mri : mriList){
                Integer materialId = mri.getMaterialId();
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mri.setMaterialUnit(mbe.getMaterialUnit());
                ProductTypeInfo productTypeinfo = new ProductTypeInfo();
                productTypeinfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductTypeInfo> ptyList = productTypeService.getListBy(productTypeinfo);
                if(ptyList.size() != 0){
                    mri.setProductType(ptyList.get(0).getType());
                }
            }
        }
        return ResultUtils.success(pager);
    }

    @RequestMapping("getListByLikeAll")
    public Result<Pager<MaterialRepertoryInfo>> getListByLikeAll(int pageNum, int pageSize, MaterialRepertoryInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<MaterialRepertoryInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<MaterialRepertoryInfo> mriList = pager.getList();
        if(mriList.size() != 0){
            //投料数量
            Float issueQty = 0f;
            for(MaterialRepertoryInfo mri : mriList){
                Integer materialId = mri.getMaterialId();
                issueQty = productionIssueService.getIssueQty(materialId);
                ProduceMaterialReturnLogInfo produceMaterialReturnLogInfo = new ProduceMaterialReturnLogInfo();
                produceMaterialReturnLogInfo.setMaterialId(materialId);
                produceMaterialReturnLogInfo.setStatus(STATUS_DEFAULT);
                produceMaterialReturnLogInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProduceMaterialReturnLogInfo> pmrliList = produceMaterialReturnLogService.getListBy(produceMaterialReturnLogInfo);
                Float returnQty = 0f;
                if(pmrliList.size() != 0){
                    for(ProduceMaterialReturnLogInfo pmrli : pmrliList){
                        returnQty += pmrli.getReturnQty();
                    }
                }
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mri.setMaterialUnit(mbe.getMaterialUnit());
                mri.setIssueQty(issueQty-returnQty);
                mri.setVersionVumStr("V"+mbe.getVersionNum()+".0");
                /*PurchaseOrdersStayInfo purchaseOrdersStayInfo = new PurchaseOrdersStayInfo();
                purchaseOrdersStayInfo.setStatus(STATUS_DEFAULT);
                purchaseOrdersStayInfo.setPurchaseStayStatus(NO_ORDER);
                purchaseOrdersStayInfo.setMaterialId(mri.getMaterialId());
                List<PurchaseOrdersStayInfo> posiList = purchaseOrdersStayService.getListBy(purchaseOrdersStayInfo);
                Float stayPurQty = 0f;
                if(posiList.size() != 0){
                    for(PurchaseOrdersStayInfo posi : posiList){
                        stayPurQty += posi.getStayPurQty();
                    }
                }

                PurchaseOrdersListInfo purchaseOrderListInfo = new PurchaseOrdersListInfo();
                purchaseOrderListInfo.setMaterialId(mri.getMaterialId());
                List<PurchaseOrdersListInfo> poliList = purchaseOrdersListService.getListBy(purchaseOrderListInfo);
                Float stayDeliveredQty = 0f;
                if(poliList.size() != 0){
                    for(PurchaseOrdersListInfo poli : poliList){
                        PurchaseOrdersInfo purchaseOrdersInfo = purchaseOrdersService.getModel(poli.getPurchaseOrdersId());
                        if(!purchaseOrdersInfo.getReviewStatus().equals("2")){
                            stayDeliveredQty += poli.getUnReceivedQty();
                        }
                    }
                }
                mri.setStayDeliveredQty(stayDeliveredQty);
                mri.setStayPurchaseQty(stayPurQty);*/
            }
        }
        return ResultUtils.success(pager);
    }


    /**
     * 更新安全库存数
     * @param materialRepertoryInfo
     * @return
     */
    @RequestMapping("updateInventorySafeQty")
    public Result<Void> updateInventorySafeQty(MaterialRepertoryInfo materialRepertoryInfo){
        materialRepertoryService.saveOrUpdate(materialRepertoryInfo);
        return ResultUtils.success();
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
}
