package com.ecms.service;

import com.ecms.bean.PurchaseOrdersInfo;
import com.ecms.bean.PurchaseOrdersListInfo;
import com.ecms.bean.PurchaseOrdersStayDetailInfo;
import com.ecms.bean.PurchaseOrdersStayInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PurchaseOrdersStayDetailService extends BaseService<PurchaseOrdersStayDetailEntity, PurchaseOrdersStayDetailInfo> {
    @Resource
    private PurchaseOrdersStayDetailManager manager;
    @Resource
    private PurchaseOrdersService purchaseOrdersService;
    @Resource
    private PurchaseOrdersStayService purchaseOrdersStayService;
    @Resource
    private PurchaseOrdersListService purchaseOrdersListService;
    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Override
    protected BaseManager<PurchaseOrdersStayDetailEntity> getManager() {
        return manager;
    }

    public void turnback(Integer orderId) {
        PurchaseOrdersInfo searchPurchaseOrdersInfo = purchaseOrdersService.getModel(orderId);
        //撤销采购订单记录
        PurchaseOrdersInfo rollbackOrder = new PurchaseOrdersInfo();
        rollbackOrder.setId(searchPurchaseOrdersInfo.getId());
        rollbackOrder.setStatus(0);
        purchaseOrdersService.updateByPrimaryKeySelective(rollbackOrder);

        PurchaseOrdersStayDetailInfo stayDetailInfo = new PurchaseOrdersStayDetailInfo();
        stayDetailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        stayDetailInfo.setStatus(1);
        stayDetailInfo.setOrderId(orderId);
        for (PurchaseOrdersStayDetailInfo detailInfo : getListBy(stayDetailInfo)) {
            //修改待采购明细
            detailInfo.setDetailStatus("撤销");
            detailInfo.setStatus(0);
            updateByPrimaryKeySelective(detailInfo);
            //修改待采购记录
            PurchaseOrdersStayInfo stayInfo = purchaseOrdersStayService.getModel(detailInfo.getOrderStayId());
            stayInfo.setStayPurQty(stayInfo.getStayPurQty() + detailInfo.getOrderedNumber());
            stayInfo.setStatus(STATUS_DEFAULT);
            stayInfo.setPurchaseStayStatus(NO_ORDER);
            purchaseOrdersStayService.updateByPrimaryKeySelective(stayInfo);
            //修改采购记录明细
            PurchaseOrdersListInfo ordersListInfo = purchaseOrdersListService.getModel(detailInfo.getOrderListId());
            ordersListInfo.setStatus(0);
            purchaseOrdersListService.updateByPrimaryKeySelective(ordersListInfo);

            //修改库存收货记录
            MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
            mre.setMaterialId(ordersListInfo.getMaterialId());
            List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
            if (mreList.size() != 0) {
                mre.setId(mreList.get(0).getId());
                mre.setStayPurchaseQty(mreList.get(0).getStayPurchaseQty() + detailInfo.getOrderedNumber());
                if (mreList.get(0).getStayDeliveredQty() - detailInfo.getOrderedNumber() > 0) {
                    mre.setStayDeliveredQty(mreList.get(0).getStayDeliveredQty() - detailInfo.getOrderedNumber());
                } else {
                    mre.setStayDeliveredQty(0f);
                }
                //materialRepertoryManager.updateByPrimaryKeySelective(mre);
            }

        }
    }
}
