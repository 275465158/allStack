package com.ecms.service;

import com.ecms.bean.BaseCompanyAndStatusInfo;
import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialBomParamNameInfo;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.MaterialPartInfo;
import com.ecms.bean.MaterialProcessingInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.BomTaskDetailEntity;
import com.ecms.dal.entity.DeliveryDetailEntity;
import com.ecms.dal.entity.IncomingUnqualifiedEntity;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.MaterialBomParamNameEntity;
import com.ecms.dal.entity.MaterialCheckDetailEntity;
import com.ecms.dal.entity.MaterialCheckEntity;
import com.ecms.dal.entity.MaterialDrawingEntity;
import com.ecms.dal.entity.MaterialProcessEntity;
import com.ecms.dal.entity.MaterialRelationEntity;
import com.ecms.dal.entity.MaterialRepertoryDeliveryEntity;
import com.ecms.dal.entity.MaterialRepertoryDetailEntity;
import com.ecms.dal.entity.MaterialRepertoryEntity;
import com.ecms.dal.entity.MaterialRepertoryInboundlogEntity;
import com.ecms.dal.entity.MaterialRepertoryIncomingCheckEntity;
import com.ecms.dal.entity.MaterialRepertoryOutboundDetailEntity;
import com.ecms.dal.entity.MaterialRepertoryOutboundEntity;
import com.ecms.dal.entity.MaterialRepertoryOutboundlogEntity;
import com.ecms.dal.entity.MaterialSendDetailEntity;
import com.ecms.dal.entity.ProcessEntity;
import com.ecms.dal.entity.ProduceMaterialReturnLogEntity;
import com.ecms.dal.entity.ProductAcceptanceEntity;
import com.ecms.dal.entity.ProductDrawingEntity;
import com.ecms.dal.entity.ProductTypeEntity;
import com.ecms.dal.entity.ProductionCheckLogEntity;
import com.ecms.dal.entity.ProductionIssueDetailEntity;
import com.ecms.dal.entity.ProductionIssueEntity;
import com.ecms.dal.entity.ProductionLogDetailEntity;
import com.ecms.dal.entity.ProductionLogEntity;
import com.ecms.dal.entity.ProductionPlanEntity;
import com.ecms.dal.entity.PurchaseOrdersListEntity;
import com.ecms.dal.entity.PurchaseOrdersStayEntity;
import com.ecms.dal.entity.RepertoryEntity;
import com.ecms.dal.entity.ReturnedPurchaseEntity;
import com.ecms.dal.entity.SellerOrderProductEntity;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.dal.entity.SupplierMaterialEntity;
import com.ecms.excel.MaterialBomAuditingPassTemplate;
import com.ecms.excel.MaterialTemplate;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.BomTaskDetailManager;
import com.ecms.manager.DeliveryDetailManager;
import com.ecms.manager.IncomingUnqualifiedManager;
import com.ecms.manager.MaterialBomManager;
import com.ecms.manager.MaterialBomParamNameManager;
import com.ecms.manager.MaterialCheckDetailManager;
import com.ecms.manager.MaterialCheckManager;
import com.ecms.manager.MaterialDrawingManager;
import com.ecms.manager.MaterialProcessManager;
import com.ecms.manager.MaterialRelationManager;
import com.ecms.manager.MaterialRepertoryDeliveryManager;
import com.ecms.manager.MaterialRepertoryDetailManager;
import com.ecms.manager.MaterialRepertoryInboundlogManager;
import com.ecms.manager.MaterialRepertoryIncomingCheckManager;
import com.ecms.manager.MaterialRepertoryManager;
import com.ecms.manager.MaterialRepertoryOutboundDetailManager;
import com.ecms.manager.MaterialRepertoryOutboundManager;
import com.ecms.manager.MaterialRepertoryOutboundlogManager;
import com.ecms.manager.MaterialSendDetailManager;
import com.ecms.manager.ProcessManager;
import com.ecms.manager.ProduceMaterialReturnLogManager;
import com.ecms.manager.ProductAcceptanceManager;
import com.ecms.manager.ProductDrawingManager;
import com.ecms.manager.ProductTypeManager;
import com.ecms.manager.ProductionCheckLogManager;
import com.ecms.manager.ProductionIssueDetailManager;
import com.ecms.manager.ProductionIssueManager;
import com.ecms.manager.ProductionLogDetailManager;
import com.ecms.manager.ProductionLogManager;
import com.ecms.manager.ProductionPlanManager;
import com.ecms.manager.PurchaseOrdersListManager;
import com.ecms.manager.PurchaseOrdersStayManager;
import com.ecms.manager.RepertoryManager;
import com.ecms.manager.ReturnedPurchaseManager;
import com.ecms.manager.SellerOrderProductManager;
import com.ecms.manager.SupplierManager;
import com.ecms.manager.SupplierMaterialManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ecms.common.PinYin.getPinYinHeadChar;
import static com.ecms.common.PinYin.isContainChinese;
import static com.ecms.common.PinYin.wordToUpperCase;

@Service
public class MaterialBomService extends BaseService<MaterialBomEntity, MaterialBomInfo> {

    private Logger logger = getLogger();

    protected String workflowKey = "MaterialBomReview";
    @Resource
    private MaterialBomManager manager;
    @Resource
    private MaterialParameterService paramService;
    @Resource
    private MaterialProcessingService processingService;
    @Resource
    private MaterialPartService partService;
    @Resource
    private MaterialRepertoryDetailManager materialRepertoryDetailManager;
    @Resource
    private MaterialRepertoryManager materialRepertoryManager;
    @Resource
    private MaterialBomParamNameManager materialBomParamNameManager;
    @Resource
    private MaterialRelationManager materialRelationManager;
    @Resource
    private MaterialProcessManager materialProcessManager;
    @Resource
    private ProcessManager processManager;
    @Resource
    private MaterialCheckManager materialCheckManager;
    @Resource
    private MaterialDrawingManager materialDrawingManager;
    @Resource
    private ProductDrawingManager productDrawingManager;
    @Resource
    private ProductTypeManager productTypeManager;
    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;
    @Resource
    private BomTaskDetailManager bomTaskDetailManager;
    @Resource
    private DeliveryDetailManager deliveryDetailManager;
    @Resource
    private IncomingUnqualifiedManager incomingUnqualifiedManager;
    @Resource
    private SupplierMaterialManager supplierMaterialManager;
    @Resource
    private SellerOrderProductManager sellerOrderProductManager;
    @Resource
    private ReturnedPurchaseManager returnedPurchaseManager;
    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;
    @Resource
    private PurchaseOrdersListManager purchaseOrdersListManager;
    @Resource
    private ProductionPlanManager productionPlanManager;
    @Resource
    private ProductionLogDetailManager productionLogDetailManager;
    @Resource
    private ProductionLogManager productionLogManager;
    @Resource
    private ProductionCheckLogManager productionCheckLogManager;
    @Resource
    private ProduceMaterialReturnLogManager produceMaterialReturnLogManager;
    @Resource
    private ProductionIssueDetailManager productionIssueDetailManager;
    @Resource
    private ProductionIssueManager productionIssueManager;
    @Resource
    private MaterialSendDetailManager materialSendDetailManager;

    @Resource
    private MaterialCheckDetailManager materialCheckDetailManager;
    @Resource
    private MaterialRepertoryDeliveryManager materialRepertoryDeliveryManager;
    @Resource
    private MaterialRepertoryInboundlogManager materialRepertoryInboundlogManager;
    @Resource
    private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;
    @Resource
    private MaterialRepertoryOutboundManager materialRepertoryOutboundManager;
    @Resource
    private MaterialRepertoryOutboundDetailManager materialRepertoryOutboundDetailManager;
    @Resource
    private MaterialRepertoryOutboundlogManager materialRepertoryOutboundlogManager;
    @Resource
    private SupplierManager supplierManager;
    @Resource
    private ActivitiWorkflowService tasksService;
    @Resource
    private ActivitiModelService activitiModelService;
    @Resource
    private ProductAcceptanceManager productAcceptanceManager;
    @Resource
    private RepertoryManager repertoryManager;

    @Override
    protected BaseManager<MaterialBomEntity> getManager() {
        return manager;
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_material_";
    }

    public Boolean workflowIsSuspended() {
        Map<String, Object> workflowSuspended = activitiModelService.queryModelByWorkflowKey(WORKFLOW_MATERIAL_BOM);
        Boolean status = null == workflowSuspended.get("isSuspended") || (Boolean) workflowSuspended.get("isSuspended");
        return status;
    }

