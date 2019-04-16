package com.ecms.service;

import com.ecms.bean.*;
import com.ecms.dal.entity.*;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

@Service
public class ProductionPlanService extends BaseService<ProductionPlanEntity, ProductionPlanInfo> {

    @Resource
    private MaterialBomManager materialBomManager;
	@Resource
    private ProductionPlanManager productionPlanManager;

	@Resource
    private MaterialRepertoryManager materialRepertoryManager;

	@Resource
    private SellerOrderManager sellerOrderManager;

	@Resource
    private SellerOrderProductManager sellerOrderProductManager;

	@Resource
    private MaterialBomService materialBomService;

	@Resource
    private MaterialRelationService materialRelationService;

	@Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;

	@Resource
    private ProductionIssueDetailManager productionIssueDetailManager;

    @Resource
    private ProductionIssueDetailService productionIssueDetailService;

    @Resource
    private ProductionIssueManager productionIssueManager;

    @Resource
    private ProductionLogManager productionLogManager;
    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

	//计划单
	private Integer PLAN_CREATE = 1;

	//手工添加
	private Integer HAND_CREATE = 2;

    @Override
    protected BaseManager<ProductionPlanEntity> getManager() {
        return productionPlanManager;
    }

    public List<ProductionPlanInfo> getProductionBatchList(int materialId){
        return productionPlanManager.getProductionBatchList(materialId);
    }

    public List<ProductionPlanInfo> getProductionNumberList(Integer materialId,String contractId){
        return productionPlanManager.getProductionNumberList(materialId,contractId);
    }

    public void addProductionPlan(ProductionPlanInfo productionPlanInfo,Integer planNumber){
        ProductionPlanEntity productionPlanEntity = ExchangeUtils.exchangeObject(productionPlanInfo, ProductionPlanEntity.class);
        Integer materialId = productionPlanInfo.getMaterialId();

        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
        mre.setMaterialId(materialId);
        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        mre.setStatus(STATUS_DEFAULT);
        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
        if(mreList.size() != 0){
            mre = mreList.get(0);
            mre.setStayProductionQty(mre.getStayProductionQty()+productionPlanInfo.getMaterialNum());
            materialRepertoryManager.updateByPrimaryKeySelective(mre);
        }
        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
        productionPlanEntity.setId(null);
        productionPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        productionPlanEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
        productionPlanEntity.setDrawingCode(mbe.getDrawingCode());
        productionPlanEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
        productionPlanEntity.setNumber(mbe.getType());
        productionPlanEntity.setOriginalMaterial(mbe.getOriginalMaterial());
        productionPlanEntity.setSource(mbe.getSource());
        productionPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
        productionPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
        productionPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        productionPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        productionPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
        productionPlanEntity.setPlanNumber(planNumber);
        productionPlanEntity.setProductionNumber(frontCompWithZore(planNumber,11));
        productionPlanEntity.setMaterialName(mbe.getMaterialName());
        productionPlanEntity.setMaterialId(mbe.getId());
        productionPlanEntity.setMaterialCode(mbe.getMaterialCode());

        if (productionPlanInfo.getProductionType()==null){
            productionPlanEntity.setProductionType(HAND_CREATE);
        }else {
            productionPlanEntity.setProductionType(productionPlanInfo.getProductionType());
        }
        productionPlanManager.insertBy(productionPlanEntity);
    }

