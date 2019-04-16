package com.ecms.service;

import com.ecms.bean.IncomingUnqualifiedInfo;
import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.MaterialRepertoryIncomingCheckInfo;
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

/**
 * 仓库物料明细service
 * @author Richie
 * @version 2018/10/25 17:21
 */
@Service
public class MaterialRepertoryIncomingCheckService extends BaseService<MaterialRepertoryIncomingCheckEntity, MaterialRepertoryIncomingCheckInfo> {

    @Resource
    private MaterialRepertoryIncomingCheckManager materialRepertoryIncomingCheckManager;

    @Resource
    private PurchaseOrdersManager purchaseOrdersManager;

    @Resource
    private MaterialRepertoryDeliveryManager materialRepertoryDeliveryManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private UnqualifiedTypeListManager unqualifiedTypeListManager;

    @Resource
    private CheckLogManager checkLogManager;

    @Resource
    private MaterialBomManager materialBomManager;

    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;

    @Resource
    private IncomingUnqualifiedManager incomingUnqualifiedManager;

    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

    @Override
    protected BaseManager<MaterialRepertoryIncomingCheckEntity> getManager() {
        return materialRepertoryIncomingCheckManager;
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_incoming_check_";
    }

    @Transactional
    public void saveIncomingCheckout(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo,Integer incomingCheckNumber,Integer incomingUnqualifiedNumber){
        Integer ids = materialRepertoryIncomingCheckInfo.getId();
        materialRepertoryIncomingCheckInfo.setId(null);
        MaterialRepertoryIncomingCheckEntity mrice = ExchangeUtils.exchangeObject(materialRepertoryIncomingCheckInfo, MaterialRepertoryIncomingCheckEntity.class);
        mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String checkDate = sdf.format(date);

        MaterialBomEntity mbe = materialBomManager.getEntityById(materialRepertoryIncomingCheckInfo.getMaterialId());
        String numberValue = null;
        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
        mrice.setNumber(numberValue);
        mrice.setOriginalMaterial(mbe.getOriginalMaterial());
        mrice.setSource(mbe.getSource());
        mrice.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
        mrice.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
        mrice.setDrawingCode(mbe.getDrawingCode());
        mrice.setMaterialBatch(materialRepertoryIncomingCheckInfo.getMaterialBatch());
        mrice.setMaterialName(materialRepertoryIncomingCheckInfo.getMaterialName());
        mrice.setMaterialId(materialRepertoryIncomingCheckInfo.getMaterialId());
        mrice.setMaterialCode(materialRepertoryIncomingCheckInfo.getMaterialCode());
        mrice.setRepertoryId(materialRepertoryIncomingCheckInfo.getRepertoryId());
        mrice.setInventoryQty(materialRepertoryIncomingCheckInfo.getInventoryQty());
        mrice.setDeliveryDate(materialRepertoryIncomingCheckInfo.getDeliveryDate());
        mrice.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        mrice.setPurchaseReason(materialRepertoryIncomingCheckInfo.getPurchaseReason());
        mrice.setPurchasePrice(materialRepertoryIncomingCheckInfo.getPurchasePrice());
        mrice.setUnqualifiedQty(materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
        mrice.setQualifiedQty(materialRepertoryIncomingCheckInfo.getQualifiedQty());
        mrice.setPurchaseCode(materialRepertoryIncomingCheckInfo.getPurchaseCode());
        mrice.setDeliveryCode(materialRepertoryIncomingCheckInfo.getDeliveryCode());
        mrice.setCheckDate(checkDate);
        mrice.setSupplierId(materialRepertoryIncomingCheckInfo.getSupplierId());
        mrice.setPurchaseOrdersListId(materialRepertoryIncomingCheckInfo.getPurchaseOrdersListId());
        mrice.setInboundStutas(STAY_INBOUND_STATUS);
        mrice.setMaterialSource(INCOMING_MATERIAL);
        if(materialRepertoryIncomingCheckInfo.getMaterialSource() != null && materialRepertoryIncomingCheckInfo.getMaterialSource() == 2){
            mrice.setMaterialSource(PRODUCTION_MATERIAL);
        }
        mrice.setPurchaseOrdersId(materialRepertoryIncomingCheckInfo.getPurchaseOrdersId());
        mrice.setQualifiedPercent(materialRepertoryIncomingCheckInfo.getQualifiedPercent()+"%");
        mrice.setCheckPercent(materialRepertoryIncomingCheckInfo.getCheckPercent()+"%");
        mrice.setCheckQty(materialRepertoryIncomingCheckInfo.getCheckQty());
        mrice.setFileUrl(materialRepertoryIncomingCheckInfo.getFileUrl());
        mrice.setUnqualifiedRemark(materialRepertoryIncomingCheckInfo.getUnqualifiedRemark());
        mrice.setIncomingCheckNumber(incomingCheckNumber);
        //更新检测单号
        MaterialRepertoryIncomingCheckEntity materialRepertoryIncomingCheckEntity = materialRepertoryIncomingCheckManager.insertBy(mrice);
        materialRepertoryIncomingCheckEntity.setCheckNumber("IQC"+frontCompWithZore(incomingCheckNumber,8));
        materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(materialRepertoryIncomingCheckEntity);
        // 不合格类型
        List<String> unqualifiedTypeFormList = materialRepertoryIncomingCheckInfo.getUnqualifiedTypeFormList();
        if (unqualifiedTypeFormList!=null){
            for (int i=0;i<unqualifiedTypeFormList.size();i++){
                UnqualifiedTypeListEntity utle = new UnqualifiedTypeListEntity();
                utle.setCheckId(materialRepertoryIncomingCheckEntity.getId());
                utle.setCheckSource(0);
                utle.setTypeName(unqualifiedTypeFormList.get(i));
                utle.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                unqualifiedTypeListManager.insertBy(utle);
            }
        }
        if (materialRepertoryIncomingCheckInfo.getTemp() != null){
            List<String> temp = materialRepertoryIncomingCheckInfo.getTemp();
            for (int i=0;i<temp.size();i++){
                CheckLogEntity cle = new CheckLogEntity();
                cle.setCheckFlag(0);
                cle.setCheckId(materialRepertoryIncomingCheckEntity.getId());
                cle.setCheckResult(temp.get(i));
                cle.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                checkLogManager.insertBy(cle);
            }
        }
        PurchaseOrdersEntity purchaseOrdersEntity = new PurchaseOrdersEntity();
        purchaseOrdersEntity.setInboundStatus(STAY_INBOUND_STATUS);
        purchaseOrdersEntity.setId(materialRepertoryIncomingCheckInfo.getPurchaseOrdersId());
        purchaseOrdersManager.updateByPrimaryKeySelective(purchaseOrdersEntity);
        MaterialRepertoryDeliveryEntity mrde = new MaterialRepertoryDeliveryEntity();
        mrde.setId(ids);
        mrde.setDeliveryStatus(STATUS_INVALID);
        materialRepertoryDeliveryManager.updateByPrimaryKeySelective(mrde);
        MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
        materialRepertoryEntity.setMaterialId(materialRepertoryIncomingCheckInfo.getMaterialId());
        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(materialRepertoryEntity);
        if(mreList.size() != 0){
            Integer id = mreList.get(0).getId();
            materialRepertoryEntity.setId(id);
            materialRepertoryEntity.setStayInboundQty(materialRepertoryIncomingCheckInfo.getQualifiedQty()+mreList.get(0).getStayInboundQty());
            Float stayCheckQty = mreList.get(0).getStayCheckQty();
            if(stayCheckQty == null){
                stayCheckQty = 0f;
            }
            Float inventoryQty = materialRepertoryIncomingCheckInfo.getInventoryQty();
            if(inventoryQty == null){
                inventoryQty = 0f;
            }
            materialRepertoryEntity.setStayCheckQty(stayCheckQty-inventoryQty);
            materialRepertoryEntity.setUnqualifiedQty(mreList.get(0).getUnqualifiedQty()+materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
        }
        //如果有不合格品则生成一张不合格品处理单
        if (materialRepertoryIncomingCheckInfo.getUnqualifiedQty()>0.0){
            IncomingUnqualifiedEntity iue = new IncomingUnqualifiedEntity();
            iue.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            iue.setPurchaseOrdersId(materialRepertoryIncomingCheckInfo.getPurchaseOrdersId());
            iue.setMaterialName(materialRepertoryIncomingCheckInfo.getMaterialName());
            iue.setSupplierName(materialRepertoryIncomingCheckInfo.getSupplierName());
            iue.setPurchaseReason(materialRepertoryIncomingCheckInfo.getPurchaseReason());
            iue.setPurchaseCode(materialRepertoryIncomingCheckInfo.getPurchaseCode());
            iue.setMaterialId(materialRepertoryIncomingCheckInfo.getMaterialId());
            iue.setUnqualifiedQty(materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
            iue.setCheckId(materialRepertoryIncomingCheckEntity.getId());
            iue.setIncomingUnqualifiedNumber(incomingUnqualifiedNumber);
            iue.setPurchaseOrdersListId(materialRepertoryIncomingCheckInfo.getPurchaseOrdersListId());
            String unqualifiedReviewNo = "IR"+frontCompWithZore(incomingUnqualifiedNumber,8);
            iue.setUnqualifiedReviewNo(unqualifiedReviewNo);
            incomingUnqualifiedManager.insertBy(iue);
        }
    }

    @Transactional
    public void concessionUse(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        /*String purchaseCode = materialRepertoryIncomingCheckInfo.getPurchaseCode();
        Integer materialId = materialRepertoryIncomingCheckInfo.getMaterialId();
        MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
        mrice.setMaterialId(materialId);
        mrice.setPurchaseCode(purchaseCode);
        List<MaterialRepertoryIncomingCheckEntity> mriciList = materialRepertoryIncomingCheckManager.getListBy(mrice);
        if (mriciList.size()!=0){
            MaterialRepertoryIncomingCheckEntity mriceOne = mriciList.get(0);
            //如果合格品还是待入库，则把不合格品直接加到合格品上
            if (mriceOne.getInboundStutas()==2){
                Integer id = mriceOne.getId();
                mriceOne.setQualifiedQty(mriceOne.getQualifiedQty()+materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
                mriceOne.setId(id);
                materialRepertoryIncomingCheckManager.updateByPrimaryKeySelective(mrice);
            }else{
                //如果已经入库的则直接加到库存上
                MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                materialRepertoryEntity.setMaterialId(materialRepertoryIncomingCheckInfo.getMaterialId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(materialRepertoryEntity);
                if(mreList.size() != 0){
                    Integer id = mreList.get(0).getId();
                    materialRepertoryEntity.setId(id);
                    materialRepertoryEntity.setInventoryQty(materialRepertoryEntity.getInventoryQty()+materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
                    materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                }
            }
        }*/
    }

    @Transactional
    public void materialChange(MaterialRepertoryIncomingCheckInfo materialRepertoryIncomingCheckInfo){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createDate = sdf.format(date);
        //获取转换的物料id
        Integer newMaterialId = materialRepertoryIncomingCheckInfo.getNewMaterialId();
        MaterialBomEntity mbe = materialBomManager.getEntityById(newMaterialId);
        MaterialRepertoryIncomingCheckEntity mrice = new MaterialRepertoryIncomingCheckEntity();
        //获取原来物料待入库的数据
        Integer oldMaterialId = materialRepertoryIncomingCheckInfo.getMaterialId();
        String purchaseCode = materialRepertoryIncomingCheckInfo.getPurchaseCode();
        MaterialRepertoryIncomingCheckEntity oldMrice = new MaterialRepertoryIncomingCheckEntity();
        oldMrice.setMaterialId(oldMaterialId);
        oldMrice.setPurchaseCode(purchaseCode);
        List<MaterialRepertoryIncomingCheckEntity> oldMriceList = materialRepertoryIncomingCheckManager.getListBy(oldMrice);
        if (oldMriceList.size()!=0){
            oldMrice = oldMriceList.get(0);
            mrice.setRepertoryId(oldMrice.getRepertoryId());
            mrice.setMaterialId(newMaterialId);
            mrice.setInventoryQty(materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
            mrice.setSupplierId(oldMrice.getSupplierId());
            mrice.setMaterialBatch(createDate);
            mrice.setMaterialCode(mbe.getMaterialCode());
            mrice.setMaterialName(mbe.getMaterialName());
            mrice.setDeliveryDate(oldMrice.getDeliveryDate());
            mrice.setOperatingPersonnelId(oldMrice.getOperatingPersonnelId());
            mrice.setPurchaseReason("不合格物料"+materialRepertoryIncomingCheckInfo.getMaterialCode()+"转换成"+mbe.getMaterialCode());
            mrice.setPurchasePrice(oldMrice.getPurchasePrice());
            mrice.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            mrice.setUnqualifiedQty(0f);
            //原来物料的不合格数变成新物料的合格数
            mrice.setQualifiedQty(materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
            mrice.setPurchaseCode(oldMrice.getPurchaseCode());
            mrice.setInboundStutas(STAY_INBOUND_STATUS);
            mrice.setCheckDate(oldMrice.getCheckDate());
            mrice.setPurchaseOrdersId(oldMrice.getPurchaseOrdersId());
            mrice.setMaterialSource(oldMrice.getMaterialSource());
            mrice.setQualifiedPercent("100%");
            mrice.setCheckPercent("100%");
            mrice.setCheckQty(materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
            mrice.setCheckNumber(oldMrice.getCheckNumber());
            mrice.setDeliveryCode(oldMrice.getDeliveryCode());
            //把转换的新物料变成待入库
            materialRepertoryIncomingCheckManager.insertBy(mrice);
            //修改新物料的待入库总数
            MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
            materialRepertoryEntity.setMaterialId(materialRepertoryIncomingCheckInfo.getMaterialId());
            List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(materialRepertoryEntity);
            if(mreList.size() != 0){
                Integer id = mreList.get(0).getId();
                materialRepertoryEntity.setId(id);
                materialRepertoryEntity.setStayInboundQty(mreList.get(0).getStayInboundQty()+materialRepertoryIncomingCheckInfo.getUnqualifiedQty());
                materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
            }
        }
        //计算旧物料是否需要重新采购
        //总库存数
        Float inventoryQty = 0.00f;
        //订单需求数
        Float orderNeedQty = 0.00f;
        //待入库总数
        Float stayInboundQty = 0.00f;
        //待收货总数
        Float stayDeliveredQty = 0.00f;
        //待采购总数
        Float stayPurchaseQty = 0.00f;
        //根据物料id去仓库表查询数据
        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
        mre.setMaterialId(oldMaterialId);
        List<MaterialRepertoryEntity> mreList =  materialRepertoryManager.getListBy(mre);
        if (mreList.size()!=0){
            inventoryQty = mreList.get(0).getInventoryQty();
            stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
            stayInboundQty = mreList.get(0).getStayInboundQty();
            orderNeedQty = mreList.get(0).getOrderNeedQty();
            stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
            //算出当前物料可用的数量
            Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-orderNeedQty;
            if (differenceQty<0){
                //则需要重新生成采购单
                PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
                pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                pose.setPurchaseStayStatus(0);
                pose.setStayPurQty(-differenceQty);
                pose.setLatestPurDate(createDate);
                pose.setPurchaseReason("物料转换需重新采购旧物料");
                pose.setOrderId(0);
                PurchaseOrdersStayEntity purchaseOrdersStayEntity = purchaseOrdersStayManager.insertBy(pose);
                String purchaseStayCode = "Pur" +frontCompWithZore(purchaseOrdersStayEntity.getId(),8);
                purchaseOrdersStayEntity.setPurchaseStayCode(purchaseStayCode);
                purchaseOrdersStayManager.updateByPrimaryKeySelective(purchaseOrdersStayEntity);
                //最后更新仓库带采购数
                mre.setId(mreList.get(0).getId());
                mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty()+(-differenceQty));
                materialRepertoryManager.updateByPrimaryKeySelective(mre);
            }
        }
    }

}
