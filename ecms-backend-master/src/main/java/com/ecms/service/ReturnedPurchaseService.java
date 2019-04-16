package com.ecms.service;

import com.ecms.bean.ReturnedPurchaseInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

/**
 * @author Richie
 * @version 2018/12/25 14:13
 */
@Service
public class ReturnedPurchaseService extends BaseService<ReturnedPurchaseEntity,ReturnedPurchaseInfo> {
    @Resource
    private ReturnedPurchaseManager returnedPurchaseManager;

    @Resource
    private MaterialRepertoryDeliveryManager materialRepertoryDeliveryManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;

    @Override
    protected BaseManager<ReturnedPurchaseEntity> getManager() {
        return returnedPurchaseManager;
    }

    @Transactional
    public void saveReturnedPurchase(ReturnedPurchaseInfo returnedPurchaseInfo){
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        MaterialRepertoryDeliveryEntity mrde = new MaterialRepertoryDeliveryEntity();
        mrde.setPurchaseOrdersId(returnedPurchaseInfo.getPurchaseOrdersId());
        mrde.setMaterialBatch(returnedPurchaseInfo.getMaterialBatch());
        mrde.setMaterialId(returnedPurchaseInfo.getMaterialId());
        List<MaterialRepertoryDeliveryEntity> mrdeList = materialRepertoryDeliveryManager.getListBy(mrde);
        ReturnedPurchaseEntity rpe = new ReturnedPurchaseEntity();
        if (mrdeList.size()!=0){
            rpe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            rpe.setSupplierId(mrdeList.get(0).getSupplierId());
            rpe.setMaterialBatch(returnedPurchaseInfo.getMaterialBatch());
            rpe.setMaterialName(mrdeList.get(0).getMaterialName());
            rpe.setMaterialCode(mrdeList.get(0).getMaterialCode());
            rpe.setPurchaseOrdersId(returnedPurchaseInfo.getPurchaseOrdersId());
            rpe.setPurchaseOrdersListId(returnedPurchaseInfo.getPurchaseOrdersListId());
            rpe.setPurchaseCode(mrdeList.get(0).getPurchaseCode());
            rpe.setReturnedPurchaseQty(returnedPurchaseInfo.getReturnedPurchaseQty());
            rpe.setReturnedPurchaseReason(returnedPurchaseInfo.getReturnedPurchaseReason());
            rpe.setRepertoryId(mrdeList.get(0).getRepertoryId());
            rpe.setMaterialId(mrdeList.get(0).getMaterialId());
            rpe.setPerson(LoginFilter.getCurrentUser().getUserName());
            rpe.setOperationDate(createDate);
            returnedPurchaseManager.insertBy(rpe);
        }
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
        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
        mre.setMaterialId(returnedPurchaseInfo.getMaterialId());
        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
        if (mreList.size()!=0){
            inventoryQty = mreList.get(0).getInventoryQty();
            stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
            stayInboundQty = mreList.get(0).getStayInboundQty();
            orderNeedQty = mreList.get(0).getOrderNeedQty();
            stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
            //算出当前物料可用的数量
            Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-(returnedPurchaseInfo.getReturnedPurchaseQty()+orderNeedQty);
            //如果差值小于0  则需要生成待采购记录
            if (differenceQty<0){
                PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
                pose.setMaterialId(returnedPurchaseInfo.getMaterialId());
                pose.setStayPurQty(differenceQty);
                pose.setPurchaseReason("退货需重新采购");
                pose.setLatestPurDate(createDate);
                pose.setPurchaseStayStatus(0);
                pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                PurchaseOrdersStayEntity PurchaseOrdersStayEntity = purchaseOrdersStayManager.insertBy(pose);
                String purchaseStayCode = "Pur" +frontCompWithZore(PurchaseOrdersStayEntity.getId(),8);
                PurchaseOrdersStayEntity.setPurchaseStayCode(purchaseStayCode);
                purchaseOrdersStayManager.updateByPrimaryKeySelective(PurchaseOrdersStayEntity);
            }
        }
    }
}
