package com.ecms.service;

import com.ecms.bean.MaterialProcessInfo;
import com.ecms.bean.MaterialRelationInfo;
import com.ecms.bean.ProductionLogInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;


@Service
public class ProductionLogService extends BaseService<ProductionLogEntity, ProductionLogInfo> {

	@Resource
    private ProductionLogManager productionLogManager;

	@Resource
    private ProductionCheckLogManager productionCheckLogManager;

	@Resource
    private ProductionPlanManager productionPlanManager;

	@Resource
    private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;

	@Resource
    private ProductionLogDetailManager productionLogDetailManager;

	@Resource
    private MaterialRelationService materialRelationService;

	@Resource
    private ProductionIssueManager productionIssueManager;

	@Resource
    private UnqualifiedTypeListManager unqualifiedTypeListManager;

	@Resource
    private CheckLogManager checkLogManager;

	@Resource
    private MaterialBomManager materialBomManager;

	@Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

	@Resource
    private EmployeeManager employeeManager;

	@Resource
    private MaterialProcessService materialProcessService;

	@Resource
    private MaterialRepertoryIncomingCheckService materialRepertoryIncomingCheckService;

	@Resource
    private ProductionIssueDetailManager productionIssueDetailManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Override
    protected BaseManager<ProductionLogEntity> getManager() {
        return productionLogManager;
    }

    @Transactional
    public void saveCheck(ProductionLogInfo productionLogInfo,Integer checkNumber){
        ProductionLogEntity productionLogEntity = ExchangeUtils.exchangeObject(productionLogInfo, ProductionLogEntity.class);
        ProductionLogDetailEntity plde = new ProductionLogDetailEntity();
        plde.setId(productionLogInfo.getId());
        plde.setCheckStatus(CHECK_ALREADY_STATUS);

        productionLogDetailManager.updateByPrimaryKeySelective(plde);

        ProductionLogDetailEntity pldEntity = productionLogDetailManager.getEntityById(productionLogInfo.getId());

        /*productionLogEntity.setQualifiedPercent(productionLogInfo.getCheckQualifiedPercent()+"%");
        productionLogEntity.setQualifiedPercent(productionLogInfo.getQualifiedPercent());*/
        productionLogEntity.setCheckStatus(CHECK_ALREADY_STATUS);
        productionLogManager.updateByPrimaryKeySelective(productionLogEntity);
        ProductionCheckLogEntity productionCheckLogEntity = new ProductionCheckLogEntity();
        Integer materialId = pldEntity.getMaterialId();

        //检测时间
        productionCheckLogEntity.setCheckDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
        //检测人id
        Integer employeeId = Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId());
        EmployeeEntity ee = employeeManager.getEntityById(employeeId);
        EmployeeEntity ee1 = employeeManager.getEntityById(pldEntity.getOperatId());
        productionCheckLogEntity.setProductPersonId(pldEntity.getOperatId());
        productionCheckLogEntity.setProductPersonName(ee1.getName());
        productionCheckLogEntity.setCheckPersonId(employeeId);
        productionCheckLogEntity.setCheckPersonName(ee.getName());
        productionCheckLogEntity.setProductDate(pldEntity.getProduceDate());
        productionCheckLogEntity.setMaterialCode(pldEntity.getMaterialCode());
        productionCheckLogEntity.setProcessId(pldEntity.getProcessId());
        productionCheckLogEntity.setMaterialId(materialId);
        productionCheckLogEntity.setMaterialName(productionLogInfo.getMaterialName());
        productionCheckLogEntity.setMaterialNum(productionLogInfo.getCompleteQty());
        productionCheckLogEntity.setQualifiedNum(productionLogInfo.getQualifiedNum());
        productionCheckLogEntity.setUnQualifiedNum(productionLogInfo.getUnQualifiedNum());
        productionCheckLogEntity.setCheckPercent(productionLogInfo.getCheckPercent());
        productionCheckLogEntity.setCheckNum(productionLogInfo.getCheckNum());
        productionCheckLogEntity.setUnQualifiedType(productionLogInfo.getUnQualifiedType());
        productionCheckLogEntity.setUnQualifiedRemark(productionLogInfo.getUnQualifiedRemark());
        productionCheckLogEntity.setFileUrl(productionLogInfo.getFileUrl());
        productionCheckLogEntity.setCheckResult(productionLogInfo.getCheckResult());
        productionCheckLogEntity.setQualifiedPercent(productionLogInfo.getCheckQualifiedPercent()+"%");
        productionCheckLogEntity.setProductionNumber(productionLogInfo.getProductionNumber());
        productionCheckLogEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        //生产记录id
        productionCheckLogEntity.setProductionLogId(productionLogInfo.getId());
        productionCheckLogEntity.setProductionBatch(productionLogInfo.getProductBatch());
        productionCheckLogEntity.setProductLogNumber(checkNumber);
        productionCheckLogEntity = productionCheckLogManager.insertBy(productionCheckLogEntity);
        String checkNumber1 = frontCompWithZore(checkNumber,8);
        productionCheckLogEntity.setCheckNumber(checkNumber1);
        productionCheckLogEntity.setId(productionCheckLogEntity.getId());
        productionCheckLogManager.updateByPrimaryKeySelective(productionCheckLogEntity);