    /**
     * @Author:pratice
     * @date 2019/1/3
     * @Description: 询价系统重写
     */
    public Pager<MaterialBomInfo> getModelListByLikeForQuery(int pageNum, int pageSize, MaterialBomInfo info, boolean ignoreStatus) {
        Condition condition = new Condition(eClass);
        Example.Criteria criteria = condition.createCriteria();
        if (info instanceof BaseCompanyAndStatusInfo) {
            if (!ignoreStatus) {
                criteria.andEqualTo("status", STATUS_DEFAULT);
            }
            criteria.andEqualTo("companyId", LoginFilter.getCurrentUser().getParentId());
        }
        try {
            List<Field> fields = new ArrayList<>();
            getAllField(fields, info.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(info);
                if (value != null) {
                    if (value instanceof String) {
                        if (StringUtils.isNotBlank((String) value)) {
                            criteria.andLike(field.getName(), "%" + value + "%");
                        }
                    } else {
                        criteria.andEqualTo(field.getName(), value);
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonBusinessException(e.getMessage(), e);
        }
        long total = getManager().getTotal(condition);
        List<MaterialBomEntity> list = getManager().getByCondition(pageNum, pageSize, condition);
        List<MaterialBomInfo> modelList = ExchangeUtils.exchangeList(list, MaterialBomInfo.class);
        setCustomProperties(modelList);
        Pager<MaterialBomInfo> pager = new Pager<MaterialBomInfo>(pageNum, pageSize, total, modelList);
        return pager;
    }

    public List<MaterialBomInfo> getSupplierBomList(int pageNum, int pageSize, String materialCode, String materialName, Integer supplierId, Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String originalMaterial,String drawingCode,String source) {
        return manager.getSupplierBomList(pageNum, pageSize, materialCode, materialName, supplierId, companyId, factoryMaterialCode, type, materialBomParamValueStr, originalMaterial, drawingCode, source);
    }

    public List<MaterialBomInfo> getSupplierBomList1(String materialCode, String materialName, Integer supplierId, Integer companyId,String factoryMaterialCode,String type,String materialBomParamValueStr,String originalMaterial,String drawingCode,String source) {
        return manager.getSupplierBomList1(materialCode, materialName, supplierId, companyId, factoryMaterialCode, type, materialBomParamValueStr, originalMaterial, drawingCode, source);
    }

    /**
     * @param materialId
     * @return
     * @Description 获取当前id的部件列表
     */
    public List<MaterialPartInfo> listMaterialPart(Integer materialId) {
        MaterialPartInfo searchPart = new MaterialPartInfo();
        searchPart.setParentId(materialId);
        List<MaterialPartInfo> parts = partService.getListBy(searchPart);
        //嵌入相关实体
        parts.stream().forEach(part -> {
            part.setParentBom(this.getModel(part.getParentId()));
            MaterialBomModal modal = this.getMaterialBomModal(part.getMaterialBomId());
            List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(modal.getId());
            modal.setMaterialParameters(listParams);
            Map<String, String> params = new HashMap<String, String>();
            listParams.stream().forEach(param -> {
                params.put(param.getParameterName(), param.getParameterValue());
            });
            modal.setMaterialParameter(params);
            List<MaterialProcessingInfo> listProcessing = processingService.getListByMaterialId(modal.getId());
            modal.setMaterialProcessing(listProcessing);
            part.setMaterialBom(modal);
        });
        return parts;
    }

    /**
     * @param materialBomId
     * @return
     * @Description 获取当前id相关数据实体
     */
    public MaterialBomModal getMaterialBomModal(Integer materialBomId) {
        MaterialBomModal modal = ExchangeUtils.exchangeObject(getModel(materialBomId), MaterialBomModal.class);
        MaterialPartInfo searchPart = new MaterialPartInfo();
        searchPart.setParentId(modal.getId());
        //搜索嵌入部件
        List<MaterialPartInfo> listParts = partService.getListBy(searchPart);
        listParts.stream().forEach(part -> {
            part.setMaterialBom(getMaterialBomModal(part.getMaterialBomId()));
        });
        modal.setMaterialParts(listParts);
        //搜索嵌入属性
        List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(materialBomId);
        modal.setMaterialParameters(listParams);
        Map<String, String> params = new HashMap<String, String>();
        listParams.stream().forEach(param -> {
            params.put(param.getParameterName(), param.getParameterValue());
        });
        modal.setMaterialParameter(params);
        //搜索嵌入制程列表
        List<MaterialProcessingInfo> listProcessing = processingService.getListByMaterialId(materialBomId);
        modal.setMaterialProcessing(listProcessing);
        return modal;
    }

    public List<MaterialBomInfo> getMaterielNameByParentId(Integer parentId) {
        Example.Criteria criteria = getDefaultCriteria();
        criteria.andEqualTo("id", parentId);
        return getByCondition();
    }

    public Map<String, Object> getTotalNumList(Integer id) {
        return manager.getTotalNumList(id);
    }

    public List<MaterialBomInfo> getAddMaterialBomList(int pageNum, int pageSize, String materialCode, String materialName, String author, Integer repertoryId, Integer companyId, String factoryMaterialCode, String type, String materialBomParamValueStr, String drawingCode, String source) {
        return manager.getAddMaterialBomList(pageNum, pageSize, materialCode, materialName, author, repertoryId, companyId, factoryMaterialCode, type, materialBomParamValueStr, drawingCode, source);
    }

    public List<MaterialBomInfo> getAddMaterialBomList1(String materialCode, String materialName, String author, Integer repertoryId, Integer companyId, String factoryMaterialCode, String type, String materialBomParamValueStr, String drawingCode, String source) {
        return manager.getAddMaterialBomList1(materialCode, materialName, author, repertoryId, companyId, factoryMaterialCode, type, materialBomParamValueStr, drawingCode, source);
    }

    @Transactional
    public void addRepertoryMaterial(MaterialBomInfo materialBomInfo) {
        List<Map<String, Object>> materialList = materialBomInfo.getMaterialList();
        if (materialList.size() != 0) {
            for (Map<String, Object> materialMap : materialList) {
                MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
                MaterialBomEntity mbe = manager.getEntityById(Integer.parseInt(materialMap.get("id").toString()));
                String numberValue = null;
                numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                mrde.setNumber(numberValue);
                mrde.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                mrde.setSource(mbe.getSource());
                mrde.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                mrde.setDrawingCode(mbe.getDrawingCode());
                mrde.setOriginalMaterial(mbe.getOriginalMaterial());
                mrde.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrde.setMaterialId(Integer.parseInt(materialMap.get("id").toString()));
                mrde.setMaterialName(materialMap.get("materialName").toString());
                mrde.setMaterialCode(materialMap.get("materialCode").toString());
                mrde.setRepertoryId(materialBomInfo.getRepertoryId());
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mrde.setInboundDate(sdf.format(date));
                materialRepertoryDetailManager.insertBy(mrde);

                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(Integer.parseInt(materialMap.get("id").toString()));
                Long materialSize = materialRepertoryManager.getTotal(mre);
                if (materialSize == 0) {
                    mre.setMaterialName(materialMap.get("materialName").toString());
                    mre.setOriginalMaterial(materialMap.get("originalMaterial").toString());
                    mre.setMaterialBomParamValueStr(getParamValue(Integer.parseInt(materialMap.get("id").toString())));
                    mre.setMaterialId(Integer.parseInt(materialMap.get("id").toString()));
                    mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    //materialRepertoryManager.insertBy(mre);
                }
            }
        }
    }

    @Transactional
    public void materialMerge(MaterialBomInfo materialBomInfo) {
        List<Integer> materialList = materialBomInfo.getMaterialIds();
        Integer materialMergeId = materialBomInfo.getId();
        MaterialBomEntity mbe = manager.getEntityById(materialMergeId);
        String numberValue = null;
        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
        if (materialList.size() != 0) {
            for (Integer materialId : materialList) {    //取list集合里的那一条Map集合
                //更新物料父子关系表,把所有子级id是materialId的全部修改为materialMergeId
                materialRelationManager.updateByMaterialChildIdAndCompanyId(materialId,LoginFilter.getCurrentUser().getCompanyId(),materialMergeId);
                BomTaskDetailEntity btde = new BomTaskDetailEntity();
                btde.setStatus(STATUS_DEFAULT);
                btde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                btde.setMaterialId(materialMergeId);
                List<BomTaskDetailEntity> btdeList = bomTaskDetailManager.getListBy(btde);
                if (btdeList.size() != 0) {
                    btde = btdeList.get(0);
                }
                BomTaskDetailEntity btde2 = new BomTaskDetailEntity();
                btde2.setStatus(STATUS_DEFAULT);
                btde2.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                btde2.setMaterialId(materialId);
                List<BomTaskDetailEntity> btdeList2 = bomTaskDetailManager.getListBy(btde2);
                if (btdeList.size() != 0) {
                    for (BomTaskDetailEntity btdEntity : btdeList2) {
                        btdEntity.setMaterialId(btde.getMaterialId());
                        btdEntity.setDraftName(btde.getDraftName());
                        bomTaskDetailManager.updateByPrimaryKeySelective(btdEntity);
                    }
                }

                DeliveryDetailEntity dde = new DeliveryDetailEntity();
                dde.setMaterielId(materialId);
                dde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                dde.setStatus(STATUS_DEFAULT);
                List<DeliveryDetailEntity> ddeList = deliveryDetailManager.getListBy(dde);
                if (ddeList.size() != 0) {
                    for (DeliveryDetailEntity ddEntity : ddeList) {
                        ddEntity.setMaterielId(materialMergeId);
                        deliveryDetailManager.updateByPrimaryKeySelective(ddEntity);
                    }
                }

                IncomingUnqualifiedEntity iufe = new IncomingUnqualifiedEntity();
                iufe.setMaterialId(materialId);
                iufe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                iufe.setStatus(STATUS_DEFAULT);
                List<IncomingUnqualifiedEntity> iufeList = incomingUnqualifiedManager.getListBy(iufe);
                if (iufeList.size() != 0) {
                    for (IncomingUnqualifiedEntity iufEntity : iufeList) {
                        iufEntity.setMaterialId(materialMergeId);
                        iufEntity.setMaterialName(mbe.getMaterialName());
                        incomingUnqualifiedManager.updateByPrimaryKeySelective(iufEntity);
                    }
                }

                SupplierMaterialEntity materialEntity = new SupplierMaterialEntity();
                materialEntity.setMaterialId(materialId);
                materialEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialEntity.setStatus(STATUS_DEFAULT);
                List<SupplierMaterialEntity> materialEntityList = supplierMaterialManager.getListBy(materialEntity);
                if (materialEntityList.size() != 0) {
                    for (SupplierMaterialEntity supplierMaterialEntity : materialEntityList) {
                        supplierMaterialEntity.setMaterialId(materialMergeId);
                        supplierMaterialEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        supplierMaterialEntity.setMaterialCode(mbe.getMaterialCode());
                        supplierMaterialEntity.setMaterialName(mbe.getMaterialName());
                        supplierMaterialEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        supplierMaterialEntity.setType(mbe.getType());
                        supplierMaterialEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        supplierMaterialManager.updateByPrimaryKeySelective(supplierMaterialEntity);
                    }
                }

                SellerOrderProductEntity productEntity = new SellerOrderProductEntity();
                productEntity.setProductId(materialId);
                productEntity.setStatus(STATUS_DEFAULT);
                List<SellerOrderProductEntity> productEntityList = sellerOrderProductManager.getListBy(productEntity);
                if (productEntityList.size() != 0) {
                    for (SellerOrderProductEntity sellerOrderProductEntity : productEntityList) {
                        sellerOrderProductEntity.setProductId(materialMergeId);
                        sellerOrderProductEntity.setProductCode(mbe.getMaterialCode());
                        sellerOrderProductEntity.setProductName(mbe.getMaterialName());
                        sellerOrderProductManager.updateByPrimaryKeySelective(sellerOrderProductEntity);
                    }
                }

                ReturnedPurchaseEntity purchaseEntity = new ReturnedPurchaseEntity();
                purchaseEntity.setMaterialId(materialId);
                purchaseEntity.setStatus(STATUS_DEFAULT);
                purchaseEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ReturnedPurchaseEntity> purchaseEntityList = returnedPurchaseManager.getListBy(purchaseEntity);
                if (purchaseEntityList.size() != 0) {
                    for (ReturnedPurchaseEntity returnedPurchaseEntity : purchaseEntityList) {
                        returnedPurchaseEntity.setMaterialId(materialMergeId);
                        returnedPurchaseEntity.setMaterialCode(mbe.getMaterialCode());
                        returnedPurchaseEntity.setMaterialName(mbe.getMaterialName());
                        returnedPurchaseManager.updateByPrimaryKeySelective(returnedPurchaseEntity);
                    }
                }

                PurchaseOrdersStayEntity stayEntity = new PurchaseOrdersStayEntity();
                stayEntity.setMaterialId(materialId);
                stayEntity.setStatus(STATUS_DEFAULT);
                stayEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<PurchaseOrdersStayEntity> stayEntityList = purchaseOrdersStayManager.getListBy(stayEntity);
                if (stayEntityList.size() != 0) {
                    for (PurchaseOrdersStayEntity purchaseOrdersStayEntity : stayEntityList) {
                        purchaseOrdersStayEntity.setMaterialId(materialMergeId);
                        purchaseOrdersStayEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        purchaseOrdersStayEntity.setMaterialCode(mbe.getMaterialCode());
                        purchaseOrdersStayEntity.setMaterialName(mbe.getMaterialName());
                        purchaseOrdersStayEntity.setMaterialType(mbe.getType());
                        purchaseOrdersStayEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        purchaseOrdersStayManager.updateByPrimaryKeySelective(purchaseOrdersStayEntity);
                    }
                }

                PurchaseOrdersListEntity listEntity = new PurchaseOrdersListEntity();
                listEntity.setMaterialId(materialId);
                listEntity.setStatus(STATUS_DEFAULT);
                listEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<PurchaseOrdersListEntity> listEntityList = purchaseOrdersListManager.getListBy(listEntity);
                if (listEntityList.size() != 0) {
                    for (PurchaseOrdersListEntity purchaseOrdersListEntity : listEntityList) {
                        purchaseOrdersListEntity.setMaterialId(materialMergeId);
                        purchaseOrdersListEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        purchaseOrdersListEntity.setMaterialCode(mbe.getMaterialCode());
                        purchaseOrdersListEntity.setMaterialName(mbe.getMaterialName());
                        purchaseOrdersListEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        purchaseOrdersListEntity.setType(mbe.getType());
                        purchaseOrdersListEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        purchaseOrdersListEntity.setMaterialSource(mbe.getSource());
                        purchaseOrdersListEntity.setDrawingCode(mbe.getDrawingCode());
                        purchaseOrdersListEntity.setNumber(mbe.getNumber());
                        purchaseOrdersListManager.updateByPrimaryKeySelective(purchaseOrdersListEntity);
                    }
                }

                ProductionPlanEntity planEntity = new ProductionPlanEntity();
                planEntity.setMaterialId(materialId);
                planEntity.setStatus(STATUS_DEFAULT);
                planEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionPlanEntity> planEntityList = productionPlanManager.getListBy(planEntity);
                if (planEntityList.size() != 0) {
                    for (ProductionPlanEntity productionPlanEntity : planEntityList) {
                        productionPlanEntity.setMaterialId(materialMergeId);
                        productionPlanEntity.setMaterialName(mbe.getMaterialName());
                        productionPlanEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        productionPlanEntity.setMaterialUnit(mbe.getMaterialUnit());
                        productionPlanEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        productionPlanEntity.setNumber(mbe.getType());
                        productionPlanEntity.setSource(mbe.getSource());
                        productionPlanEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        productionPlanEntity.setDrawingCode(mbe.getDrawingCode());
                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
                    }
                }

                ProductionLogDetailEntity detailEntity = new ProductionLogDetailEntity();
                detailEntity.setMaterialId(materialId);
                detailEntity.setStatus(STATUS_DEFAULT);
                detailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionLogDetailEntity> detailEntityList = productionLogDetailManager.getListBy(detailEntity);
                if (detailEntityList.size() != 0) {
                    for (ProductionLogDetailEntity productionLogDetailEntity : detailEntityList) {
                        productionLogDetailEntity.setMaterialId(materialMergeId);
                        productionLogDetailEntity.setMaterialCode(mbe.getMaterialCode());
                        productionLogDetailManager.updateByPrimaryKeySelective(productionLogDetailEntity);
                    }
                }
                MaterialBomEntity mbEntity = new MaterialBomEntity();
                mbEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mbEntity.setStatus(STATUS_INVALID);
                mbEntity.setId(materialId);
                manager.updateByPrimaryKeySelective(mbEntity);

                MaterialBomParamNameEntity mbpne = new MaterialBomParamNameEntity();
                mbpne.setMaterialId(materialId);
                mbpne.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mbpne.setStatus(STATUS_DEFAULT);
                List<MaterialBomParamNameEntity> mbpneList = materialBomParamNameManager.getListBy(mbpne);
                if (mbpneList.size() != 0) {
                    for (MaterialBomParamNameEntity mbpnEntity : mbpneList) {
                        mbpnEntity.setStatus(STATUS_INVALID);
                        materialBomParamNameManager.updateByPrimaryKeySelective(mbpnEntity);
                    }
                }

                MaterialCheckEntity mce = new MaterialCheckEntity();
                mce.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mce.setStatus(STATUS_DEFAULT);
                mce.setMaterialId(materialId);
                List<MaterialCheckEntity> mceList = materialCheckManager.getListBy(mce);
                if (mceList.size() != 0) {
                    for (MaterialCheckEntity mcEntity : mceList) {
                        mcEntity.setMaterialId(materialMergeId);
                        materialCheckManager.updateByPrimaryKeySelective(mcEntity);
                    }
                }

                MaterialCheckDetailEntity mcde = new MaterialCheckDetailEntity();
                mcde.setMaterialId(materialId);
                mcde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mcde.setStatus(STATUS_DEFAULT);
                List<MaterialCheckDetailEntity> mcdeList = materialCheckDetailManager.getListBy(mcde);
                if (mcdeList.size() != 0) {
                    for (MaterialCheckDetailEntity mcdEntity : mcdeList) {
                        mcdEntity.setMaterialId(materialMergeId);
                        materialCheckDetailManager.updateByPrimaryKeySelective(mcdEntity);
                    }
                }


                MaterialRepertoryEntity mre1 = new MaterialRepertoryEntity();
                mre1.setMaterialId(materialId);
                mre1.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mre1.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryEntity> mreList1 = materialRepertoryManager.getListBy(mre1);
                if (mreList1.size() != 0) {
                    for (MaterialRepertoryEntity mrEntity : mreList1) {

                        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                        mre.setMaterialId(materialMergeId);
                        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        mre.setStatus(STATUS_DEFAULT);
                        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                        if (mreList.size() != 0) {
                            mre = mreList.get(0);
                        }

                        mre.setInventoryQty(mre.getInventoryQty() + mrEntity.getInventoryQty());
                        mre.setStayProductionQty(mre.getStayProductionQty() + mrEntity.getStayProductionQty());
                        mre.setOrderNeedQty(mre.getOrderNeedQty() + mrEntity.getOrderNeedQty());
                        mre.setPlanStayQty(mre.getPlanStayQty() + mrEntity.getPlanStayQty());
                        mre.setStayInboundQty(mre.getStayInboundQty() + mrEntity.getStayInboundQty());
                        mre.setStayPurchaseQty(mre.getStayPurchaseQty() + mrEntity.getStayPurchaseQty());
                        mre.setStayCheckQty(mre.getStayCheckQty() + mrEntity.getStayCheckQty());
                        mre.setStayDeliveredQty(mre.getStayDeliveredQty() + mrEntity.getStayDeliveredQty());
                        mre.setUnqualifiedQty(mre.getUnqualifiedQty() + mrEntity.getUnqualifiedQty());
                        mrEntity.setStatus(STATUS_INVALID);
                        materialRepertoryManager.updateByPrimaryKeySelective(mrEntity);
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                    }
                }

                MaterialRepertoryDetailEntity mrde = new MaterialRepertoryDetailEntity();
                mrde.setMaterialId(materialId);
                mrde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrde.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryDetailEntity> mrdeList = materialRepertoryDetailManager.getListBy(mrde);
                if (mrdeList.size() != 0) {
                    for (MaterialRepertoryDetailEntity mrdEntity : mrdeList) {
                        mrdEntity.setMaterialId(materialMergeId);
                        mrdEntity.setMaterialCode(mbe.getMaterialCode());
                        mrdEntity.setMaterialName(mbe.getMaterialName());
                        mrdEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mrdEntity.setMaterialSource(mbe.getSource());
                        mrdEntity.setSource(mbe.getSource());
                        mrdEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mrdEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mrdEntity.setNumber(numberValue);
                        mrdEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryDetailManager.updateByPrimaryKeySelective(mrdEntity);
                    }
                }

                MaterialRepertoryDeliveryEntity mrDeliveryEntity = new MaterialRepertoryDeliveryEntity();
                mrDeliveryEntity.setMaterialId(materialId);
                mrDeliveryEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrDeliveryEntity.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryDeliveryEntity> mrDeliveryEntityList = materialRepertoryDeliveryManager.getListBy(mrDeliveryEntity);
                if (mrDeliveryEntityList.size() != 0) {
                    for (MaterialRepertoryDeliveryEntity mrDeliveryEntity1 : mrDeliveryEntityList) {
                        mrDeliveryEntity1.setMaterialId(materialMergeId);
                        mrDeliveryEntity1.setMaterialCode(mbe.getMaterialCode());
                        mrDeliveryEntity1.setMaterialName(mbe.getMaterialName());
                        mrDeliveryEntity1.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mrDeliveryEntity1.setSource(mbe.getSource());
                        mrDeliveryEntity1.setOriginalMaterial(mbe.getOriginalMaterial());
                        mrDeliveryEntity1.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mrDeliveryEntity1.setNumber(numberValue);
                        mrDeliveryEntity1.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryDeliveryManager.updateByPrimaryKeySelective(mrDeliveryEntity1);
                    }
                }

                MaterialRepertoryInboundlogEntity mrile = new MaterialRepertoryInboundlogEntity();
                mrile.setMaterialId(materialId);
                mrile.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrile.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryInboundlogEntity> mrileList = materialRepertoryInboundlogManager.getListBy(mrile);
                if (mrileList.size() != 0) {
                    for (MaterialRepertoryInboundlogEntity mrilEntity : mrileList) {
                        mrilEntity.setMaterialId(materialMergeId);
                        mrilEntity.setMaterialCode(mbe.getMaterialCode());
                        mrilEntity.setMaterialName(mbe.getMaterialName());
                        mrilEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mrilEntity.setSource(mbe.getSource());
                        mrilEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mrilEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mrilEntity.setNumber(numberValue);
                        mrilEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryInboundlogManager.updateByPrimaryKeySelective(mrilEntity);
                    }
                }

                ProductionLogEntity logEntity = new ProductionLogEntity();
                logEntity.setMaterialId(materialId);
                logEntity.setStatus(STATUS_DEFAULT);
                logEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionLogEntity> logEntityList = productionLogManager.getListBy(logEntity);
                if (logEntityList.size() != 0) {
                    for (ProductionLogEntity productionLogEntity : logEntityList) {
                        productionLogEntity.setMaterialId(materialMergeId);
                        productionLogEntity.setMaterialCode(mbe.getMaterialCode());
                        productionLogEntity.setMaterialName(mbe.getMaterialName());
                        productionLogEntity.setMaterialUnit(mbe.getMaterialUnit());
                        productionLogEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        productionLogManager.updateByPrimaryKeySelective(productionLogEntity);
                    }
                }
                MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
                mrice.setMaterialId(materialId);
                mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrice.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryIncomingCheckEntity> mriceList = materialRepertoryIncomingCheckManager.getListBy(mrice);
                if (mriceList.size() != 0) {
                    for (MaterialRepertoryIncomingCheckEntity mricEntity : mriceList) {
                        mricEntity.setMaterialId(materialMergeId);
                        mricEntity.setMaterialCode(mbe.getMaterialCode());
                        mricEntity.setMaterialName(mbe.getMaterialName());
                        mricEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mricEntity.setSource(mbe.getSource());
                        mricEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mricEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mricEntity.setNumber(numberValue);
                        mricEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(mricEntity);
                    }
                }

                ProductionCheckLogEntity checkLogEntity = new ProductionCheckLogEntity();
                checkLogEntity.setMaterialId(materialId);
                checkLogEntity.setStatus(STATUS_DEFAULT);
                checkLogEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionCheckLogEntity> checkLogEntityList = productionCheckLogManager.getListBy(checkLogEntity);
                if (checkLogEntityList.size() != 0) {
                    for (ProductionCheckLogEntity productionCheckLogEntity : checkLogEntityList) {
                        productionCheckLogEntity.setMaterialId(materialMergeId);
                        productionCheckLogEntity.setMaterialCode(mbe.getMaterialCode());
                        productionCheckLogEntity.setMaterialName(mbe.getMaterialName());
                        productionCheckLogManager.updateByPrimaryKeySelective(productionCheckLogEntity);
                    }
                }

                ProduceMaterialReturnLogEntity returnLogEntity = new ProduceMaterialReturnLogEntity();
                returnLogEntity.setMaterialId(materialId);
                returnLogEntity.setStatus(STATUS_DEFAULT);
                returnLogEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProduceMaterialReturnLogEntity> returnLogEntityList = produceMaterialReturnLogManager.getListBy(returnLogEntity);
                if (returnLogEntityList.size() != 0) {
                    for (ProduceMaterialReturnLogEntity produceMaterialReturnLogEntity : returnLogEntityList) {
                        produceMaterialReturnLogEntity.setMaterialId(materialMergeId);
                        produceMaterialReturnLogEntity.setMaterialName(mbe.getMaterialName());
                        produceMaterialReturnLogManager.updateByPrimaryKeySelective(produceMaterialReturnLogEntity);
                    }
                }

                ProductionIssueDetailEntity issueDetailEntity = new ProductionIssueDetailEntity();
                issueDetailEntity.setMaterialId(materialId);
                issueDetailEntity.setStatus(STATUS_DEFAULT);
                issueDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionIssueDetailEntity> issueDetailEntityList = productionIssueDetailManager.getListBy(issueDetailEntity);
                if (issueDetailEntityList.size() != 0) {
                    for (ProductionIssueDetailEntity productionIssueDetailEntity : issueDetailEntityList) {
                        productionIssueDetailEntity.setMaterialId(materialMergeId);
                        productionIssueDetailEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        productionIssueDetailEntity.setMaterialName(mbe.getMaterialName());
                        productionIssueDetailEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        productionIssueDetailEntity.setDrawingCode(mbe.getDrawingCode());
                        productionIssueDetailEntity.setNumber(mbe.getType());
                        productionIssueDetailEntity.setSource(mbe.getSource());
                        productionIssueDetailManager.updateByPrimaryKeySelective(productionIssueDetailEntity);
                    }
                }

                ProductionIssueEntity issueEntity = new ProductionIssueEntity();
                issueEntity.setMaterialId(materialId);
                issueEntity.setStatus(STATUS_DEFAULT);
                issueEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductionIssueEntity> issueEntityList = productionIssueManager.getListBy(issueEntity);
                if (issueEntityList.size() != 0) {
                    for (ProductionIssueEntity productionIssueEntity : issueEntityList) {
                        productionIssueEntity.setMaterialId(materialMergeId);
                        productionIssueEntity.setDrawingCode(mbe.getDrawingCode());
                        productionIssueEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        productionIssueEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        productionIssueEntity.setNumber(mbe.getType());
                        productionIssueEntity.setSource(mbe.getSource());
                        productionIssueEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        productionIssueManager.updateByPrimaryKeySelective(productionIssueEntity);
                    }
                }

                MaterialSendDetailEntity sendDetailEntity = new MaterialSendDetailEntity();
                sendDetailEntity.setMaterialId(materialId);
                sendDetailEntity.setStatus(STATUS_DEFAULT);
                sendDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<MaterialSendDetailEntity> sendDetailEntityList = materialSendDetailManager.getListBy(sendDetailEntity);
                if (sendDetailEntityList.size() != 0) {
                    for (MaterialSendDetailEntity materialSendDetailEntity : sendDetailEntityList) {
                        materialSendDetailEntity.setMaterialId(materialMergeId);
                        materialSendDetailEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        materialSendDetailEntity.setMaterialCode(mbe.getMaterialCode());
                        materialSendDetailEntity.setMaterialName(mbe.getMaterialName());
                        materialSendDetailEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        materialSendDetailEntity.setNumber(mbe.getType());
                        materialSendDetailEntity.setDrawingCode(mbe.getDrawingCode());
                        materialSendDetailEntity.setSource(mbe.getSource());
                        materialSendDetailEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        materialSendDetailManager.updateByPrimaryKeySelective(materialSendDetailEntity);
                    }
                }
                MaterialRepertoryOutboundEntity mroe = new MaterialRepertoryOutboundEntity();
                List<MaterialRepertoryOutboundEntity> mroeList = materialRepertoryOutboundManager.getListBy(mroe);
                if (mroeList.size() != 0) {
                    for (MaterialRepertoryOutboundEntity mroEntity : mroeList) {
                        mroEntity.setMaterialId(materialMergeId);
                        mroEntity.setMaterialCode(mbe.getMaterialCode());
                        mroEntity.setMaterialName(mbe.getMaterialName());
                        mroEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mroEntity.setSource(mbe.getSource());
                        mroEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mroEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mroEntity.setNumber(numberValue);
                        mroEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryOutboundManager.updateByPrimaryKeySelective(mroEntity);
                    }
                }

                MaterialRepertoryOutboundDetailEntity mrode = new MaterialRepertoryOutboundDetailEntity();
                mrode.setMaterialId(materialId);
                mrode.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrode.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryOutboundDetailEntity> mrodeList = materialRepertoryOutboundDetailManager.getListBy(mrode);
                if (mrodeList.size() != 0) {
                    for (MaterialRepertoryOutboundDetailEntity mrodEntity : mrodeList) {
                        mrodEntity.setMaterialId(materialMergeId);
                        mrodEntity.setMaterialCode(mbe.getMaterialCode());
                        mrodEntity.setMaterialName(mbe.getMaterialName());
                        mrodEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mrodEntity.setSource(mbe.getSource());
                        mrodEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mrodEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mrodEntity.setNumber(numberValue);
                        mrodEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryOutboundDetailManager.updateByPrimaryKeySelective(mrodEntity);
                    }
                }

                MaterialRepertoryOutboundlogEntity mrole = new MaterialRepertoryOutboundlogEntity();
                mrole.setMaterialId(materialId);
                mrole.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                mrole.setStatus(STATUS_DEFAULT);
                List<MaterialRepertoryOutboundlogEntity> mroleList = materialRepertoryOutboundlogManager.getListBy(mrole);
                if (mroleList.size() != 0) {
                    for (MaterialRepertoryOutboundlogEntity mrolEntity : mroleList) {
                        mrolEntity.setMaterialId(materialMergeId);
                        mrolEntity.setMaterialCode(mbe.getMaterialCode());
                        mrolEntity.setMaterialName(mbe.getMaterialName());
                        mrolEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        mrolEntity.setSource(mbe.getSource());
                        mrolEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        mrolEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        mrolEntity.setNumber(numberValue);
                        mrolEntity.setDrawingCode(mbe.getDrawingCode());
                        materialRepertoryOutboundlogManager.updateByPrimaryKeySelective(mrolEntity);
                    }
                }
            }
        }
    }

    //导出
    public List<MaterialTemplate> getExportList() {
        List<MaterialTemplate> result = new ArrayList<>();
        return result;
    }

    public MaterialTemplate getMaterialTemplate(Integer materialId, Float quantity) {
        MaterialTemplate materialTemplate = new MaterialTemplate();
        return materialTemplate;
    }

    public Result<Void> isRepeat(MaterialBomInfo materialBomInfo) {
        Integer checkAuditingStatus = materialBomInfo.getCheckAuditingStatus();
        materialBomInfo.setCheckAuditingStatus(null);
        ExchangeUtils.convertBlank2Null(materialBomInfo);
        MaterialBomEntity mbe = ExchangeUtils.exchangeObject(materialBomInfo, MaterialBomEntity.class);

        MaterialBomInfo mbeTrim = new MaterialBomInfo();

        String factoryMaterialCodeTrim = null;
        String materialNameTrim = null;
        String originalMaterialTrim = null;
        if (mbe.getFactoryMaterialCode() != null) {
            factoryMaterialCodeTrim = mbe.getFactoryMaterialCode().replace(" ", "");
        }
        if (mbe.getMaterialName() != null) {
            materialNameTrim = mbe.getMaterialName().replace(" ", "");
        }
        if (mbe.getOriginalMaterial() != null) {
            originalMaterialTrim = mbe.getOriginalMaterial().replace(" ", "");
        }

        Integer drawingId = materialBomInfo.getDrawingId();
        Integer checkId = materialBomInfo.getCheckId();
        String drawingCode = null;
        if (drawingId != null) {
            ProductDrawingEntity pde = productDrawingManager.getEntityById(drawingId);
            drawingCode = pde.getDrawingCode();
        }
        if (materialBomInfo.getDynamicParameterName() != null) {
            String materialBomParamValueStr = "";
            for (int i = 0; i < materialBomInfo.getDynamicParameterName().size(); i++) {
                if (i == materialBomInfo.getDynamicParameterName().size() - 1) {
                    materialBomParamValueStr += materialBomInfo.getDynamicParameterName().get(i).get("materialParamNameValue");
                } else {
                    materialBomParamValueStr += materialBomInfo.getDynamicParameterName().get(i).get("materialParamNameValue") + ",";
                }
            }
            mbeTrim.setMaterialBomParamValueStr(materialBomParamValueStr.replace(" ", ""));
        }
        mbeTrim.setFactoryMaterialCode(factoryMaterialCodeTrim);
        mbeTrim.setMaterialName(materialNameTrim);
        mbeTrim.setOriginalMaterial(originalMaterialTrim);
        mbeTrim.setNumber(materialBomInfo.getNumber());
        //把页面传过来的source标识转换成中文
        String source = "";
        if (mbe.getSource() != null) {
            if (Integer.parseInt(mbe.getSource()) == 0) {
                source = "自制";
            } else if (Integer.parseInt(mbe.getSource()) == 1) {
                source = "外购";
            } else {
                source = "自制/外购";
            }
        } else {
            source = "外购";
        }
        mbeTrim.setSource(source);
        mbeTrim.setStatus(STATUS_DEFAULT);
        mbeTrim.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        //去空格判断是否值相等
        List<MaterialBomInfo> mbiTrimList = manager.getMbeTrimListByInfo(mbeTrim, drawingCode, checkId);

        if (mbiTrimList.size() > 0) {
            return ResultUtils.failure("物料已存在，相同物料编码为" + mbiTrimList.get(0).getMaterialCode() + "!");
        } else {
            saveMaterial(materialBomInfo);
            return ResultUtils.success();
        }

        /*mbe.setSource(source);
        //首先给编号赋值为空，后面生成了id之后再更新编码
        mbe.setMaterialCode("null");
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        mbe.setMaterialBomCreated(createDate);
        //获取当前登陆的账号的名称，赋值给制作人
        String author = LoginFilter.getCurrentUser().getUserName();
        mbe.setAuthor(author);
        mbe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        MaterialBomInfo tempInfo = ExchangeUtils.exchangeObject(mbe, MaterialBomInfo.class);

        //先判断是否重复然后再加入
        tempInfo.setMaterialBomCreated(null);
        tempInfo.setMaterialCode(null);
        tempInfo.setStatus(STATUS_DEFAULT);

        List<MaterialBomInfo> repetitionList = this.getListBy(tempInfo);
        boolean []isSame = new boolean[mbiTrimList.size()];
        boolean result=false;//图纸和验收标准统一判断
        boolean paramIsSame=false;//参数另外判断
        if (mbiTrimList != null || mbiTrimList.size() != 0) {
            for (int i = 0; i < mbiTrimList.size(); i++) {
                int bomid=mbiTrimList.get(i).getId();
                MaterialCheckEntity materialCheckEntity = new MaterialCheckEntity();
                materialCheckEntity.setMaterialId(mbiTrimList.get(i).getId());
                List<MaterialCheckEntity> materialCheckEntities = materialCheckManager.getListBy(materialCheckEntity);
                if (materialCheckEntities != null && materialCheckEntities.size() != 0) {
                    mbiTrimList.get(i).setCheckId(materialCheckEntities.get(0).getCheckId());
                }
                MaterialDrawingEntity materialDrawingEntity = new MaterialDrawingEntity();
                materialDrawingEntity.setMaterialId(mbiTrimList.get(i).getId());
                List<MaterialDrawingEntity> materialDrawingEntities = materialDrawingManager.getListBy(materialDrawingEntity);
                if (materialDrawingEntities != null && materialDrawingEntities.size() != 0) {
                    mbiTrimList.get(i).setDrawingId(materialDrawingEntities.get(0).getDrawingId());
                }

                try {
                    isSame[i] = this.CompareProperties(mbiTrimList.get(i), materialBomInfo);
                }  catch (Exception e) {
                    e.printStackTrace();
                }
                MaterialBomParamNameEntity materialBomParamNameEntity=new MaterialBomParamNameEntity();
                materialBomParamNameEntity.setMaterialId(bomid);
                List<MaterialBomParamNameEntity> materialBomParamNameEntityList= materialBomParamNameManager.getListBy(materialBomParamNameEntity);
                List paramValueList=new ArrayList();
                for (int j = 0; j <materialBomParamNameEntityList.size() ; j++) {
                    paramValueList.add(materialBomParamNameEntityList.get(j).getMaterialParamNameValue());
                }
                List bomParamList=new ArrayList();
                if(materialBomInfo.getDynamicParameterName() != null){
                    for (int j = 0; j <materialBomInfo.getDynamicParameterName().size() ; j++) {
                        bomParamList.add(materialBomInfo.getDynamicParameterName().get(j).get("materialParamNameValue"));
                    }
                }
                if(bomParamList.size()!=paramValueList.size()){

                }else {
                    Collections.sort(bomParamList);
                    Collections.sort(paramValueList);
                    paramIsSame=bomParamList.equals(paramValueList);
                }
                    result=isSame[i]||result;
            }
            if(!(result&&paramIsSame)){
                saveMaterial(materialBomInfo);
                return ResultUtils.success();
            }else {
               return ResultUtils.failure("保存失败，该物料已存在！");
            }
        }else {
            saveMaterial(materialBomInfo);
            return ResultUtils.success();
        }*/
    }

    private static boolean CompareProperties(Object obj1, Object obj2) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        //为空判断
        if (obj1 == null && obj2 == null) {
            return true;
        } else if (obj1 == null || obj2 == null) {
            return false;
        }

        Class<?> classType = obj1.getClass();
        //如果传入的类型不一样则直接返回false
        if (classType != obj2.getClass()) {
            return false;
        }
        List<String> fieldName = new ArrayList<>();
        fieldName.add("drawingId");
        fieldName.add("checkId");
        List<Object> obj1Value = new ArrayList<>();
        List<Object> obj2Value = new ArrayList<>();

        for (int i = 0; i < fieldName.size(); i++) {
            obj1Value.add(getFieldValueByFieldName(fieldName.get(i), obj1));
            obj2Value.add(getFieldValueByFieldName(fieldName.get(i), obj2));
            if (!obj1Value.get(i).equals(obj2Value.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            if (field.get(object) != null) {
                Object value = field.get(object);
                return value;
            } else {
                return "null";
            }
        } catch (Exception e) {

            return null;
        }
    }


    @Transactional
    public void saveMaterial(MaterialBomInfo materialBomInfo) {
        ExchangeUtils.convertBlank2Null(materialBomInfo);
        MaterialBomEntity mbe = ExchangeUtils.exchangeObject(materialBomInfo, MaterialBomEntity.class);
        //把页面传过来的source标识转换成中文
        String source = "";
        if (mbe.getSource() != null) {
            if (Integer.parseInt(mbe.getSource()) == 0) {
                source = "自制";
            } else if (Integer.parseInt(mbe.getSource()) == 1) {
                source = "外购";
            } else {
                source = "自制/外购";
            }
        } else {
            source = "外购";
        }
        mbe.setSource(source);
        //首先给编号赋值为空，后面生成了id之后再更新编码
        mbe.setMaterialCode("null");
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        mbe.setMaterialBomCreated(createDate);
        //把物料编码拼接（类型编号+名称编号+材料编号+单位编号+最大值）
//        Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId())+1;
//        String materialCode = mbe.getNumber()+mbe.getNameCode()+mbe.getOriginalCode()+mbe.getUnitCode()+nextNumber;
//        mbe.setMaterialCode(materialCode);
//        mbe.setMaterialNumber(nextNumber);
        //获取当前登陆的账号的名称，赋值给制作人
        String author = LoginFilter.getCurrentUser().getUserName();
        mbe.setAuthor(author);
        mbe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        //根据物料类型number获取物料类型名称
        ProductTypeEntity pte = new ProductTypeEntity();
        pte.setNumber(materialBomInfo.getNumber());
        pte.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<ProductTypeEntity> pteList = productTypeManager.getListBy(pte);
        if (pteList.size() != 0) {
            mbe.setType(pteList.get(0).getType());
        }
        //首先往部件表插入数据，并且返回部件id
        MaterialBomEntity materialBomEntity = manager.insertBy(mbe);
        if (materialBomEntity != null) {
            Integer materialId = materialBomEntity.getId();
            //先判断名称中是否包含中文
            String pinYin = "";
            Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId()) + 1;
            if (isContainChinese(mbe.getMaterialName()) == true) {
                pinYin = getPinYinHeadChar(mbe.getMaterialName()).toUpperCase();
                //根据中文的首字母加id生成编号
                mbe.setMaterialCode(materialBomInfo.getNumber() + pinYin + nextNumber);
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            } else {
                pinYin = wordToUpperCase(mbe.getMaterialName());
                //根据英文的首字母加id生成编号
                mbe.setMaterialCode(materialBomInfo.getNumber() + pinYin + nextNumber);
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            }

            //循环往部件-参数表插入数据
            if (materialBomInfo.getDynamicParameterName() != null && materialBomInfo.getDynamicParameterName().size() != 0) {
                List<Map<String, String>> dynamicParameterNameMapList = materialBomInfo.getDynamicParameterName();
                for (int i = 0; i < dynamicParameterNameMapList.size(); i++) {
                    Map<String, String> dynamicParameterNameMap = dynamicParameterNameMapList.get(i);
                    MaterialBomParamNameEntity materialBomParamNameInfo = new MaterialBomParamNameEntity();
                    materialBomParamNameInfo.setMaterialId(materialId);
                    materialBomParamNameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialBomParamNameInfo.setMaterialParamNameId(0);
                    materialBomParamNameInfo.setMaterialParamNameValue(dynamicParameterNameMap.get("materialParamNameValue"));
                    materialBomParamNameManager.insertBy(materialBomParamNameInfo);
                }
            }
            mbe.setMaterialBomParamValueStr(getParamValue(materialId));
            //往部件-验收标准中间表插入数据
            MaterialCheckEntity materialCheckInfo = new MaterialCheckEntity();
            if (materialBomInfo.getCheckId() != null && !"".equals(materialBomInfo.getCheckId())) {
                materialCheckInfo.setCheckId(materialBomInfo.getCheckId());
                materialCheckInfo.setMaterialId(materialId);
                materialCheckInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialCheckManager.insertBy(materialCheckInfo);
            }
            //往部件-图纸中间表插入数据
            MaterialDrawingEntity materialDrawingInfo = new MaterialDrawingEntity();
            if (materialBomInfo.getDrawingId() != null && !"".equals(materialBomInfo.getDrawingId())) {
                materialDrawingInfo.setDrawingId(materialBomInfo.getDrawingId());
                materialDrawingInfo.setMaterialId(materialId);
                materialDrawingInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialDrawingManager.insertBy(materialDrawingInfo);
                //根据图纸id查询图纸编号
                ProductDrawingEntity pde = productDrawingManager.getEntityById(materialBomInfo.getDrawingId());
                mbe.setDrawingCode(pde.getDrawingCode());
            }
            //往部件-部件中间表插入数据，关联父子关系
            if (materialBomInfo.getMapList() != null && materialBomInfo.getMapList().size() != 0) {
                List<Map<String, String>> mapList = materialBomInfo.getMapList();
                for (int i = 0; i < mapList.size(); i++) {
                    Map<String, String> map = mapList.get(i);
                    MaterialRelationEntity materialRelationInfo = new MaterialRelationEntity();
                    materialRelationInfo.setMaterialParentId(materialId);
                    materialRelationInfo.setMaterialChildId(Integer.parseInt(map.get("id")));
                    materialRelationInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    Float quantity = Float.parseFloat(map.get("quantity"));
                    materialRelationInfo.setQuantity(quantity);
                    materialRelationManager.insertBy(materialRelationInfo);
                }
            }
            //往部件-制程中间表添加数据
            List<JSONObject> processInfo = materialBomInfo.getProcessInfo();
            if (processInfo != null) {
                for (int i = 0; i < processInfo.size(); i++) {
                    JSONObject jsonObject = processInfo.get(i);
                    MaterialProcessEntity mp = new MaterialProcessEntity();
                    mp.setMaterialId(materialId);
					/*mp.setJobGuide(jsonObject.getString("作业指导书"));
					mp.setPeople(jsonObject.getInt("人数"));
					mp.setProcessName(jsonObject.getString("制程名称"));
					mp.setRatedHour(jsonObject.getDouble("额定工时"));*/
                    mp.setJsonParam(jsonObject.toString());
                    mp.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    mp.setCheckStatus(jsonObject.getInt("checkStatus"));
                    Float price = 0f;
                    if (jsonObject.getString("price") != "") {
                        price = Float.parseFloat(jsonObject.getString("price"));
                    }
                    mp.setProcessPrice(price);
                    MaterialProcessEntity materialProcessEntity = materialProcessManager.insertBy(mp);
                    //修改json串id为当前记录id
                    jsonObject.put("id", materialProcessEntity.getId());
                    materialProcessManager.updateByPrimaryKeySelective(materialProcessEntity);
                }
            }

           /* //往仓库新增一条数据 只需要一个物料id和公司id  其他数量初始化为0
            MaterialRepertoryEntity mri = new MaterialRepertoryEntity();
            mri.setMaterialId(materialId);
            mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            mri.setMaterialCode(mbe.getMaterialCode());
            mri.setMaterialName(mbe.getMaterialName());
            mri.setOriginalMaterial(mbe.getOriginalMaterial());
            mri.setFactoryMaterialCode(materialBomInfo.getFactoryMaterialCode());
            mri.setDrawingCode(mbe.getDrawingCode());
            String numberValue = null;
            numberValue = materialRepertoryDetailService.getNumberValue(materialBomInfo.getNumber(), numberValue);
            mri.setNumber(numberValue);
            mri.setMaterialSource(mbe.getSource());
            mri.setMaterialBomParamValueStr(getParamValue(mbe.getId()));
            materialRepertoryManager.insertBy(mri);*/

            mbe.setId(materialId);
            manager.updateByPrimaryKeySelective(mbe);

            //审核状态,0 审核通过,1 待审核,
            if (materialBomInfo.getAuditingStatus() == 1) {
                mbe.setAuditingStatus(IN_AUDITING);
                Map<String, Object> var = new HashMap<>();
                //手动插入流程参数
                var.put("materialName", mbe.getMaterialName());
                var.put("materialCode", mbe.getMaterialCode());
                tasksService.startWorkflow(this.workflowKey, materialId.toString(), var);
            }
            if (materialBomInfo.getAuditingStatus() == 0) {
                auditingPass(materialId);
            }

            logger.info("物料新增:"+mbe.getMaterialCode()+"-"+"工艺id:"+materialBomInfo.getProcessId()+"-"+"操作人:"+LoginFilter.getCurrentUser().getUserName());
            /*SupplierEntity se = new SupplierEntity();
            se.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            se.setStatus(STATUS_DEFAULT);
            se.setSupplierFlag(0);
            List<SupplierEntity> seList = supplierManager.getListBy(se);
            if(seList.size() != 0){
                se = seList.get(0);
                SupplierMaterialEntity sme = new SupplierMaterialEntity();
                MaterialBomEntity mbEntity = manager.getEntityById(materialId);
                sme.setMaterialId(materialId);
                sme.setFactoryMaterialCode(mbEntity.getFactoryMaterialCode());
                sme.setMaterialBomParamValueStr(mbEntity.getMaterialBomParamValueStr());
                sme.setMaterialCode(mbEntity.getMaterialCode());
                sme.setMaterialName(mbEntity.getMaterialName());
                sme.setOriginalMaterial(mbEntity.getOriginalMaterial());
                sme.setType(mbEntity.getType());
                sme.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                sme.setStatus(STATUS_DEFAULT);
                sme.setSupplierId(se.getId());
                //价格、最小值、最小采购数默认
                sme.setPrice(1f);
                sme.setMinQty(1f);
                sme.setPurchaseDays("1");
                supplierMaterialManager.insertBy(sme);
            }*/
        }
    }

    @Transactional
    public void updateMaterial(MaterialBomInfo materialBomInfo) {
        materialBomInfo.setCheckAuditingStatus(null);
        ExchangeUtils.convertBlank2Null(materialBomInfo);
        MaterialBomEntity mbe = ExchangeUtils.exchangeObject(materialBomInfo, MaterialBomEntity.class);
        //获取部件id
        Integer materialId = materialBomInfo.getId();
        mbe.setAuthor(LoginFilter.getCurrentUser().getUserName());
        if (mbe.getFactoryMaterialCode()==null){
            mbe.setFactoryMaterialCode("");
        }
        //修改material-bom表数据
        manager.updateByPrimaryKeySelective(mbe);
        //修改部件-验收标准中间表数据
        if (materialBomInfo.getCheckId() != null && !"".equals(materialBomInfo.getCheckId())) {
            MaterialCheckEntity materialCheckInfo = new MaterialCheckEntity();
            materialCheckInfo.setMaterialId(materialId);
            materialCheckInfo.setStatus(1);
            //根据物料id去查找数据
            List<MaterialCheckEntity> mciList = materialCheckManager.getListBy(materialCheckInfo);
            if (mciList.size() != 0) {
                //如果有，把id取出来放入对象，执行方法
                for (int i = 0; i < mciList.size(); i++) {
                    materialCheckInfo.setId(mciList.get(i).getId());
                }
                materialCheckInfo.setCheckId(materialBomInfo.getCheckId());
                materialCheckManager.updateByPrimaryKeySelective(materialCheckInfo);
            } else {
                //如果没有，则没有id，直接执行方法
                MaterialCheckEntity mci = new MaterialCheckEntity();
                mci.setMaterialId(materialId);
                mci.setCheckId(materialBomInfo.getCheckId());
                mci.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialCheckManager.insertBy(mci);
            }
        }
        //修改部件-图纸中间表数据
        if (materialBomInfo.getDrawingId() != null && !"".equals(materialBomInfo.getDrawingId())) {
            //根据物料id去查找数据
            MaterialDrawingEntity materialDrawingInfo = new MaterialDrawingEntity();
            materialDrawingInfo.setMaterialId(materialId);
            materialDrawingInfo.setStatus(1);
            List<MaterialDrawingEntity> mdiList = materialDrawingManager.getListBy(materialDrawingInfo);
            if (mdiList.size() != 0) {
                //如果有，把id取出来放入对象，执行方法
                for (int i = 0; i < mdiList.size(); i++) {
                    materialDrawingInfo.setId(mdiList.get(i).getId());
                }
                materialDrawingInfo.setDrawingId(materialBomInfo.getDrawingId());
                materialDrawingManager.updateByPrimaryKeySelective(materialDrawingInfo);
            } else {
                //如果没有，则没有id，直接执行方法
                MaterialDrawingEntity mdi = new MaterialDrawingEntity();
                mdi.setMaterialId(materialId);
                mdi.setDrawingId(materialBomInfo.getDrawingId());
                mdi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialDrawingManager.insertBy(mdi);
            }
            //根据图纸id查询图纸编号
            ProductDrawingEntity pde = productDrawingManager.getEntityById(materialBomInfo.getDrawingId());
            mbe.setDrawingCode(pde.getDrawingCode());
        } else {
            mbe.setDrawingCode("");
        }
        //修改部件-部件中间表数据，关联父子关系
        if (materialBomInfo.getMapList() != null && materialBomInfo.getMapList().size() != 0) {
            List<Map<String, String>> mapList = materialBomInfo.getMapList();
            //根据物料id获取数据
            MaterialRelationEntity materialRelationInfo = new MaterialRelationEntity();
            materialRelationInfo.setMaterialParentId(materialId);
            materialRelationInfo.setStatus(1);
            List<MaterialRelationEntity> mriList = materialRelationManager.getListBy(materialRelationInfo);
            for (int i = 0; i < mapList.size(); i++) {
                Map<String, String> map = mapList.get(i);
                if (mriList.size() != 0) {
                    //如果有，先把状态改变为0
                    for (int j = 0; j < mriList.size(); j++) {
                        materialRelationInfo.setStatus(0);
                        materialRelationInfo.setId(mriList.get(j).getId());
                        materialRelationManager.updateByPrimaryKeySelective(materialRelationInfo);
                    }
                    //然后新增数据
                    MaterialRelationEntity mri = new MaterialRelationEntity();
                    mri.setMaterialParentId(materialId);
                    mri.setMaterialChildId(Integer.parseInt(map.get("id")));
                    Float quantity = Float.parseFloat(map.get("quantity"));
                    mri.setQuantity(quantity);
                    mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialRelationManager.insertBy(mri);
                } else {
                    //如果没有，直接新增
                    MaterialRelationEntity mri = new MaterialRelationEntity();
                    mri.setMaterialParentId(materialId);
                    mri.setMaterialChildId(Integer.parseInt(map.get("id")));
                    Float quantity = Float.parseFloat(map.get("quantity"));
                    mri.setQuantity(quantity);
                    mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialRelationManager.insertBy(mri);
                }
            }
        }
        //循环往部件-参数表插入数据
        if (materialBomInfo.getDynamicParameterName() != null && materialBomInfo.getDynamicParameterName().size() != 0) {
            List<Map<String, String>> dynamicParameterNameMapList = materialBomInfo.getDynamicParameterName();
            //根据物料id查询中间表中的数据
            MaterialBomParamNameEntity materialBomParamNameInfo = new MaterialBomParamNameEntity();
            materialBomParamNameInfo.setMaterialId(materialId);
            materialBomParamNameInfo.setStatus(1);
            List<MaterialBomParamNameEntity> mbpniList = materialBomParamNameManager.getListBy(materialBomParamNameInfo);
            for (int i = 0; i < dynamicParameterNameMapList.size(); i++) {
                Map<String, String> dynamicParameterNameMap = dynamicParameterNameMapList.get(i);
                if (mbpniList.size() != 0) {
                    //如果有，先把状态改变成0
                    for (int j = 0; j < mbpniList.size(); j++) {
                        materialBomParamNameInfo.setStatus(0);
                        materialBomParamNameInfo.setId(mbpniList.get(j).getId());
                        materialBomParamNameManager.updateByPrimaryKeySelective(materialBomParamNameInfo);
                    }
                    //然后新增数据
                    MaterialBomParamNameEntity mbpni = new MaterialBomParamNameEntity();
                    mbpni.setMaterialId(materialId);
                    mbpni.setMaterialParamNameId(0);
                    mbpni.setMaterialParamNameValue(dynamicParameterNameMap.get("materialParamNameValue"));
                    materialBomParamNameManager.insertBy(mbpni);
                } else {
                    //如果没有直接新增
                    MaterialBomParamNameEntity mbpni = new MaterialBomParamNameEntity();
                    mbpni.setMaterialId(materialId);
                    mbpni.setMaterialParamNameId(0);
                    mbpni.setMaterialParamNameValue(dynamicParameterNameMap.get("materialParamNameValue"));
                    materialBomParamNameManager.insertBy(mbpni);
                }
                mbe.setMaterialBomParamValueStr(getParamValue(materialId));
            }
        }
        //往物料-制程表中插入数据
        //先把原来的删除
        MaterialProcessEntity processEntity = new MaterialProcessEntity();
        processEntity.setMaterialId(materialId);
        processEntity.setStatus(STATUS_DEFAULT);
        processEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<MaterialProcessEntity> processEntityList = materialProcessManager.getListBy(processEntity);
        if (processEntityList.size()!=0){
            for (int i=0;i<processEntityList.size();i++){
                materialProcessManager.deleteByPrimaryKey(processEntityList.get(i).getId());
            }
        }
        if (materialBomInfo.getProcessInfo() != null) {
            List<JSONObject> processInfo = materialBomInfo.getProcessInfo();
            if (processInfo != null) {
                for (int i = 0; i < processInfo.size(); i++) {
                    JSONObject jsonObject = processInfo.get(i);
                    MaterialProcessEntity mp = new MaterialProcessEntity();
                    mp.setMaterialId(materialId);
                    mp.setJsonParam(jsonObject.toString());
                    mp.setCheckStatus(jsonObject.getInt("checkStatus"));
                    Float price = 0f;
                    if (jsonObject.getString("price") != "") {
                        price = Float.parseFloat(jsonObject.getString("price"));
                    }
                    mp.setProcessPrice(price);
                    mp.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    MaterialProcessEntity mpe = materialProcessManager.insertBy(mp);
                }
            }
        }

        manager.updateByPrimaryKeySelective(mbe);
        logger.info("物料修改:"+materialBomInfo.getMaterialCode()+"-"+"工艺id:"+materialBomInfo.getProcessId()+"-"+"操作人:"+LoginFilter.getCurrentUser().getUserName());
        //审核状态,0 审核通过,1 待审核,
        if (materialBomInfo.getAuditingStatus() == 1) {
            mbe.setAuditingStatus(IN_AUDITING);
            Map<String, Object> var = new HashMap<>();
            //手动插入流程参数
            var.put("materialName", mbe.getMaterialName());
            var.put("materialCode", mbe.getMaterialCode());
            tasksService.startWorkflow(this.workflowKey, materialId.toString(), var);
        }
        if (materialBomInfo.getAuditingStatus() == 0) {
            auditingPass(materialId);
        }
    }

    public Float getMaxPriceToSupplier(Integer materialId, Integer companyId) {
        return manager.getMaxPriceToSupplier(materialId, companyId);
    }

    public Float getMaxPriceToRepertory(Integer materialId, Integer companyId) {
        return manager.getMaxPriceToRepertory(materialId, companyId);
    }

    /**
     * 获取拼接的参数值
     *
     * @param id
     * @return
     */
    public String getParamValue(Integer id) {
        //根据子id去查部件-参数表
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialId(id);
        materialBomParamNameInfo.setStatus(1);
        MaterialBomParamNameEntity mbpne = ExchangeUtils.exchangeObject(materialBomParamNameInfo, MaterialBomParamNameEntity.class);
        List<MaterialBomParamNameEntity> mbpniList = materialBomParamNameManager.getListBy(mbpne);
        String materialBomParamNameValueStr = "";
        if (mbpniList.size() != 0) {
            for (int j = 0; j < mbpniList.size(); j++) {
                if (j < mbpniList.size() - 1) {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
                } else {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
                }
            }
        }
        if (materialBomParamNameValueStr != "") {
            return (materialBomParamNameValueStr);
        } else {
            return (null);
        }
    }

    @Transactional
    public void materialUpgraded(MaterialBomInfo materialBomInfo) {
        ExchangeUtils.convertBlank2Null(materialBomInfo);
        MaterialBomEntity mbe = ExchangeUtils.exchangeObject(materialBomInfo, MaterialBomEntity.class);

        MaterialBomInfo mbeTrim = new MaterialBomInfo();

        String factoryMaterialCodeTrim = null;
        String materialNameTrim = null;
        String originalMaterialTrim = null;
        if (mbe.getFactoryMaterialCode() != null) {
            factoryMaterialCodeTrim = mbe.getFactoryMaterialCode().replace(" ", "");
        }
        if (mbe.getMaterialName() != null) {
            materialNameTrim = mbe.getMaterialName().replace(" ", "");
        }
        if (mbe.getOriginalMaterial() != null) {
            originalMaterialTrim = mbe.getOriginalMaterial().replace(" ", "");
        }

        Integer drawingId = materialBomInfo.getDrawingId();
        Integer checkId = materialBomInfo.getCheckId();
        String drawingCode = null;
        if (drawingId != null) {
            ProductDrawingEntity pde = productDrawingManager.getEntityById(drawingId);
            drawingCode = pde.getDrawingCode();
        }
        if (materialBomInfo.getDynamicParameterName() != null) {
            String materialBomParamValueStr = "";
            for (int i = 0; i < materialBomInfo.getDynamicParameterName().size(); i++) {
                if (i == materialBomInfo.getDynamicParameterName().size() - 1) {
                    materialBomParamValueStr += materialBomInfo.getDynamicParameterName().get(i).get("materialParamNameValue");
                } else {
                    materialBomParamValueStr += materialBomInfo.getDynamicParameterName().get(i).get("materialParamNameValue") + ",";
                }
            }
            mbeTrim.setMaterialBomParamValueStr(materialBomParamValueStr.replace(" ", ""));
        }
        mbeTrim.setFactoryMaterialCode(factoryMaterialCodeTrim);
        mbeTrim.setMaterialName(materialNameTrim);
        mbeTrim.setOriginalMaterial(originalMaterialTrim);
        //把页面传过来的source标识转换成中文
        String source = "";
        if (mbe.getSource() != null) {
            if (Integer.parseInt(mbe.getSource()) == 0) {
                source = "自制";
            } else if (Integer.parseInt(mbe.getSource()) == 1) {
                source = "外购";
            } else {
                source = "自制/外购";
            }
        } else {
            source = "外购";
        }
        mbeTrim.setSource(source);
        mbeTrim.setStatus(STATUS_DEFAULT);
        mbeTrim.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        saveMaterial1(materialBomInfo);
    }

    @Transactional
    public void saveMaterial1(MaterialBomInfo materialBomInfo) {
        Integer oldId = materialBomInfo.getId();
        Integer versionNumOld = materialBomInfo.getVersionNum();
        String materialCode = materialBomInfo.getMaterialCode();
        //添加版本号
        if (versionNumOld == 0) {
            materialCode = materialCode + "_1";
        } else {
            Integer versionNumNew = versionNumOld + 1;
            String subMaterialCode = materialCode.substring(0, materialCode.lastIndexOf("_"));
            materialCode = subMaterialCode + "_" + versionNumNew;
        }
        materialBomInfo.setId(null);
        ExchangeUtils.convertBlank2Null(materialBomInfo);
        MaterialBomEntity mbe = ExchangeUtils.exchangeObject(materialBomInfo, MaterialBomEntity.class);
        //把页面传过来的source标识转换成中文
        String source = "";
        if (mbe.getSource() != null) {
            if (Integer.parseInt(mbe.getSource()) == 0) {
                source = "自制";
            } else if (Integer.parseInt(mbe.getSource()) == 1) {
                source = "外购";
            } else {
                source = "自制/外购";
            }
        } else {
            source = "外购";
        }
        mbe.setSource(source);
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        mbe.setMaterialBomCreated(createDate);
        //获取当前登陆的账号的名称，赋值给制作人
        String author = LoginFilter.getCurrentUser().getUserName();
        mbe.setAuthor(author);
        mbe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        //根据物料类型number获取物料类型名称
        ProductTypeEntity pte = new ProductTypeEntity();
        pte.setNumber(materialBomInfo.getNumber());
        pte.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<ProductTypeEntity> pteList = productTypeManager.getListBy(pte);
        if (pteList.size() != 0) {
            mbe.setType(pteList.get(0).getType());
        }
        mbe.setMaterialCode(materialCode);

        //版本号+1
        mbe.setVersionNum(versionNumOld + 1);
        //首先往部件表插入数据，并且返回部件id
        MaterialBomEntity materialBomEntity = manager.insertBy(mbe);
        if (materialBomEntity != null) {
            Integer materialId = materialBomEntity.getId();
            String pinYin = "";
            Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId()) + 1;
            //先判断名称中是否包含中文
            if (isContainChinese(mbe.getMaterialName()) == true) {
                //根据中文的首字母加id生成编号
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            } else {
                //根据英文的首字母加id生成编号
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            }
            //循环往部件-参数表插入数据
            if (materialBomInfo.getDynamicParameterName() != null && materialBomInfo.getDynamicParameterName().size() != 0) {
                List<Map<String, String>> dynamicParameterNameMapList = materialBomInfo.getDynamicParameterName();
                for (int i = 0; i < dynamicParameterNameMapList.size(); i++) {
                    Map<String, String> dynamicParameterNameMap = dynamicParameterNameMapList.get(i);
                    MaterialBomParamNameEntity materialBomParamNameInfo = new MaterialBomParamNameEntity();
                    materialBomParamNameInfo.setMaterialId(materialId);
                    materialBomParamNameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialBomParamNameInfo.setMaterialParamNameId(0);
                    materialBomParamNameInfo.setMaterialParamNameValue(dynamicParameterNameMap.get("materialParamNameValue"));
                    materialBomParamNameManager.insertBy(materialBomParamNameInfo);
                }
            }
            mbe.setMaterialBomParamValueStr(getParamValue(materialId));
            //往部件-验收标准中间表插入数据
            MaterialCheckEntity materialCheckInfo = new MaterialCheckEntity();
            if (materialBomInfo.getCheckId() != null && !"".equals(materialBomInfo.getCheckId())) {
                materialCheckInfo.setCheckId(materialBomInfo.getCheckId());
                materialCheckInfo.setMaterialId(materialId);
                materialCheckInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialCheckManager.insertBy(materialCheckInfo);
            }
            //往部件-图纸中间表插入数据
            MaterialDrawingEntity materialDrawingInfo = new MaterialDrawingEntity();
            if (materialBomInfo.getDrawingId() != null && !"".equals(materialBomInfo.getDrawingId())) {
                materialDrawingInfo.setDrawingId(materialBomInfo.getDrawingId());
                materialDrawingInfo.setMaterialId(materialId);
                materialDrawingInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialDrawingManager.insertBy(materialDrawingInfo);
                //根据图纸id查询图纸编号
                ProductDrawingEntity pde = productDrawingManager.getEntityById(materialBomInfo.getDrawingId());
                mbe.setDrawingCode(pde.getDrawingCode());
            }
            //往部件-部件中间表插入数据，关联父子关系
            if (materialBomInfo.getMapList() != null && materialBomInfo.getMapList().size() != 0) {
                List<Map<String, String>> mapList = materialBomInfo.getMapList();
                for (int i = 0; i < mapList.size(); i++) {
                    Map<String, String> map = mapList.get(i);
                    MaterialRelationEntity materialRelationInfo = new MaterialRelationEntity();
                    materialRelationInfo.setMaterialParentId(materialId);
                    materialRelationInfo.setMaterialChildId(Integer.parseInt(map.get("id")));
                    materialRelationInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    Float quantity = Float.parseFloat(map.get("quantity"));
                    materialRelationInfo.setQuantity(quantity);
                    materialRelationManager.insertBy(materialRelationInfo);
                }
            }
            //往部件-制程中间表添加数据
            List<JSONObject> processInfo = materialBomInfo.getProcessInfo();
            if (processInfo != null) {
                for (int i = 0; i < processInfo.size(); i++) {
                    JSONObject jsonObject = processInfo.get(i);
                    MaterialProcessEntity mp = new MaterialProcessEntity();
                    mp.setMaterialId(materialId);
//					mp.setJobGuide(jsonObject.getString("作业指导书"));
//					mp.setPeople(jsonObject.getInt("人数"));
//					mp.setProcessName(jsonObject.getString("制程名称"));
//					mp.setRatedHour(jsonObject.getDouble("额定工时"));
                    mp.setJsonParam(jsonObject.toString());
                    mp.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    mp.setCheckStatus(jsonObject.getInt("checkStatus"));
                    Float price = 0f;
                    if (jsonObject.getString("price") != "") {
                        price = Float.parseFloat(jsonObject.getString("price"));
                    }
                    mp.setProcessPrice(price);
                    MaterialProcessEntity materialProcessEntity = materialProcessManager.insertBy(mp);
                    //修改json串id为当前记录id
                    jsonObject.put("id", materialProcessEntity.getId());
                    materialProcessManager.updateByPrimaryKeySelective(materialProcessEntity);
                }
            }
            //升级之后状态改为待审核
            if(materialBomInfo.getAuditingStatus()==1){
                mbe.setAuditingStatus(IN_AUDITING);
                Map<String, Object> var = new HashMap<>();
                //手动插入流程参数
                var.put("materialName", mbe.getMaterialName());
                var.put("materialCode", mbe.getMaterialCode());
                tasksService.startWorkflow(this.workflowKey, materialId.toString(), var);
            }
            if (materialBomInfo.getAuditingStatus() == 0) {
                auditingPass(materialId);
            }
            //mbe.setAuditingStatus(STAY_AUDITING);
            manager.updateByPrimaryKeySelective(mbe);
            MaterialBomEntity mbe1 = new MaterialBomEntity();
            mbe1.setId(oldId);
            mbe1.setAuditingStatus(BLOCK_UP);
            manager.updateByPrimaryKeySelective(mbe1);
        }
    }

    @Transactional
    public void auditingPass(Integer materialId) {
        MaterialBomEntity mbe = manager.getEntityById(materialId);

        //往仓库新增一条数据 只需要一个物料id和公司id  其他数量初始化为0
        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
        mre.setMaterialId(materialId);
        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
        if (mreList.size() == 0) {
            mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            mre.setMaterialCode(mbe.getMaterialCode());
            mre.setMaterialName(mbe.getMaterialName());
            mre.setOriginalMaterial(mbe.getOriginalMaterial());
            mre.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
            mre.setDrawingCode(mbe.getDrawingCode());
            mre.setNumber(mbe.getType());
            mre.setMaterialSource(mbe.getSource());
            mre.setMaterialBomParamValueStr(getParamValue(mbe.getId()));
            materialRepertoryManager.insertBy(mre);
        }
        //添加到默认供应商列表
        SupplierEntity se = new SupplierEntity();
        se.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        se.setStatus(STATUS_DEFAULT);
        se.setSupplierFlag(0);
        List<SupplierEntity> seList = supplierManager.getListBy(se);
        if (seList.size() != 0) {
            se = seList.get(0);
            SupplierMaterialEntity sme = new SupplierMaterialEntity();
            sme.setMaterialId(materialId);
            List<SupplierMaterialEntity> smeList = supplierMaterialManager.getListBy(sme);
            if (smeList.size() == 0) {
                MaterialBomEntity mbEntity = manager.getEntityById(materialId);
                sme.setFactoryMaterialCode(mbEntity.getFactoryMaterialCode());
                sme.setMaterialBomParamValueStr(mbEntity.getMaterialBomParamValueStr());
                sme.setMaterialCode(mbEntity.getMaterialCode());
                sme.setMaterialName(mbEntity.getMaterialName());
                sme.setOriginalMaterial(mbEntity.getOriginalMaterial());
                sme.setType(mbEntity.getType());
                sme.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                sme.setStatus(STATUS_DEFAULT);
                sme.setSupplierId(se.getId());
                //价格、最小值、最小采购数默认
                sme.setPrice(1f);
                sme.setMinQty(1f);
                sme.setPurchaseDays("1");
                supplierMaterialManager.insertBy(sme);
            }
        }

        mbe.setAuditingPersonId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        mbe.setAuditingStatus(AUDITING_PASS);
        manager.updateByPrimaryKeySelective(mbe);

    }

    @Transactional
    public void upload(List<Object> list) {
        Integer maxMaterialId = 0;
        Integer processId = 0;
        List<Map<String, Integer>> maps = new ArrayList<>();
        if (list.size() != 0) {
            MaterialTemplate template = (MaterialTemplate) list.get(0);
            if (template.getLevel().equals("0")) {
                //先处理第一条
                MaterialBomEntity bomEntity = ExchangeUtils.exchangeObject(template, MaterialBomEntity.class);
                if (template.getProcessFormat() != null) {
                    String processFormat = template.getProcessFormat();
                    ProcessEntity processEntity = new ProcessEntity();
                    processEntity.setProcessName(processFormat);
                    processEntity.setStatus(STATUS_DEFAULT);
                    processEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProcessEntity> processEntityList = processManager.getListBy(processEntity);
                    if (processEntityList.size() != 0) {
                        processId = processEntityList.get(0).getId();
                        bomEntity.setProcessId(processId);
                    } else {
                        throw new CommonBusinessException("第1行数据中工艺在数据库中不存在");
                    }
                }
                String number = "";
                if (template.getType() != null) {
                    ProductTypeEntity typeEntity = new ProductTypeEntity();
                    typeEntity.setType(template.getType());
                    typeEntity.setStatus(STATUS_DEFAULT);
                    typeEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductTypeEntity> typeEntityList = productTypeManager.getListBy(typeEntity);
                    if (typeEntityList.size() != 0) {
                        number = typeEntityList.get(0).getNumber();
                        bomEntity.setNumber(number);
                    } else {
                        throw new CommonBusinessException("第1行数据中物料类型在数据库中不存在");
                    }
                } else {
                    throw new CommonBusinessException("第1行数据中物料类型不存在");
                }
                if (bomEntity.getMaterialName() != null) {
                    String pinYin = "";
                    Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId()) + 1;
                    if (isContainChinese(bomEntity.getMaterialName()) == true) {
                        pinYin = getPinYinHeadChar(bomEntity.getMaterialName()).toUpperCase();
                        //根据中文的首字母加id生成编号
                        bomEntity.setMaterialCode(bomEntity.getNumber() + pinYin + nextNumber);
                        bomEntity.setMaterialNumber(nextNumber);
                    } else {
                        pinYin = wordToUpperCase(bomEntity.getMaterialName());
                        //根据英文的首字母加id生成编号
                        bomEntity.setMaterialCode(bomEntity.getNumber() + pinYin + nextNumber);
                        bomEntity.setMaterialNumber(nextNumber);
                    }
                } else {
                    throw new CommonBusinessException("第1行数据中物料名称不存在");
                }
                Integer drawingId = 0;
                if (template.getDrawingCode() != null) {
                    ProductDrawingEntity drawingEntity = new ProductDrawingEntity();
                    drawingEntity.setDrawingCode(template.getDrawingCode());
                    drawingEntity.setStatus(STATUS_DEFAULT);
                    drawingEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductDrawingEntity> drawingEntityList = productDrawingManager.getListBy(drawingEntity);
                    if (drawingEntityList.size() != 0) {
                        drawingId = drawingEntityList.get(0).getId();
                    } else {
                        throw new CommonBusinessException("第1行数据中图号在数据库不存在");
                    }
                }
                Integer checkId = 0;
                if (template.getAcceptanceStandard() != null) {
                    ProductAcceptanceEntity acceptanceEntity = new ProductAcceptanceEntity();
                    acceptanceEntity.setName(template.getAcceptanceStandard());
                    acceptanceEntity.setStatus(STATUS_DEFAULT);
                    acceptanceEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductAcceptanceEntity> acceptanceEntityList = productAcceptanceManager.getListBy(acceptanceEntity);
                    if (acceptanceEntityList.size() != 0) {
                        checkId = acceptanceEntityList.get(0).getId();
                    } else {
                        throw new CommonBusinessException("第1行数据中验收标准在数据库不存在");
                    }
                }
                bomEntity.setAuthor(LoginFilter.getCurrentUser().getUserName());
                bomEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                MaterialBomEntity materialBomEntity = manager.insertBy(bomEntity);
                maxMaterialId = materialBomEntity.getId();
                if (drawingId != 0) {
                    // 往中间表插入数据
                    MaterialDrawingEntity drawingEntity = new MaterialDrawingEntity();
                    drawingEntity.setDrawingId(drawingId);
                    drawingEntity.setMaterialId(materialBomEntity.getId());
                    drawingEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialDrawingManager.insertBy(drawingEntity);
                }
                if (checkId != 0) {
                    // 往中间表插入数据
                    MaterialCheckEntity checkEntity = new MaterialCheckEntity();
                    checkEntity.setCheckId(checkId);
                    checkEntity.setMaterialId(materialBomEntity.getId());
                    checkEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialCheckManager.insertBy(checkEntity);
                }
                Map<String, Integer> map = new HashMap<>();
                map.put(template.getLevel(), maxMaterialId);
                maps.add(map);
            } else {
                throw new CommonBusinessException("整机的层级需从0开始");
            }
            //把最外层的id先赋给一个中间值保存
            Integer topId = maxMaterialId;
            //接下来处理第二条开始的
            for (int i = 1; i < list.size(); i++) {
                MaterialTemplate materialTemplate = (MaterialTemplate) list.get(i);
                String level = materialTemplate.getLevel();
                char ch = level.charAt(level.length() - 1);
                //如果是字母 则是制程
                if (Character.isLetter(ch)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", "");
                    jsonObject.put("制程名称", materialTemplate.getProcessName());
                    jsonObject.put("人数", materialTemplate.getPeople());
                    jsonObject.put("额定工时", materialTemplate.getRatedTime());
                    jsonObject.put("作业指导书", materialTemplate.getJobGuide());
                    jsonObject.put("checkStatus", materialTemplate.getCheckFlag());
                    jsonObject.put("price", materialTemplate.getPrice());
                    jsonObject.put("工艺序号", materialTemplate.getProcessNumber());
                    String jsonParam = jsonObject.toString();
                    MaterialProcessEntity processEntity = new MaterialProcessEntity();
                    processEntity.setMaterialId(topId);
                    processEntity.setJsonParam(jsonParam);
                    processEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    processEntity.setCheckStatus(materialTemplate.getCheckFlag());
                    processEntity.setProcessPrice(materialTemplate.getPrice());
                    materialProcessManager.insertBy(processEntity);
                } else {
                    //否则是物料
                    MaterialBomEntity bomEntity = ExchangeUtils.exchangeObject(materialTemplate, MaterialBomEntity.class);
                    if (materialTemplate.getProcessFormat() != null) {
                        String processFormat = materialTemplate.getProcessFormat();
                        ProcessEntity processEntity = new ProcessEntity();
                        processEntity.setProcessName(processFormat);
                        processEntity.setStatus(STATUS_DEFAULT);
                        processEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<ProcessEntity> processEntityList = processManager.getListBy(processEntity);
                        if (processEntityList.size() != 0) {
                            processId = processEntityList.get(0).getId();
                            bomEntity.setProcessId(processId);
                        } else {
                            throw new CommonBusinessException("第" + (i + 1) + "行数据中工艺在数据库中不存在");
                        }
                    }
                    String number = "";
                    if (materialTemplate.getType() != null) {
                        ProductTypeEntity typeEntity = new ProductTypeEntity();
                        typeEntity.setType(materialTemplate.getType());
                        typeEntity.setStatus(STATUS_DEFAULT);
                        typeEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<ProductTypeEntity> typeEntityList = productTypeManager.getListBy(typeEntity);
                        if (typeEntityList.size() != 0) {
                            number = typeEntityList.get(0).getNumber();
                            bomEntity.setNumber(number);
                        } else {
                            throw new CommonBusinessException("第" + (i + 1) + "行数据中物料类型在数据库中不存在");
                        }
                    } else {
                        throw new CommonBusinessException("第" + (i + 1) + "行数据中物料类型不存在");
                    }
                    if (bomEntity.getMaterialName() != null) {
                        String pinYin = "";
                        Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId()) + 1;
                        if (isContainChinese(bomEntity.getMaterialName()) == true) {
                            pinYin = getPinYinHeadChar(bomEntity.getMaterialName()).toUpperCase();
                            //根据中文的首字母加id生成编号
                            bomEntity.setMaterialCode(bomEntity.getNumber() + pinYin + nextNumber);
                            bomEntity.setMaterialNumber(nextNumber);
                        } else {
                            pinYin = wordToUpperCase(bomEntity.getMaterialName());
                            //根据英文的首字母加id生成编号
                            bomEntity.setMaterialCode(bomEntity.getNumber() + pinYin + nextNumber);
                            bomEntity.setMaterialNumber(nextNumber);
                        }
                    } else {
                        throw new CommonBusinessException("第" + (i + 1) + "行数据中物料名称不存在");
                    }
                    Integer drawingId = 0;
                    if (materialTemplate.getDrawingCode() != null) {
                        ProductDrawingEntity drawingEntity = new ProductDrawingEntity();
                        drawingEntity.setDrawingCode(materialTemplate.getDrawingCode());
                        drawingEntity.setStatus(STATUS_DEFAULT);
                        drawingEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<ProductDrawingEntity> drawingEntityList = productDrawingManager.getListBy(drawingEntity);
                        if (drawingEntityList.size() != 0) {
                            drawingId = drawingEntityList.get(0).getId();
                        } else {
                            throw new CommonBusinessException("第" + (i + 1) + "行数据中图号在数据库不存在");
                        }
                    }
                    Integer checkId = 0;
                    if (materialTemplate.getAcceptanceStandard() != null) {
                        ProductAcceptanceEntity acceptanceEntity = new ProductAcceptanceEntity();
                        acceptanceEntity.setName(materialTemplate.getAcceptanceStandard());
                        acceptanceEntity.setStatus(STATUS_DEFAULT);
                        acceptanceEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<ProductAcceptanceEntity> acceptanceEntityList = productAcceptanceManager.getListBy(acceptanceEntity);
                        if (acceptanceEntityList.size() != 0) {
                            checkId = acceptanceEntityList.get(0).getId();
                        } else {
                            throw new CommonBusinessException("第" + (i + 1) + "行数据中验收标准在数据库不存在");
                        }
                    }
                    bomEntity.setAuthor(LoginFilter.getCurrentUser().getUserName());
                    bomEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    MaterialBomEntity materialBomEntity = manager.insertBy(bomEntity);
                    if (drawingId != 0) {
                        // 往中间表插入数据
                        MaterialDrawingEntity drawingEntity = new MaterialDrawingEntity();
                        drawingEntity.setDrawingId(drawingId);
                        drawingEntity.setMaterialId(materialBomEntity.getId());
                        drawingEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        materialDrawingManager.insertBy(drawingEntity);
                    }
                    if (checkId != 0) {
                        // 往中间表插入数据
                        MaterialCheckEntity checkEntity = new MaterialCheckEntity();
                        checkEntity.setCheckId(checkId);
                        checkEntity.setMaterialId(materialBomEntity.getId());
                        checkEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        materialCheckManager.insertBy(checkEntity);
                    }
                    topId = materialBomEntity.getId();
                    Map<String, Integer> map = new HashMap<>();
                    map.put(materialTemplate.getLevel(), materialBomEntity.getId());
                    maps.add(map);
                    //把最后一个-后面的全部去掉
                    String str = level;
                    str = str.substring(0, str.lastIndexOf("-"));
                    Integer parentMaterialId = 0;
                    //循环获取到maplist里面的key和截取的字符串对比
                    for (Map<String, Integer> map1 : maps) {
                        Set<Map.Entry<String, Integer>> entrySet = map1.entrySet();
                        for (Map.Entry<String, Integer> entry : entrySet) {
                            String key = entry.getKey();
                            if (key.equals(str)) {
                                parentMaterialId = entry.getValue();
                            }
                        }
                    }
                    // 往物料关联表中插入数据
                    MaterialRelationEntity relationEntity = new MaterialRelationEntity();
                    relationEntity.setMaterialParentId(parentMaterialId);
                    relationEntity.setMaterialChildId(topId);
                    relationEntity.setQuantity(materialTemplate.getQuantity());
                    relationEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    materialRelationManager.insertBy(relationEntity);
                    //直接通过
                    auditingPass(topId);
                }
            }
        }
    }

