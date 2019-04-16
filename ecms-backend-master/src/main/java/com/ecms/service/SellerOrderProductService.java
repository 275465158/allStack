package com.ecms.service;

import com.ecms.bean.SellerOrderProductInfo;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerOrderProductService extends BaseService<SellerOrderProductEntity, SellerOrderProductInfo> {

    @Resource
    private SellerOrderProductManager sellerOrderProductManager;

    @Resource
    private MaterialBomManager materialBomManager;

    @Resource
    private SellerOrderLogManager sellerOrderLogManager;

    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private ProductionPlanManager productionPlanManager;

    @Resource
    private SellerOrderManager sellerOrderManager;

    @Override
    protected BaseManager<SellerOrderProductEntity> getManager() {
        return sellerOrderProductManager;
    }

    @Transactional
    public void editNewProduct(Integer orderId,Integer materialId,Integer sequenceNumber){
        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
        SellerOrderProductEntity sope = new SellerOrderProductEntity();
        sope.setOrderId(orderId);
        sope.setSequenceNumber(sequenceNumber);
        sope.setStatus(1);
        List<SellerOrderProductEntity> sopeList = sellerOrderProductManager.getListBy(sope);
        if (sopeList.size()!=0){
            SellerOrderProductEntity sellerOrderProductEntity = sopeList.get(0);
            sellerOrderProductEntity.setProductId(materialId);
            sellerOrderProductEntity.setProductCode(mbe.getMaterialCode());
            sellerOrderProductEntity.setProductName(mbe.getMaterialName());
            sellerOrderProductEntity.setUnit(mbe.getMaterialUnit());
            sellerOrderProductManager.updateByPrimaryKeySelective(sellerOrderProductEntity);

            if (sopeList.get(0).getMaterialStatus() == 2){
                //修改状态为2的产品需要重新计算
                PurchaseOrdersStayEntity stayEntity = new PurchaseOrdersStayEntity();
                stayEntity.setOrderId(orderId);
                stayEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                stayEntity.setStatus(STATUS_DEFAULT);
                stayEntity.setSellerOrderProductId(sopeList.get(0).getId());
                List<PurchaseOrdersStayEntity> stayEntityList = purchaseOrdersStayManager.getListBy(stayEntity);
                if (stayEntityList.size()!=0){
                    for (int i=0;i<stayEntityList.size();i++){
                        MaterialRepertoryEntity repertoryEntity = new MaterialRepertoryEntity();
                        repertoryEntity.setMaterialId(stayEntityList.get(i).getMaterialId());
                        repertoryEntity.setStatus(STATUS_DEFAULT);
                        repertoryEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<MaterialRepertoryEntity> repertoryEntityList = materialRepertoryManager.getListBy(repertoryEntity);
                        if (repertoryEntityList.size()!=0){
                            //减掉带采购数和订单需求数
                            MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                            materialRepertoryEntity.setOrderNeedQty(repertoryEntityList.get(0).getOrderNeedQty()-stayEntityList.get(i).getOrderNeedQty());
                            materialRepertoryEntity.setStayPurchaseQty(repertoryEntityList.get(0).getStayPurchaseQty()-stayEntityList.get(i).getStayPurQty());
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                        }
                        //把当前这条采购单状态变成0
                        PurchaseOrdersStayEntity purchaseOrdersStayEntity = new PurchaseOrdersStayEntity();
                        purchaseOrdersStayEntity.setId(stayEntityList.get(i).getId());
                        purchaseOrdersStayEntity.setStatus(STATUS_INVALID);
                        purchaseOrdersStayManager.updateByPrimaryKeySelective(purchaseOrdersStayEntity);
                    }
                }
                //计算生产单
                ProductionPlanEntity planEntity = new ProductionPlanEntity();
                planEntity.setSellerOrderProductId(sopeList.get(0).getId());
                planEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                planEntity.setStatus(STATUS_DEFAULT);
                List<ProductionPlanEntity> planEntityList = productionPlanManager.getListBy(planEntity);
                if (planEntityList.size()!=0){
                    for(ProductionPlanEntity ppEntity : planEntityList){
                        if(ppEntity.getIssueQty() == 0){
                            MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                            mre.setMaterialId(ppEntity.getMaterialId());
                            mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            mre.setStatus(STATUS_DEFAULT);
                            List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                            if(mreList.size() != 0){
                                mre = mreList.get(0);
                                //减掉仓库总表里面的待生产数
                                mre.setStayProductionQty(mre.getStayProductionQty()-ppEntity.getMaterialNum());
                                //减掉仓库总表里面的订单需求数
                                mre.setOrderNeedQty(mre.getOrderNeedQty()-ppEntity.getOrderNeedQty());
                                materialRepertoryManager.updateByPrimaryKeySelective(mre);
                            }
                        }
                        //把未发料的生产单状态变成0
                        ProductionPlanEntity productionPlanEntity = new ProductionPlanEntity();
                        productionPlanEntity.setId(ppEntity.getId());
                        productionPlanEntity.setStatus(STATUS_INVALID);
                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
                    }
                }
            }
            //把当前记录产品状态变成待下发
            sope.setMaterialStatus(STATUS_DEFAULT);
            sope.setId(sopeList.get(0).getId());
            sellerOrderProductManager.updateByPrimaryKeySelective(sope);
            //把销售订单状态变成bom制作中
            SellerOrderEntity orderEntity = new SellerOrderEntity();
            orderEntity.setId(orderId);
            orderEntity.setSellerOrderStatus(STATUS_INVALID);
            sellerOrderManager.updateByPrimaryKeySelective(orderEntity);

            //记录
            SellerOrderLogEntity sole = new SellerOrderLogEntity();
            sole.setOrderId(orderId);
            sole.setOperator(LoginFilter.getCurrentUser().getUserName());
            sole.setOperation("选择产品");
            sole.setContent("编号:"+sequenceNumber + "选择了产品:" + mbe.getMaterialCode());
            sellerOrderLogManager.insertBy(sole);
        }
    }

    public List<Integer> getListByStatus(Integer productId){
        return sellerOrderProductManager.getListByStatus(productId);
    }
}