        //最后一道制程检测完成时才可以到待入库状态
        MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
        materialProcessInfo.setMaterialId(materialId);
        materialProcessInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        materialProcessInfo.setStatus(STATUS_DEFAULT);
        List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
        if(productionLogInfo.getProcessId() != null){
            if(list.size() != 0){
                MaterialProcessInfo mpInfo = list.get(list.size() - 1);
                if(productionLogInfo.getProcessId().equals(mpInfo.getId())){
                    MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
                    MaterialBomEntity mbe = materialBomManager.getEntityById(productionLogInfo.getMaterialId());
                    String numberValue = null;
                    numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                    mrice.setNumber(numberValue);
                    mrice.setOriginalMaterial(mbe.getOriginalMaterial());
                    mrice.setSource(mbe.getSource());
                    mrice.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                    mrice.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    mrice.setDrawingCode(mbe.getDrawingCode());
                    mrice.setMaterialSource(PRODUCTION_MATERIAL);
                    mrice.setInboundStutas(STAY_INBOUND_STATUS);
                    mrice.setCheckDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                    mrice.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                    mrice.setDeliveryDate(productionLogInfo.getProductTime());
                    mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    mrice.setInventoryQty(productionLogInfo.getCompleteQty());
                    mrice.setMaterialBatch(productionLogInfo.getProductBatch());
                    mrice.setMaterialCode(productionLogInfo.getMaterialCode());
                    mrice.setCheckNumber(checkNumber1);
                    Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
                    mrice.setIncomingCheckNumber(incomingCheckNumber);
                    mrice.setMaterialId(productionLogInfo.getMaterialId());
                    mrice.setFileUrl(productionLogInfo.getFileUrl());
                    mrice.setMaterialName(productionLogInfo.getMaterialName());
                    mrice.setQualifiedQty(productionLogInfo.getQualifiedNum());
                    mrice.setUnqualifiedQty(productionLogInfo.getUnQualifiedNum());
                    mrice.setPurchaseReason(productionLogInfo.getProductionNumber());
                    MaterialRepertoryIncomingCheckEntity materialRepertoryIncomingCheckEntity = materialRepertoryIncomingCheckManager.insertBy(mrice);

                    MaterialRepertoryEntity mre1 = new MaterialRepertoryEntity();
                    mre1.setMaterialId(productionLogInfo.getMaterialId());
                    List<MaterialRepertoryEntity> mre1List = materialRepertoryManager.getListBy(mre1);
                    if(mre1List.size() != 0){
                        mre1 = mre1List.get(0);
                        mre1.setStayInboundQty(mre1.getStayInboundQty()+productionLogInfo.getQualifiedNum());
                        mre1.setStayCheckQty(mre1.getStayCheckQty()-productionLogInfo.getCompleteQty());
                        mre1.setUnqualifiedQty(mre1.getUnqualifiedQty()+productionLogInfo.getUnQualifiedNum());
                        materialRepertoryManager.updateByPrimaryKeySelective(mre1);
                    }
                }
            }
        }
        /*List<String> unqualifiedTypeFormList = productionLogInfo.getUnqualifiedTypeFormList();
        if (unqualifiedTypeFormList!=null){
            for (int i=0;i<unqualifiedTypeFormList.size();i++){
                UnqualifiedTypeListEntity utle = new UnqualifiedTypeListEntity();
                utle.setCheckId(productionCheckLogEntity.getId());
                utle.setCheckSource(PRODUCE_CHECK);
                utle.setTypeName(unqualifiedTypeFormList.get(i));
                utle.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                unqualifiedTypeListManager.insertBy(utle);
            }
        }*/