    /**
     * 停用、删除流程时所有待评审记录改为评审通过
     */
    public void suspendedWorkflow(){
        MaterialBomInfo searchMaterialBom = new MaterialBomInfo();
        searchMaterialBom.setAuditingStatus(STAY_AUDITING);
        List<MaterialBomInfo> stayReviewList = this.getListBy(searchMaterialBom);
        for(MaterialBomInfo mbi : stayReviewList){
            mbi.setAuditingStatus(AUDITING_PASS);
            this.saveOrUpdate(mbi);
        }
    }
    /**
     * 导入库存（导入后的物料状态为审批通过）文件
     *
     * @param list
     * @author leisx
     * @version 2019/04/08 16:53
     */
    public void uploadAuditingPass(List<Object> list) {
        int count = 1;
        if (!list.isEmpty()) {
            Integer supplierId = null;
            Integer drawingId = null;
            Integer repertoryId = null;
            MaterialBomEntity materialBomEntity = null;
            Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
            for (Object obj : list) {
                count++;
                MaterialBomAuditingPassTemplate template = (MaterialBomAuditingPassTemplate) obj;
                if (StringUtils.isBlank(template.getType())) {
                    throw new CommonBusinessException("第" + count + "行数据中物料类型不能为空");
                }
                if (StringUtils.isBlank(template.getMaterialName())) {
                    throw new CommonBusinessException("第" + count + "行数据中物料名称不能为空");
                }
                if (StringUtils.isBlank(template.getOriginalMaterial())) {
                    throw new CommonBusinessException("第" + count + "行数据中材质不能为空");
                }
                if (StringUtils.isBlank(template.getMaterialBomParamValueStr())) {
                    throw new CommonBusinessException("第" + count + "行数据中规格不能为空");
                }
                if (StringUtils.isBlank(template.getSupplierName())) {
                    throw new CommonBusinessException("第" + count + "行数据中供应商名称不能为空");
                }
                if (StringUtils.isBlank(template.getRepertoryName())) {
                    throw new CommonBusinessException("第" + count + "行数据中仓库名称不能为空");
                }
                if (StringUtils.isBlank(template.getSource())) {
                    throw new CommonBusinessException("第" + count + "行数据中来源不能为空");
                }
                if (template.getInventoryQty() != null && Float.compare(template.getInventoryQty(),0.0f)<0) {
                    throw new CommonBusinessException("第" + count + "行数据中数量不能小于0");
                }
                if (template.getPrice() != null  && template.getPrice().compareTo(BigDecimal.ZERO)<0 ) {
                    throw new CommonBusinessException("第" + count + "行数据中单价不能小于0");
                }
                if (template.getWeight() !=null  && template.getWeight()<0 ) {
                    throw new CommonBusinessException("第" + count + "行数据中单重不能小于0");
                }
                MaterialBomEntity bomEntity = ExchangeUtils.exchangeObject(template, MaterialBomEntity.class);
                ProductTypeEntity pte = new ProductTypeEntity();
                pte.setType(bomEntity.getType());
                pte.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<ProductTypeEntity> pteList = productTypeManager.getListBy(pte);
                if (pteList.size() != 0) {
                    bomEntity.setNumber(pteList.get(0).getNumber());
                } else {
                    throw new CommonBusinessException("第" + count + "行数据中物料类型在数据库不存在");
                }
                //根据图号获取图纸Id
                if (template.getDrawingCode() != null) {
                    ProductDrawingEntity drawingEntity = new ProductDrawingEntity();
                    drawingEntity.setDrawingCode(template.getDrawingCode());
                    drawingEntity.setStatus(STATUS_DEFAULT);
                    drawingEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductDrawingEntity> drawingEntityList = productDrawingManager.getListBy(drawingEntity);
                    if (drawingEntityList.size() != 0) {
                        drawingId = drawingEntityList.get(0).getId();
                    } else {
                        throw new CommonBusinessException("第" + count + "行数据中图号在数据库不存在");
                    }
                }
                if (template.getSupplierName() != null) {
                    SupplierEntity supplierEntity = new SupplierEntity();
                    supplierEntity.setCompanyId(companyId);
                    supplierEntity.setStatus(STATUS_DEFAULT);
                    supplierEntity.setSupplierName(template.getSupplierName());
                    List<SupplierEntity> supplierEntityList = supplierManager.getListBy(supplierEntity);
                    if (!supplierEntityList.isEmpty()) {
                        supplierId = supplierEntityList.get(0).getId();
                    } else {
                        throw new CommonBusinessException("第" + count  + "行数据中部分供应商不存在");
                    }
                }
                if (template.getRepertoryName() != null) {
                    RepertoryEntity repertoryEntity = new RepertoryEntity();
                    repertoryEntity.setCompanyId(companyId);
                    repertoryEntity.setStatus(STATUS_DEFAULT);
                    repertoryEntity.setRepertoryName(template.getRepertoryName());
                    List<RepertoryEntity> repertoryEntityList = repertoryManager.getListBy(repertoryEntity);
                    if(!repertoryEntityList.isEmpty()){
                        repertoryId = repertoryEntityList.get(0).getId();
                    } else {
                        throw new CommonBusinessException("第" + count  + "行数据中部分仓库名称不存在");
                    }
                }
                materialBomEntity = new MaterialBomEntity();
                materialBomEntity.setType(template.getType());
                materialBomEntity.setMaterialName(template.getMaterialName());
                materialBomEntity.setOriginalMaterial(template.getOriginalMaterial());
                materialBomEntity.setMaterialBomParamValueStr(template.getMaterialBomParamValueStr());
                List<MaterialBomEntity> materialBomEntityList = manager.getListBy(materialBomEntity);
                //物料列表中没有相同的物料情况下，给它创建为新物料
                if (materialBomEntityList.isEmpty()) {
                    //保存物料数据
                    this.saveImportMaterial(bomEntity, drawingId);
                    //保存供应商信息
                    this.saveImportSupplierMaterial(bomEntity, template, supplierId);
                    //保存仓库物料信息
                    this.saveImportMaterialRepertory(bomEntity, template, repertoryId);
                    //保存仓库物料明细信息
                    this.saveImportMaterialRepertoryDetail(bomEntity,template,supplierId,repertoryId,companyId);
                    //物料列表中有，物料类型、物料名称、材质、规格，相同的物料情况下，直接入库。
                } else {
                    MaterialBomEntity materialBomEntityData = materialBomEntityList.get(0);
                    //更新产品物料信息
                    materialBomEntityData=this.updateImportMaterial(materialBomEntityData,template);
                    this.updateMaterialRepetory(materialBomEntityData, template, supplierId, repertoryId);
                }
            }
        }
    }