    @Transactional
    public void updateByProductionNumber(ProductionPlanInfo productionPlanInfo){
        ProductionPlanEntity productionPlanEntity = ExchangeUtils.exchangeObject(productionPlanInfo, ProductionPlanEntity.class);
        ProductionPlanEntity ppe = productionPlanManager.getEntityById(productionPlanEntity.getId());
        /*if((ppe.getIssueQty()+productionPlanEntity.getIssueQty()) < ppe.getMaterialNum()){
            productionPlanEntity.setMaterialStatus(LACK_STATUS);
        }else{
            productionPlanEntity.setMaterialStatus(RE ADY_STATUS);
        }*/
        productionPlanEntity.setIssueQty(ppe.getIssueQty()+productionPlanEntity.getIssueQty());
        List<Map<String,Object>> materialTable = productionPlanInfo.getMaterialTable();
        productionPlanEntity.setParentProductionNumber(productionPlanInfo.getParentProductionNumber());
        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
        if(materialTable.size() != 0){
            for(int i=0;i<materialTable.size();i++){
                if(materialTable.get(i).get("productionIssueQty") != null && materialTable.get(i).get("productionIssueQty") != ""){
                    if(Float.parseFloat(materialTable.get(i).get("productionIssueQty").toString()) != 0){

                        Integer id = Integer.parseInt(materialTable.get(i).get("id").toString());
                        MaterialRepertoryDetailEntity mrde = materialRepertoryDetailManager.getEntityById(id);
                        Float productionIssueQty = Float.parseFloat(materialTable.get(i).get("productionIssueQty").toString());
                        mrde.setId(null);
                        mrde.setInventoryQty(-productionIssueQty);
                        mrde.setDetailid(id);
                        mrde.setOperatStatus(DISPATCH_MATERIAL);
                        materialRepertoryDetailManager.insertBy(mrde);

                        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                        mre.setMaterialId(Integer.parseInt(materialTable.get(i).get("materialId").toString()));
                        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                        if(mreList.size() != 0){
                            mre.setId(mreList.get(0).getId());
                            mre.setInventoryQty(mreList.get(0).getInventoryQty()-productionIssueQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        }

                        ProductionIssueEntity pie = new ProductionIssueEntity();

                        Integer materialId = Integer.parseInt(materialTable.get(i).get("materialId").toString());
                        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                        String numberValue = null;
                        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                        pie.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        pie.setNumber(numberValue);
                        pie.setSource(mbe.getSource());
                        pie.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        pie.setOriginalMaterial(mbe.getOriginalMaterial());
                        pie.setDrawingCode(mbe.getDrawingCode());

                        pie.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        pie.setPlanId(ppe.getId());
                        pie.setMaterialId(materialId);
                        pie.setProductionNumber(ppe.getProductionNumber());

                        ProductionIssueEntity piEntity = new ProductionIssueEntity();
                        List<ProductionIssueEntity> pieList = productionIssueManager.getListBy(pie);
                        ProductionIssueDetailEntity pide = new ProductionIssueDetailEntity();
                        ProductionIssueEntity productionIssueEntity = new ProductionIssueEntity();
                        if(pieList.size() != 0){
                            pie.setIssueQty(pieList.get(0).getIssueQty()+productionIssueQty);
                            pie.setId(pieList.get(0).getId());
                            productionIssueManager.updateByPrimaryKeySelective(pie);
                            pide.setIssuanceId(pieList.get(0).getId());
                        }else{
                            pie.setIssueQty(productionIssueQty);
                            productionIssueEntity = productionIssueManager.insertBy(pie);
                            pide.setIssuanceId(productionIssueEntity.getId());
                        }

                        if(materialTable.get(i).get("materialBatch") != null){
                            pide.setMaterialBatch(materialTable.get(i).get("materialBatch").toString());
                        }
                        Integer issuanceNumber = productionIssueDetailService.getNextNumber();
                        pide.setIssuanceNo(issuanceNumber);
                        pide.setIssuanceNumber(frontCompWithZore(issuanceNumber, 8));
                        pide.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        pide.setNumber(numberValue);
                        pide.setSource(mbe.getSource());
                        pide.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        pide.setOriginalMaterial(mbe.getOriginalMaterial());
                        pide.setDrawingCode(mbe.getDrawingCode());
                        pide.setContractId(ppe.getContractId());
                        pide.setMaterialId(Integer.parseInt(materialTable.get(i).get("materialId").toString()));
                        pide.setIssueQty(productionIssueQty);
                        pide.setRepertoryId(Integer.parseInt(materialTable.get(i).get("repertoryId").toString()));

                        pide.setMaterialName(materialTable.get(i).get("materialName").toString());
                        pide.setProductionNumber(ppe.getProductionNumber());
                        pide.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                        pide.setPlanId(ppe.getId());
                        pide.setMaterialRepertoryDetailId(Integer.parseInt(materialTable.get(i).get("id").toString()));
                        pide.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());

                        productionIssueDetailManager.insertBy(pide);
                    }

                }
            }
        }

    }