        if (productionLogInfo.getTemp().size()!=0){
            List<String> temp = productionLogInfo.getTemp();
            for (int i=0;i<temp.size();i++){
                CheckLogEntity cle = new CheckLogEntity();
                cle.setCheckFlag(1);
                cle.setCheckId(productionCheckLogEntity.getId());
                cle.setCheckResult(temp.get(i));
                cle.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                checkLogManager.insertBy(cle);
            }
        }

        /*//获取检测记录list
        List<Map<String,String>> resultList = productionLogInfo.getResultList();
        if (resultList!=null){
            for (int i=0;i<resultList.size();i++){
                Map<String,String> map = resultList.get(i);
                CheckLogEntity cle = new CheckLogEntity();
                cle.setAcceptanceDetailId(Integer.parseInt(map.get("id")));
                cle.setCheckFlag(PRODUCE_CHECK);
                cle.setCheckId(productionCheckLogEntity.getId());
                cle.setCheckResult(map.get("checkResult"));
                cle.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                checkLogManager.insertBy(cle);
            }
        }*/
    }

    @Transactional
    public void completeProduce(ProductionLogInfo productionLogInfo){
        Integer materialId = productionLogInfo.getMaterialId();
        ProductionLogDetailEntity plde = new ProductionLogDetailEntity();
        MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
        materialRelationInfo.setMaterialParentId(materialId);
        materialRelationInfo.setStatus(STATUS_DEFAULT);


        ProductionLogEntity productionLogEntity = new ProductionLogEntity();
        productionLogEntity.setProductionNumber(productionLogInfo.getProductionNumber());
        List<ProductionLogEntity> pliList = productionLogManager.getListBy(productionLogEntity);
        ProductionLogEntity ple;
        if(pliList.size() == 0){
            ProductionLogEntity productionLogEntity1 = ExchangeUtils.exchangeObject(productionLogInfo, ProductionLogEntity.class);
            productionLogEntity1.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            productionLogEntity1.setProductTime(getDateFormat("yyyy-MM-dd"));
//            productionLogEntity1.setCompletedQty(productionLogInfo.getProcessProductQty());
            ple = productionLogManager.insertBy(productionLogEntity1);
        }

        //最后一道制程完成时才可以结束生产单
        MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
        materialProcessInfo.setMaterialId(materialId);
        materialProcessInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        materialProcessInfo.setStatus(STATUS_DEFAULT);
        List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
        if(list.size() != 0){
            MaterialProcessInfo mpInfo = list.get(list.size() - 1);
            if(productionLogInfo.getProcessId().equals(mpInfo.getId())){
                Float completedQty = productionLogDetailManager.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionLogInfo.getProductionNumber(),mpInfo.getId(),null);
                if(completedQty == null){
                    completedQty = 0f;
                }
                if(productionLogInfo.getMaterialNum() <= productionLogInfo.getProcessProductQty()+completedQty){
                    ProductionPlanEntity ppe = new ProductionPlanEntity();
                    ppe.setId(productionLogInfo.getProductionPlanId());
                    ppe.setProductionStatus(ALREADY_PRODUCTION);
                    productionPlanManager.updateByPrimaryKeySelective(ppe);
                }
                if(PROCESS_NO_CHECK.equals(mpInfo.getCheckStatus())){
                    MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
                    MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                    mrice.setMaterialId(materialId);
                    mrice.setInventoryQty(productionLogInfo.getProcessProductQty());
                    ProductionPlanEntity ppe = new ProductionPlanEntity();
                    ppe.setProductionNumber(productionLogInfo.getProductionNumber());
                    List<ProductionPlanEntity> ppeList = productionPlanManager.getListBy(ppe);
                    if(ppeList.size() != 0){
                        mrice.setMaterialBatch(ppeList.get(0).getProductionNumber());
                    }
                    mrice.setMaterialCode(mbe.getMaterialCode());
                    mrice.setMaterialName(mbe.getMaterialName());
                    mrice.setStatus(STATUS_DEFAULT);
                    mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    mrice.setUnqualifiedQty(0f);
                    mrice.setQualifiedQty(productionLogInfo.getProcessProductQty());
                    mrice.setPurchaseReason(productionLogInfo.getProductionNumber());
                    mrice.setInboundStutas(STAY_INBOUND_STATUS);
                    mrice.setMaterialSource(PRODUCTION_MATERIAL);
                    mrice.setQualifiedPercent("100%");
                    mrice.setUnqualifiedFlag(QUALIFIED_FLAG);
                    mrice.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                    Integer incomingCheckNumber = materialRepertoryIncomingCheckService.getNextNumber();
                    mrice.setIncomingCheckNumber(incomingCheckNumber);
                    mrice.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                    String numberValue = null;
                    numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                    mrice.setNumber(numberValue);
                    mrice.setOriginalMaterial(mbe.getOriginalMaterial());
                    mrice.setDrawingCode(mbe.getDrawingCode());
                    mrice.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                    mrice.setSource(mbe.getSource());
                    materialRepertoryIncomingCheckManager.insertBy(mrice);

                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(materialId);
                    mre.setStatus(STATUS_DEFAULT);
                    List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                    if(mreList.size() != 0){
                        mre.setId(mreList.get(0).getId());
                        mre.setStayProductionQty(mreList.get(0).getStayProductionQty()-productionLogInfo.getProcessProductQty());
                        mre.setStayInboundQty(mreList.get(0).getStayInboundQty()+productionLogInfo.getProcessProductQty());
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                    }
                }else{
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(materialId);
                    mre.setStatus(STATUS_DEFAULT);
                    List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                    if(mreList.size() != 0){
                        mre.setId(mreList.get(0).getId());
                        mre.setStayProductionQty(mreList.get(0).getStayProductionQty()-productionLogInfo.getProcessProductQty());
                        mre.setStayCheckQty(mreList.get(0).getStayCheckQty()+productionLogInfo.getProcessProductQty());
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                    }
                }
                productionLogEntity.setCompletedQty(pliList.get(0).getCompletedQty()+productionLogInfo.getProcessProductQty());
                productionLogEntity.setId(pliList.get(0).getId());
                ple = productionLogManager.updateByPrimaryKeySelective(productionLogEntity);
            }
            MaterialProcessInfo mpInfo1 = list.get(0);
            if(productionLogInfo.getProcessId().equals(mpInfo1.getId())){
                //根据父id查询中间表中的子id,更新投料数量
                List<MaterialRelationInfo> mriList = materialRelationService.getListBy(materialRelationInfo);
                if (mriList.size()!=0) {
                    for (MaterialRelationInfo mri : mriList) {
                        Float produceQty = 0f;
                        produceQty = productionLogInfo.getProcessProductQty()*mri.getQuantity();
                        ProductionIssueEntity productionIssueEntity = new ProductionIssueEntity();
                        productionIssueEntity.setMaterialId(mri.getMaterialChildId());
                        productionIssueEntity.setProductionNumber(productionLogInfo.getProductionNumber());
                        List<ProductionIssueEntity> pieList = productionIssueManager.getListBy(productionIssueEntity);
                        if(pieList.size() != 0){
                            ProductionIssueEntity pie = new ProductionIssueEntity();
                            pie.setId(pieList.get(0).getId());
//                          pie.setIssueQty(pieList.get(0).getIssueQty()-(productionLogInfo.getProcessProductQty()*mri.getQuantity()));
                            pie.setUsedQty(pieList.get(0).getUsedQty()+produceQty);
                            productionIssueManager.updateByPrimaryKeySelective(pie);
                        }

                        ProductionIssueDetailEntity pide = new ProductionIssueDetailEntity();
                        pide.setMaterialId(mri.getMaterialChildId());
                        pide.setProductionNumber(productionLogInfo.getProductionNumber());
                        List<ProductionIssueDetailEntity> pideList = productionIssueDetailManager.getListBy(pide);
                        if(pideList.size() != 0){
                            for(ProductionIssueDetailEntity productionIssueDetailEntity : pideList){
                                Float useQty = productionIssueDetailEntity.getIssueQty()-productionIssueDetailEntity.getUsedQty();
                                if(produceQty == 0){
                                    continue;
                                }else{
                                    if(useQty > produceQty){
                                        productionIssueDetailEntity.setUsedQty(produceQty + productionIssueDetailEntity.getUsedQty());
                                        productionIssueDetailManager.updateByPrimaryKeySelective(productionIssueDetailEntity);
                                        break;
                                    } else if (useQty <= produceQty){
                                        produceQty = produceQty - useQty;
                                        productionIssueDetailEntity.setUsedQty(productionIssueDetailEntity.getIssueQty());
                                        productionIssueDetailManager.updateByPrimaryKeySelective(productionIssueDetailEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        plde.setCheckFlag(productionLogInfo.getCheckFlag());
        plde.setProductionNumber(productionLogInfo.getProductionNumber());
        plde.setProcessId(productionLogInfo.getProcessId());
        plde.setProcessName(productionLogInfo.getProcessName());
        plde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        plde.setProductStatus(STAY_PRODUTION);
        plde.setOperatId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        plde.setMaterialId(materialId);
        List<ProductionLogDetailEntity> pldeList = productionLogDetailManager.getListBy(plde);
        if(pldeList.size() != 0){
            ProductionLogDetailEntity productionLogDetailEntity = pldeList.get(0);
            productionLogDetailEntity.setCompleteQty(productionLogInfo.getProcessProductQty());
            productionLogDetailEntity.setProductStatus(ALREADY_PRODUCTION);
            productionLogDetailEntity.setProduceDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
            productionLogDetailManager.updateByPrimaryKeySelective(productionLogDetailEntity);
        }
    }

    public void startProduct(ProductionLogInfo productionLogInfo){
        Integer materialId = productionLogInfo.getMaterialId();
        MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
        materialProcessInfo.setMaterialId(materialId);
        materialProcessInfo.setStatus(STATUS_DEFAULT);
        materialProcessInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
        ProductionLogDetailEntity plde = new ProductionLogDetailEntity();
        if(list.size() != 0){
            MaterialProcessInfo mpi = new MaterialProcessInfo();
            if(list.size()>1){
                mpi = list.get(list.size()-1);
            } else {
                mpi = list.get(0);
            }
            Integer processId = mpi.getId();
            if(productionLogInfo.getProcessId().equals(processId)){
                plde.setProcessCompleteFlag(STATUS_DEFAULT);
            }
        }
        String productionNumber = productionLogInfo.getProductionNumber();
        Integer productionPlanId = productionLogInfo.getProductionPlanId();
        Integer processId = productionLogInfo.getProcessId();
        String processName = productionLogInfo.getProcessName();

        ProductionLogEntity ple = new ProductionLogEntity();
        ple.setProductionNumber(productionNumber);
        ple.setMaterialId(materialId);
        ple.setProductionPlanId(productionPlanId);

        List<ProductionLogEntity> pleList = productionLogManager.getListBy(ple);

        ProductionPlanEntity ppe = productionPlanManager.getEntityById(productionPlanId);
        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
        if(pleList.size() == 0){
            ple.setMaterialNum(ppe.getMaterialNum());
            ple.setLatestProductDate(ppe.getLatestProductDate());
            ple.setMaterialCode(mbe.getMaterialCode());
            ple.setMaterialName(mbe.getMaterialName());
            ple.setMaterialUnit(mbe.getMaterialUnit());
            ple.setOriginalMaterial(mbe.getOriginalMaterial());
            ple.setPlanProductDate(ppe.getPlanProductDate());
            ple.setProductBatch(ppe.getProductBatch());
            ple.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            ple = productionLogManager.insertBy(ple);
        }else{
            ple = pleList.get(0);
        }


        plde.setOperatId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        plde.setProcessId(processId);
        plde.setProcessName(processName);
        plde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        plde.setProductionNumber(productionNumber);
        plde.setMaterialId(materialId);
        plde.setProductStatus(STAY_PRODUTION);
        List<ProductionLogDetailEntity> pldeList = productionLogDetailManager.getListBy(plde);
        if(pldeList.size() == 0){
            plde.setCheckFlag(productionLogInfo.getCheckFlag());
            plde.setProductBatch(ppe.getProductBatch());
            plde.setMaterialCode(mbe.getMaterialCode());
            plde.setLogId(ple.getId());
            plde.setStartDate(getDateFormat("yyyy-MM-dd HH:mm:ss"));
            productionLogDetailManager.insertBy(plde);
        }
    }

    public void saveProductQty(ProductionLogInfo productionLogInfo){
        ProductionLogDetailEntity plde = new ProductionLogDetailEntity();
        plde.setProductionNumber(productionLogInfo.getProductionNumber());
        plde.setProcessId(productionLogInfo.getProcessId());
        plde.setProcessName(productionLogInfo.getProcessName());
        plde.setOperatId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        plde.setMaterialId(productionLogInfo.getMaterialId());
        List<ProductionLogDetailEntity> pldeList = productionLogDetailManager.getListBy(plde);
        if(pldeList.size() != 0){
            ProductionLogDetailEntity productionLogDetailEntity = pldeList.get(0);
            productionLogDetailEntity.setCompleteQty(productionLogInfo.getProcessProductQty());
            productionLogDetailEntity.setProductStatus(ALREADY_PRODUCTION);
            productionLogDetailManager.updateByPrimaryKeySelective(productionLogDetailEntity);
        }
    }
}