    /**
     * 新增excel物料导入数据
     *
     * @param mbe
     * @param drawingId
     * @author leisx
     * @version 2019/04/08 16:53
     */
    @Transactional
    public MaterialBomEntity saveImportMaterial(MaterialBomEntity mbe, Integer drawingId) {
        Integer materialId = null;
        MaterialBomEntity resultmaterialBomEntity = null;
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        mbe.setMaterialCode("null");
        mbe.setMaterialBomCreated(createDate);
        //获取当前登陆的账号的名称，赋值给制作人
        String author = LoginFilter.getCurrentUser().getUserName();
        mbe.setAuthor(author);
        mbe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        //首先往部件表插入数据，并且返回部件id
        MaterialBomEntity materialBomEntity = manager.insertBy(mbe);
        if (materialBomEntity != null) {
            materialId = materialBomEntity.getId();
            //先判断名称中是否包含中文
            String pinYin = "";
            Integer nextNumber = manager.getMaxMaterialNumber(LoginFilter.getCurrentUser().getCompanyId()) + 1;
            if (isContainChinese(mbe.getMaterialName()) == true) {
                pinYin = getPinYinHeadChar(mbe.getMaterialName()).toUpperCase();
                //根据中文的首字母加id生成编号
                mbe.setMaterialCode(mbe.getNumber() + pinYin + nextNumber);
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            } else {
                pinYin = wordToUpperCase(mbe.getMaterialName());
                //根据英文的首字母加id生成编号
                mbe.setMaterialCode(mbe.getNumber() + pinYin + nextNumber);
                mbe.setId(materialId);
                mbe.setMaterialNumber(nextNumber);
                manager.updateByPrimaryKeySelective(mbe);
            }
            //参数表插入数据
            if (mbe.getMaterialBomParamValueStr() != null) {
                MaterialBomParamNameEntity materialBomParamNameInfo = new MaterialBomParamNameEntity();
                materialBomParamNameInfo.setMaterialId(materialId);
                materialBomParamNameInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                materialBomParamNameInfo.setMaterialParamNameId(0);
                materialBomParamNameInfo.setMaterialParamNameValue(mbe.getMaterialBomParamValueStr());
                materialBomParamNameManager.insertBy(materialBomParamNameInfo);
            }
            //往部件-图纸中间表插入数据
            MaterialDrawingEntity materialDrawingInfo = new MaterialDrawingEntity();
            materialDrawingInfo.setDrawingId(drawingId);
            materialDrawingInfo.setMaterialId(materialId);
            materialDrawingInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            materialDrawingManager.insertBy(materialDrawingInfo);
            mbe.setId(materialId);
            mbe.setAuditingStatus(AUDITING_PASS);
            resultmaterialBomEntity = manager.updateByPrimaryKeySelective(mbe);
        }
        return resultmaterialBomEntity;
    }