    public void deleteProductionPlan(String orderCode){
        productionPlanManager.deleteProductionPlan(orderCode);
    }

    /***
     * 获取产品下所有部件
     * @param id 物料id
     * @param contractId 合同号
     * @param sope 销售订单实体
     * @param orderCode 订单号
     */
    @Transactional
    public void getMaterielPart(SellerOrderEntity soe,Integer id, String contractId,SellerOrderProductEntity sope,String orderCode,String productBatch,Float materialNum){
        MaterialRelationInfo mrInfo = new MaterialRelationInfo();
        mrInfo.setMaterialParentId(id);
        mrInfo.setStatus(STATUS_DEFAULT);
        mrInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<MaterialRelationInfo> mriList = materialRelationService.getListBy(mrInfo);
        if (mriList.size() != 0){
            for (int i=0;i<mriList.size();i++){
                Integer materialChildId = mriList.get(i).getMaterialChildId();
                MaterialBomInfo mbi = materialBomService.getModel(materialChildId);
                MaterialRelationInfo mri = new MaterialRelationInfo();
                mri.setMaterialParentId(materialChildId);
                mriList.get(i).setMaterialBomInfo(mbi);
                if("自制".equals(mbi.getSource())){
                    ProductionPlanEntity proPlanEntity = new ProductionPlanEntity();
                    proPlanEntity.setProductBatch(productBatch);
                    Integer materialId = mriList.get(i).getMaterialBomInfo().getId();
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(materialId);
                    Float inventoryQty = 0f;
                    Float completedQty = 0f;
                    List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                    Float productionQty = mriList.get(i).getQuantity()*materialNum;
                    if(mreList.size() != 0){
                        inventoryQty = mreList.get(0).getInventoryQty();
                        completedQty = productionLogManager.getCompletedQty(soe.getOrderCode(),materialId,LoginFilter.getCurrentUser().getCompanyId());
                        if(completedQty == null){
                            completedQty = 0f;
                        }
                        if(inventoryQty+completedQty<productionQty){
                            Float productionNum = productionQty-inventoryQty-completedQty;
                            proPlanEntity.setMaterialNum(productionNum);
                            proPlanEntity.setMaterialName(mriList.get(i).getMaterialBomInfo().getMaterialName());
                            proPlanEntity.setMaterialId(materialId);
                            proPlanEntity.setOriginalMaterial(mriList.get(i).getMaterialBomInfo().getOriginalMaterial());
                            proPlanEntity.setMaterialUnit(mriList.get(i).getMaterialBomInfo().getMaterialUnit());
                            proPlanEntity.setContractId(contractId);
                            proPlanEntity.setProductionType(PLAN_CREATE);
                            proPlanEntity.setOrderCode(orderCode);
                            proPlanEntity.setIssueFlag(PRODUCE_PLAN);
                            proPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                            proPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                            proPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            proPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                            //产品类型，0:物料，1:产品
                            Integer typeFlag = mriList.get(i).getMaterialBomInfo().getFlag();
                            if (typeFlag == 1) {
                                proPlanEntity.setProductType(PRODUCT_TYPE);
                            } else {
                                proPlanEntity.setProductType(MATERIAL_TYPE);
                            }
                            ///todo 计划生产时间
                            proPlanEntity.setPlanProductDate(sope.getDeliveryDate());
                            ///todo 最晚生产时间为交货时间-生产所需时间
                            proPlanEntity.setLatestProductDate(sope.getDeliveryDate());
                            proPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);

                            MaterielInfo materielInfo = new MaterielInfo();
                            materielInfo.setMaterialId(mriList.get(i).getMaterialChildId());

                            /*MaterialRepertoryInfo materialRepertoryInfo = new MaterialRepertoryInfo();
                            materialRepertoryInfo.setMaterialId(mbi.getId());
                            List<MaterialRepertoryInfo> MaterialRepertoryInfoList = materialRepertoryService.getListBy(materialRepertoryInfo);

                            //同一物料总库存
                            Float onhandTotal = 0.00f;
                            if(mriList.size() != 0){
                                for(MaterialRepertoryInfo mrInfo : MaterialRepertoryInfoList){
                                    onhandTotal = mrInfo.getInventoryQty();
                                }
                            }
                            //所需物料数量
                            quantity = mriList.get(i).getQuantity();
                            if (quantity == null || "".equals(quantity)) {
                                quantity = 0.00f;
                            }
                            //判断库存是否足够，足够则物料状态为备齐，不够则缺失
                            if (onhandTotal >= Float.parseFloat(sope.getProductNumber().toString()) * quantity) {
                                proPlanEntity.setMaterialStatus(READY_STATUS);
                            } else {
                                proPlanEntity.setMaterialStatus(LACK_STATUS);
                            }*/
                            //添加记录
                            ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(proPlanEntity);
                            //生产单号
                            String productionNumber = frontCompWithZore(productionPlanEntity.getId(),11);
                            productionPlanEntity.setProductionNumber(productionNumber);
                            productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
                            getMaterielPart(soe,materialChildId,contractId,sope,orderCode,productBatch,mriList.get(i).getQuantity()*materialNum);
                        }
                    }
                }else{
                    continue;
                }
            }
        }
    }

    @Transactional
    public void autoIssue(ProductionPlanInfo productionPlanInfo){
        ProductionPlanEntity productionPlanEntity = ExchangeUtils.exchangeObject(productionPlanInfo, ProductionPlanEntity.class);
        List<Map<String,Object>> materialTable = productionPlanInfo.getMaterialTable();
        productionPlanEntity.setParentProductionNumber(productionPlanInfo.getParentProductionNumber());
        //应发数量
        Float needIssueQty = 0f;
        //已发数量
        Float issueQty = 0f;
        //剩余应发数量
        Float surplusIssueQty = 0f;
        //生产单号
        String productionNumber = productionPlanInfo.getProductionNumber();
        Integer productionId = productionPlanInfo.getId();
        if(materialTable.size() != 0){
            Boolean issueFlag = false;
            for(int i=0;i<materialTable.size();i++){
                Integer materialId = Integer.parseInt(materialTable.get(i).get("materialId").toString());
                MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                materialRepertoryEntity.setMaterialId(materialId);
                List<MaterialRepertoryEntity> materialRepertoryEntityList = materialRepertoryManager.getListBy(materialRepertoryEntity);
                if(materialRepertoryEntityList.size() != 0){
                    materialRepertoryEntity = materialRepertoryEntityList.get(0);
                    Float invQty = materialRepertoryEntity.getInventoryQty();
                    if(invQty != 0){
                        issueFlag = true;
                    }
                }
            }
            if(issueFlag == true){
                for(int i=0;i<materialTable.size();i++){
                    needIssueQty = Float.parseFloat(materialTable.get(i).get("needIssueQty").toString());
                    issueQty = Float.parseFloat(materialTable.get(i).get("issueQty").toString());
                    if(needIssueQty>issueQty){
                        Integer materialId = Integer.parseInt(materialTable.get(i).get("materialId").toString());
                        surplusIssueQty = needIssueQty - issueQty;
                        MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
                        mrde.setMaterialId(materialId);
                        mrde.setStatus(STATUS_DEFAULT);
                        //更新仓库物料库存（仓库物料表和明细表同步更新）
                        List<MaterialRepertoryDetailEntity> mrdeList = materialRepertoryDetailManager.getListBy(mrde);
                        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                        mre.setMaterialId(materialId);
                        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                        if(mreList.size() != 0){
                            mre = mreList.get(0);
                            if(mre.getInventoryQty() >= surplusIssueQty){
                                mre.setInventoryQty(mre.getInventoryQty()-surplusIssueQty);
                                materialRepertoryManager.updateByPrimaryKeySelective(mre);
                            }else {
                                mre.setInventoryQty(0f);
                                materialRepertoryManager.updateByPrimaryKeySelective(mre);
                            }
                        }
                        if(mrdeList.size() != 0){
                            for(MaterialRepertoryDetailEntity mrdEntity : mrdeList){
                                ProductionPlanEntity ppe = productionPlanManager.getEntityById(productionPlanEntity.getId());
                                Float usedInventoryQty = materialRepertoryDetailManager.getUseQty(mrdEntity.getId());
                                Float inventoryQty = mrdEntity.getInventoryQty()+usedInventoryQty;
                                if(inventoryQty != 0 && inventoryQty>0){
                                    MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                                    Integer issueId;
                                    if(surplusIssueQty <= inventoryQty){
                                        mrdEntity.setInventoryQty(-surplusIssueQty);
                                        mrdEntity.setDetailid(mrdEntity.getId());
                                        mrdEntity.setId(null);
                                        mrdEntity.setOperatStatus(DISPATCH_MATERIAL);
                                        materialRepertoryDetailManager.insertBy(mrdEntity);
                                        ProductionIssueEntity pie = new ProductionIssueEntity();
                                        pie.setProductionNumber(productionNumber);
                                        pie.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                        pie.setPlanId(productionId);
                                        pie.setMaterialId(materialId);
                                        List<ProductionIssueEntity> pieList = productionIssueManager.getListBy(pie);
                                        if(pieList.size() != 0){
                                            ProductionIssueEntity piEntity = pieList.get(0);
                                            issueId = piEntity.getId();
                                            piEntity.setIssueQty(pie.getIssueQty()+surplusIssueQty);
                                            productionIssueManager.updateByPrimaryKeySelective(piEntity);
                                        }else {
                                            pie.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                                            pie.setNumber(mbe.getType());
                                            pie.setSource(mbe.getSource());
                                            pie.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                                            pie.setOriginalMaterial(mbe.getOriginalMaterial());
                                            pie.setDrawingCode(mbe.getDrawingCode());

                                            pie.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                            pie.setPlanId(productionId);
                                            pie.setMaterialId(materialId);
                                            pie.setProductionNumber(productionNumber);
                                            pie.setIssueQty(surplusIssueQty);
                                            ProductionIssueEntity piEntity = productionIssueManager.insertBy(pie);
                                            issueId = piEntity.getId();
                                        }
                                        ProductionIssueDetailEntity pide = new ProductionIssueDetailEntity();
                                        Integer issuanceNumber = productionIssueDetailService.getNextNumber();
                                        pide.setIssuanceNo(issuanceNumber);
                                        pide.setIssuanceNumber(frontCompWithZore(issuanceNumber, 8));
                                        pide.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                                        pide.setNumber(mbe.getType());
                                        pide.setSource(mbe.getSource());
                                        pide.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                                        pide.setOriginalMaterial(mbe.getOriginalMaterial());
                                        pide.setDrawingCode(mbe.getDrawingCode());
                                        pide.setContractId(ppe.getContractId());
                                        pide.setMaterialId(materialId);
                                        pide.setIssueQty(surplusIssueQty);
                                        pide.setRepertoryId(mrdEntity.getRepertoryId());

                                        pide.setMaterialName(mbe.getMaterialName());
                                        pide.setProductionNumber(productionNumber);
                                        pide.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                                        pide.setPlanId(productionId);
                                        pide.setMaterialRepertoryDetailId(mrdEntity.getId());
                                        pide.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                        pide.setIssuanceId(issueId);
                                        pide.setMaterialBatch(mrdEntity.getMaterialBatch());

                                        productionPlanEntity.setIssueQty(ppe.getIssueQty()+surplusIssueQty);
                                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                                        productionIssueDetailManager.insertBy(pide);
                                        break;
                                    }else {
                                        ProductionIssueEntity pie = new ProductionIssueEntity();
                                        pie.setProductionNumber(productionNumber);
                                        pie.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                        pie.setPlanId(productionId);
                                        pie.setMaterialId(materialId);
                                        List<ProductionIssueEntity> pieList = productionIssueManager.getListBy(pie);
                                        if(pieList.size() != 0){
                                            ProductionIssueEntity piEntity = pieList.get(0);
                                            issueId = piEntity.getId();
                                            Float issueQty1 = 0f;
                                            if(pie.getIssueQty() != null){
                                                issueQty1 = pie.getIssueQty();
                                            }
                                            piEntity.setIssueQty(issueQty1+inventoryQty);
                                            productionIssueManager.updateByPrimaryKeySelective(piEntity);
                                        }else {
                                            pie.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                                            pie.setNumber(mbe.getType());
                                            pie.setSource(mbe.getSource());
                                            pie.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                                            pie.setOriginalMaterial(mbe.getOriginalMaterial());
                                            pie.setDrawingCode(mbe.getDrawingCode());

                                            pie.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                            pie.setPlanId(productionId);
                                            pie.setMaterialId(materialId);
                                            pie.setProductionNumber(productionNumber);
                                            pie.setIssueQty(inventoryQty);
                                            ProductionIssueEntity piEntity = productionIssueManager.insertBy(pie);
                                            issueId = piEntity.getId();
                                        }
                                        ProductionIssueDetailEntity pide = new ProductionIssueDetailEntity();
                                        Integer issuanceNumber = productionIssueDetailService.getNextNumber();
                                        pide.setIssuanceNo(issuanceNumber);
                                        pide.setIssuanceNumber(frontCompWithZore(issuanceNumber, 8));
                                        pide.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                                        pide.setNumber(mbe.getType());
                                        pide.setSource(mbe.getSource());
                                        pide.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                                        pide.setOriginalMaterial(mbe.getOriginalMaterial());
                                        pide.setDrawingCode(mbe.getDrawingCode());
                                        pide.setContractId(ppe.getContractId());
                                        pide.setMaterialId(materialId);
                                        pide.setIssueQty(inventoryQty);
                                        pide.setRepertoryId(mrdEntity.getRepertoryId());

                                        pide.setMaterialName(mbe.getMaterialName());
                                        pide.setProductionNumber(productionNumber);
                                        pide.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                                        pide.setPlanId(productionId);
                                        pide.setMaterialRepertoryDetailId(mrdEntity.getId());
                                        pide.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                                        pide.setIssuanceId(issueId);
                                        pide.setMaterialBatch(mrdEntity.getMaterialBatch());
                                        productionIssueDetailManager.insertBy(pide);

                                        productionPlanEntity.setIssueQty(ppe.getIssueQty()+inventoryQty);
                                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                                        surplusIssueQty = surplusIssueQty - mrdEntity.getInventoryQty();
                                        mrdEntity.setInventoryQty(-inventoryQty);
                                        mrdEntity.setDetailid(mrdEntity.getId());
                                        mrdEntity.setId(null);
                                        mrdEntity.setOperatStatus(DISPATCH_MATERIAL);
                                        materialRepertoryDetailManager.insertBy(mrdEntity);

                                    }
                                }
                            }
                        }
                    }
                }
            }else {
                throw new CommonBusinessException("发料失败，库存不足！");
            }
        }
    }
    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_plan_";
    }
}
