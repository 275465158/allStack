package com.ecms.service;

import com.ecms.bean.MaterialRepertoryDeliveryInfo;
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

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

/**
 * 仓库物料明细service
 * @author Richie
 * @version 2018/10/25 17:21
 */
@Service
public class MaterialRepertoryDeliveryService extends BaseService<MaterialRepertoryDeliveryEntity, MaterialRepertoryDeliveryInfo> {

    @Resource
    private MaterialRepertoryDeliveryManager materialRepertoryDeliveryManager;
    @Resource
    private PurchaseOrdersManager purchaseOrdersManager;
    @Resource
    private MaterialRepertoryManager materialRepertoryManager;
    @Resource
    private PurchaseOrdersListManager purchaseOrdersListManager;
    @Resource
    private MaterialBomManager materialBomManager;
    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

    @Override
    protected BaseManager<MaterialRepertoryDeliveryEntity> getManager() {
        return materialRepertoryDeliveryManager;
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return "number_delivery_";
    }

    @Transactional
    public void insertDelivery(MaterialRepertoryDeliveryInfo materialRepertoryDeliveryInfo,Integer deliveryNumber){
        ExchangeUtils.convertBlank2Null(materialRepertoryDeliveryInfo);
        MaterialRepertoryDeliveryEntity mrde = ExchangeUtils.exchangeObject(materialRepertoryDeliveryInfo,MaterialRepertoryDeliveryEntity.class);
        //往收货表里面插入数据
        MaterialBomEntity mbe = materialBomManager.getEntityById(mrde.getMaterialId());
        String numberValue = null;
        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
        mrde.setNumber(numberValue);
        mrde.setOriginalMaterial(mbe.getOriginalMaterial());
        mrde.setSource(mbe.getSource());
        mrde.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
        mrde.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
        mrde.setDrawingCode(mbe.getDrawingCode());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mrde.setDeliveryDate(sdf.format(date));
        mrde.setOperationDate(sdf.format(date));
        mrde.setOperatingPersonnelId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
        mrde.setDeliveryStatus(STATUS_DEFAULT);
        mrde.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        mrde.setPerson(LoginFilter.getCurrentUser().getUserName());
        mrde.setSupplierId(materialRepertoryDeliveryInfo.getSupplierId());
        mrde.setRepertoryId(materialRepertoryDeliveryInfo.getRepertoryId());
        mrde.setPurchaseOrdersId(materialRepertoryDeliveryInfo.getPurchaseOrdersId());
        mrde.setPurchaseOrdersListId(materialRepertoryDeliveryInfo.getPurchaseOrdersListId());
        mrde.setDeliveryNumber(deliveryNumber);
        MaterialRepertoryDeliveryEntity materialRepertoryDeliveryEntity = materialRepertoryDeliveryManager.insertBy(mrde);
        String deliveryCode = "Del" +frontCompWithZore(deliveryNumber,8);
        materialRepertoryDeliveryEntity.setDeliveryCode(deliveryCode);
        materialRepertoryDeliveryManager.updateByPrimaryKeySelective(materialRepertoryDeliveryEntity);
        Integer purchaseListId = materialRepertoryDeliveryInfo.getPurchaseOrdersListId();
        if (materialRepertoryDeliveryInfo.getInventoryQty()>=materialRepertoryDeliveryInfo.getOldDeliveryQty()){
            //修改采购单明细的数量
            PurchaseOrdersListEntity pole = purchaseOrdersListManager.getEntityById(purchaseListId);
            pole.setUnReceivedQty(0f);
            pole.setReceivedQty(pole.getReceivedQty()+materialRepertoryDeliveryInfo.getInventoryQty());
            pole.setStayPurQty(0f);
            pole.setReceivedStatus(2);
            purchaseOrdersListManager.updateByPrimaryKeySelective(pole);
        }else {
            //修改采购单明细的数量
            PurchaseOrdersListEntity pole = purchaseOrdersListManager.getEntityById(purchaseListId);
            pole.setUnReceivedQty(materialRepertoryDeliveryInfo.getOldDeliveryQty()-materialRepertoryDeliveryInfo.getInventoryQty());
            pole.setReceivedQty(pole.getReceivedQty()+materialRepertoryDeliveryInfo.getInventoryQty());
            pole.setStayPurQty(materialRepertoryDeliveryInfo.getOldDeliveryQty()-materialRepertoryDeliveryInfo.getInventoryQty());
            pole.setReceivedStatus(1);
            purchaseOrdersListManager.updateByPrimaryKeySelective(pole);
        }
        //判断这个采购单是否已经全部收货
        PurchaseOrdersListEntity pole = purchaseOrdersListManager.getEntityById(purchaseListId);
        Integer purchaseId = pole.getPurchaseOrdersId();
        PurchaseOrdersListEntity purchaseOrdersListEntity = new PurchaseOrdersListEntity();
        purchaseOrdersListEntity.setPurchaseOrdersId(purchaseId);
        List<PurchaseOrdersListEntity> poleList = purchaseOrdersListManager.getListBy(purchaseOrdersListEntity);
        if (poleList.size()!=0){
            Float unReceiverQty = 0f;
            for (int i=0;i<poleList.size();i++){
                unReceiverQty = unReceiverQty + poleList.get(i).getUnReceivedQty();
            }
            //如果未收货数量为0，则说明全部收货，状态改为待检测
            if (unReceiverQty==0){
                PurchaseOrdersEntity poe = new PurchaseOrdersEntity();
                poe.setId(purchaseId);
                poe.setInboundStatus(1);
                purchaseOrdersManager.updateByPrimaryKeySelective(poe);
            }
        }
        //修改仓库物料里面的待收货数量
        String materialCode = materialRepertoryDeliveryInfo.getMaterialCode();
        MaterialRepertoryEntity mri = new MaterialRepertoryEntity();
        mri.setMaterialCode(materialCode);
        List<MaterialRepertoryEntity> list = materialRepertoryManager.getListBy(mri);
        if (list.size()!=0){
            Float stayDeliveredQty = list.get(0).getStayDeliveredQty() - materialRepertoryDeliveryInfo.getInventoryQty();
            if (stayDeliveredQty<=0){
                mri.setStayDeliveredQty(0f);
            }else{
                mri.setStayDeliveredQty(stayDeliveredQty);
            }
            mri.setStayCheckQty(list.get(0).getStayCheckQty()+materialRepertoryDeliveryInfo.getInventoryQty());
            mri.setId(list.get(0).getId());
            materialRepertoryManager.updateByPrimaryKeySelective(mri);
        }
    }

}