    /**
     * 新增excel供应商-物料导入数据
     *
     * @param mbe
     * @param template
     * @param supplierId
     * @author leisx
     * @version 2019/04/08 16:53
     */
    @Transactional
    public SupplierMaterialEntity saveImportSupplierMaterial(MaterialBomEntity mbe, MaterialBomAuditingPassTemplate template, Integer supplierId) {
        float price= template.getPrice()== null?  0.0f :template.getPrice().floatValue();
        double weight= template.getWeight()== null?  0.0 :template.getWeight();
        SupplierMaterialEntity supplierMaterialEntityResult = null;
        Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
        SupplierMaterialEntity supplierMaterialEntity = new SupplierMaterialEntity();
        supplierMaterialEntity.setCompanyId(companyId);
        supplierMaterialEntity.setStatus(STATUS_DEFAULT);
        supplierMaterialEntity.setMaterialId(mbe.getId());
        supplierMaterialEntity.setSupplierId(supplierId);
        supplierMaterialEntity.setMaterialCode(mbe.getMaterialCode());
        supplierMaterialEntity.setFactoryMaterialCode(template.getFactoryMaterialCode());
        supplierMaterialEntity.setMaterialName(template.getMaterialName());
        supplierMaterialEntity.setOriginalMaterial(template.getOriginalMaterial());
        supplierMaterialEntity.setType(template.getType());
        supplierMaterialEntity.setMaterialBomParamValueStr(template.getMaterialBomParamValueStr());
        supplierMaterialEntity.setPrice(price);
        supplierMaterialEntity.setWeight(weight);
        supplierMaterialEntityResult = supplierMaterialManager.insertBy(supplierMaterialEntity);
        return supplierMaterialEntityResult;
    }

    /**
     * 新增excel仓库物料导入数据
     *
     * @param mbe
     * @param repertoryId
     * @author leisx  @version 2019/04/08 16:53
     */
    @Transactional
    public MaterialRepertoryEntity saveImportMaterialRepertory(MaterialBomEntity mbe,  MaterialBomAuditingPassTemplate template, Integer repertoryId) {
        MaterialRepertoryEntity materialRepertoryEntityResult = null;
        Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
        MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
        materialRepertoryEntity.setCompanyId(companyId);
        materialRepertoryEntity.setStatus(STATUS_DEFAULT);
        materialRepertoryEntity.setMaterialId(mbe.getId());
        materialRepertoryEntity.setMaterialName(template.getMaterialName());
        materialRepertoryEntity.setMaterialCode(mbe.getMaterialCode());
        materialRepertoryEntity.setInventoryQty(template.getInventoryQty());
        materialRepertoryEntity.setOriginalMaterial(template.getOriginalMaterial());
        materialRepertoryEntity.setMaterialBomParamValueStr(template.getMaterialBomParamValueStr());
        materialRepertoryEntity.setFactoryMaterialCode(template.getFactoryMaterialCode());
        materialRepertoryEntity.setNumber(template.getType());
        materialRepertoryEntity.setDrawingCode(template.getDrawingCode());
        materialRepertoryEntity.setMaterialSource(template.getSource());
        materialRepertoryEntityResult = materialRepertoryManager.insertBy(materialRepertoryEntity);
        return materialRepertoryEntityResult;
    }

    /**
     * 新增供应商-物料明细数据
     * @param mbe
     * @param template
     * @param repertoryId
     * @return
     */
    @Transactional
    public MaterialRepertoryDetailEntity saveImportMaterialRepertoryDetail(MaterialBomEntity mbe,  MaterialBomAuditingPassTemplate template, Integer supplierId,Integer repertoryId, Integer companyId) {
        MaterialRepertoryDetailEntity materialRepertoryDetailEntity = new MaterialRepertoryDetailEntity();
        BigDecimal price= template.getPrice()== null? BigDecimal.valueOf(0) :template.getPrice();
        materialRepertoryDetailEntity.setCompanyId(companyId);
        materialRepertoryDetailEntity.setStatus(STATUS_DEFAULT);
        materialRepertoryDetailEntity.setRepertoryId(repertoryId);
        materialRepertoryDetailEntity.setMaterialId(mbe.getId());
        materialRepertoryDetailEntity.setInventoryQty(template.getInventoryQty());
        materialRepertoryDetailEntity.setSupplierId(supplierId);
        materialRepertoryDetailEntity.setMaterialCode(mbe.getMaterialCode());
        materialRepertoryDetailEntity.setMaterialName(template.getMaterialName());
        materialRepertoryDetailEntity.setOperatingPersonnelId(Integer.valueOf(LoginFilter.getCurrentUser().getEmployeId()));
        materialRepertoryDetailEntity.setMaterialSource(template.getSource());
        materialRepertoryDetailEntity.setPurchasePrice(price);
        materialRepertoryDetailEntity.setFactoryMaterialCode(template.getFactoryMaterialCode());
        materialRepertoryDetailEntity.setNumber(template.getType());
        materialRepertoryDetailEntity.setOriginalMaterial(template.getOriginalMaterial());
        materialRepertoryDetailEntity.setDrawingCode(template.getDrawingCode());
        materialRepertoryDetailEntity.setMaterialBomParamValueStr(template.getMaterialBomParamValueStr());
        materialRepertoryDetailEntity.setSource(template.getSource());
        materialRepertoryDetailEntity.setOperatStatus(IN_REPERTORY);
        return materialRepertoryDetailManager.insertBy(materialRepertoryDetailEntity);
    }


    /**
     * 更新excel仓库物料导入数据
     *
     * @param materialBomEntityData
     * @param template
     * @param supplierId
     * @param repertoryId
     * @version 2019/04/08 16:53
     */
    @Transactional
    public void updateMaterialRepetory(MaterialBomEntity materialBomEntityData, MaterialBomAuditingPassTemplate template, Integer supplierId, Integer repertoryId) {
        MaterialRepertoryEntity materialRepertoryEntityResult = null;
        Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
        Float inventoryQty = null;
        float importInventoryQty= template.getInventoryQty() == null ? 0.0f : template.getInventoryQty();
        MaterialRepertoryEntity materialRepertoryEntity = null;
        materialRepertoryEntity = new MaterialRepertoryEntity();
        materialRepertoryEntity.setCompanyId(companyId);
        materialRepertoryEntity.setStatus(STATUS_DEFAULT);
        materialRepertoryEntity.setMaterialId(materialBomEntityData.getId());
        List<MaterialRepertoryEntity> materialRepertoryEntityList = materialRepertoryManager.getListBy(materialRepertoryEntity);
        //在产品物料表中存在为已审核的状态，直接更新仓库物料表数据
        if (materialRepertoryEntityList != null && materialRepertoryEntityList.size() > 0) {
            //更新物料仓库信息
            materialRepertoryEntity = materialRepertoryEntityList.get(0);
            inventoryQty = materialRepertoryEntity.getInventoryQty();
            materialRepertoryEntity.setInventoryQty(inventoryQty +importInventoryQty);
            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
            //在产品物料表中存在为非已审核的状态，新增供应商物料信息、仓库物料信息、仓库物料明细信息
        }else{

            //保存供应商信息
            SupplierMaterialEntity supplierMaterialEntityResult=this.saveImportSupplierMaterial(materialBomEntityData, template, supplierId);
            //保存仓库物料信息
            this.saveImportMaterialRepertory(materialBomEntityData, template, repertoryId);
        }
        //保存仓库物料明细信息
        this.saveImportMaterialRepertoryDetail(materialBomEntityData,template,supplierId,repertoryId,companyId);

    }

    /**
     *   更新产品物料信息
     * @param materialBomEntityData
     * @param template
     */
    @Transactional
    public MaterialBomEntity updateImportMaterial(MaterialBomEntity materialBomEntityData,MaterialBomAuditingPassTemplate template) {
        materialBomEntityData.setAuditingStatus(AUDITING_PASS);
        materialBomEntityData.setFactoryMaterialCode(template.getFactoryMaterialCode());
        materialBomEntityData.setDrawingCode(template.getDrawingCode());
        materialBomEntityData.setMaterialUnit(template.getMaterialUnit());
        materialBomEntityData.setSource(template.getSource());
        return manager.updateByPrimaryKeySelective(materialBomEntityData);
    }





}
