package com.ecms.service;

import com.ecms.bean.SellerOrderIndex;
import com.ecms.bean.SellerOrderInfo;
import com.ecms.bean.SellerOrderProductInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.common.SellerOrderProgress;
import com.ecms.dal.entity.*;
import com.ecms.manager.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.FileUtils;
import com.ecms.utils.MoneyUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ecms.web.controller.ProductionPlanController.frontCompWithZore;

@Service
public class SellerOrderService extends BaseService<SellerOrderEntity, SellerOrderInfo> {

    @Value("${env}")
    private String env;

    @Resource
    private SellerOrderManager sellerOrderManager;

    @Resource
    private PurchaseOrdersStayManager purchaseOrdersStayManager;

    @Resource
    private SellerOrderProductManager sellerOrderProductManager;

    @Resource
    private MaterialRepertoryManager materialRepertoryManager;

    @Resource
    private MaterialBomManager materialBomManager;

    @Resource
    private MaterialRelationManager materialRelationManager;

    @Resource
    private ProductionPlanService productionPlanService;

    @Resource
    private ProductionPlanManager productionPlanManager;

    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

    @Resource
    private SellerOrderLogManager sellerOrderLogManager;

    @Resource
    private PurchaseOrdersStayService purchaseOrdersStayService;

    //计划单
    private Integer PLAN_CREATE = 1;

    //手工添加
    private Integer HAND_CREATE = 2;

    @Override
    protected BaseManager<SellerOrderEntity> getManager() {
        return sellerOrderManager;
    }

    @Override
    protected void setCustomProperties(List<SellerOrderInfo> list) {
        for( SellerOrderInfo info : list) {
            info.setProgressDisplay(SellerOrderProgress.getNameById(info.getProgress()));
            info.setTotalMoneyDisplay(MoneyUtils.changeF2Y(info.getTotalMoney()));
        }
    }

    @Override
    public String uploadFileSimple(MultipartFile file) {
        return FileUtils.uploadFile(file, env, "seller-file/");
    }

    public Pager<SellerOrderInfo> getList(int pageNum, int pageSize, SellerOrderInfo info) {
        Example.Criteria criteria = getDefaultCriteria();
        if (info.getOrderCode() != null) {
            criteria.andEqualTo("orderCode", info.getOrderCode());
        }
        if (info.getContractId() != null) {
            criteria.andEqualTo("contractId", info.getContractId());
        }
        if (info.getCustomerName() != null) {
            criteria.andEqualTo("customerName", info.getCustomerName());
        }
        if (info.getProgress() != null) {
            criteria.andEqualTo("progress", info.getProgress());
        }
        if (info.getDateParam() != null && info.getDateParam().size() == 2) {
            criteria.andBetween("createDate", info.getDateParam().get(0), info.getDateParam().get(1));
        }
        return getByCondition(pageNum, pageSize);
    }

    public SellerOrderIndex getIndexById(int id) {
        return sellerOrderManager.getIndexById(id);
    }

    @Transactional
    public SellerOrderIndex save(SellerOrderIndex index) {
        SellerOrderInfo info = index.getOrderInfo();
        info.setTotalMoney(MoneyUtils.changeY2F(info.getTotalMoneyDisplay()));
        info.setFreight(MoneyUtils.changeY2F(info.getFreightDisplay()));
        boolean needActionLog = false;
        if (info.getId() == null) {
            Integer count = sellerOrderManager.getCount(LoginFilter.getCurrentUser().getCompanyId());
            String orderCode = "Order" + frontCompWithZore(count+1,8);
            info.setOrderCode(orderCode);
            info.setOwner(LoginFilter.getCurrentUser().getUserName());
            info.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            needActionLog = true;
            sellerOrderManager.insertLog(info);
        }
        //新增订单
        SellerOrderInfo entity = saveOrUpdate(info);
        sellerOrderManager.saveProducts(index.getProductInfos(), entity.getId(), needActionLog);
        index.setOrderInfo(ExchangeUtils.exchangeObject(entity, SellerOrderInfo.class));
        return index;
    }

    public void confirm(int id) {
        SellerOrderEntity entity = new SellerOrderEntity();
        entity.setId(id);
        entity.setProgress(SellerOrderProgress.WAIT_PRODUCE.getId());
        sellerOrderManager.updateByPrimaryKeySelective(entity);
    }

    public void delivery(int id) {
        SellerOrderEntity entity = new SellerOrderEntity();
        entity.setId(id);
        entity.setProgress(SellerOrderProgress.WAIT_DELIVERY.getId());
        sellerOrderManager.updateByPrimaryKeySelective(entity);
    }

    public void updateByContractId(String contractId){
        sellerOrderManager.updateByContractId(contractId);
    }

    /****
     * 确认按钮
     * @param id
     */
    @Transactional
    public void saveConfirm(Integer id,Integer purchaseStayNumber){
        //按照订单需要的产品查询库存是否需要生产
        Boolean fromStock = true;
        SellerOrderIndex orderIndex = sellerOrderManager.getIndexById(id);
        List<SellerOrderProductInfo> requestProductList = orderIndex.getProductInfos();
        //根据产品找到产品所在的订单
        SellerOrderEntity soi = sellerOrderManager.getEntityById(requestProductList.get(0).getOrderId());
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
        //待生产数
        Float stayProductionQty = 0.00f;

        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        for(SellerOrderProductInfo product : requestProductList){
            MaterialBomEntity bomInfo = materialBomManager.getEntityById(product.getProductId());
            //取出这个订单中的产品数量
            Float needQty = Float.parseFloat(product.getProductNumber().toString());
            // 判断订单中最外层的物料需不需要采购
            if ("外购".equals(bomInfo.getSource())){
                //根据物料id去仓库表查询数据
                MaterialRepertoryEntity mri = new MaterialRepertoryEntity();
                mri.setMaterialId(product.getProductId());
                List<MaterialRepertoryEntity> mriList =  materialRepertoryManager.getListBy(mri);
                if (mriList.size()!=0){
                    inventoryQty = mriList.get(0).getInventoryQty();
                    stayDeliveredQty = mriList.get(0).getStayDeliveredQty();
                    stayInboundQty = mriList.get(0).getStayInboundQty();
                    orderNeedQty = mriList.get(0).getOrderNeedQty();
                    stayPurchaseQty = mriList.get(0).getStayPurchaseQty();
                    //算出当前物料可用的数量
                    Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-(needQty+orderNeedQty);
                    if (differenceQty<0){
                        //如果可用数量小于0，则需要采购，生成待采购单
                        PurchaseOrdersStayEntity posi = new PurchaseOrdersStayEntity();
                        posi.setMaterialId(product.getProductId());
                        posi.setStayPurQty(-differenceQty);
                        //采购原因取订单合同号
                        posi.setPurchaseReason(soi.getContractId());
                        posi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        //最晚采购日期先不考虑，所以先放个当前日期
                        posi.setLatestPurDate(createDate);
                        posi.setOrderId(soi.getId());
                        posi.setMaterialCode(bomInfo.getMaterialCode());
                        posi.setMaterialName(bomInfo.getMaterialName());
                        posi.setMaterialType(bomInfo.getType());
                        posi.setMaterialBomParamValueStr(bomInfo.getMaterialBomParamValueStr());
                        posi.setOriginalMaterial(bomInfo.getOriginalMaterial());
                        posi.setPurchaseStayNumber(purchaseStayNumber);
                        PurchaseOrdersStayEntity poe = purchaseOrdersStayManager.insertBy(posi);
                        //拼接生成待采购编码
                        String purchaseStayCode = "Pur" +frontCompWithZore(purchaseStayNumber,8);
                        posi.setId(poe.getId());
                        posi.setPurchaseStayCode(purchaseStayCode);
                        //根据id修改这条待采购记录
                        purchaseOrdersStayManager.updateByPrimaryKeySelective(posi);
                        //更新物料-仓库表的订单需求数和待采购总数
                        mri.setId(mriList.get(0).getId());
                        mri.setOrderNeedQty(needQty+orderNeedQty);
                        mri.setStayPurchaseQty(-differenceQty+stayPurchaseQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mri);
                        //待生产
                        soi.setProgress(2);
                        soi.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soi);
                        SellerOrderProductEntity sope = new SellerOrderProductEntity();
                        sope.setOrderId(soi.getId());
                        sope.setProductId(product.getProductId());
                        sope.setMaterialStatus(STATUS_DEFAULT);
                        //id要改
                        sope.setId(id);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                    }else {
                        //待发货
                        soi.setProgress(4);
                        soi.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soi);
                        //修改订单需求数
                        mri.setId(mriList.get(0).getId());
                        mri.setOrderNeedQty(needQty+orderNeedQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mri);
                        SellerOrderProductEntity sope = new SellerOrderProductEntity();
                        sope.setOrderId(soi.getId());
                        sope.setProductId(product.getProductId());
                        sope.setMaterialStatus(STATUS_DEFAULT);
                        //id要改
                        sope.setId(id);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                    }
                }
                //待生产
                soi.setProgress(2);
                soi.setId(id);
                sellerOrderManager.updateByPrimaryKeySelective(soi);

            }else{
                //根据物料id去仓库表查询数据
                MaterialRepertoryEntity mri = new MaterialRepertoryEntity();
                mri.setMaterialId(product.getProductId());
                List<MaterialRepertoryEntity> mriList =  materialRepertoryManager.getListBy(mri);
                if (mriList.size()!=0){
                    inventoryQty = mriList.get(0).getInventoryQty();
                    stayDeliveredQty = mriList.get(0).getStayDeliveredQty();
                    stayInboundQty = mriList.get(0).getStayInboundQty();
                    orderNeedQty = mriList.get(0).getOrderNeedQty();
                    stayPurchaseQty = mriList.get(0).getStayPurchaseQty();
                    stayProductionQty = mriList.get(0).getStayProductionQty();
                    //算出当前物料可用的数量
                    Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty+stayProductionQty-(needQty+orderNeedQty);
                    if (differenceQty>=0){
                        //待发货
                        soi.setProgress(4);
                        soi.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soi);
                        //修改订单需求数
                        mri.setId(mriList.get(0).getId());
                        mri.setOrderNeedQty(needQty+orderNeedQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mri);
                    }else{
                        ProductionPlanEntity prPlanEntity = new ProductionPlanEntity();
                        Integer materialId = product.getProductId();
                        MaterialBomEntity mbe = materialBomManager.getEntityById(materialId);
                        String numberValue = null;
                        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                        prPlanEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        prPlanEntity.setNumber(numberValue);
                        prPlanEntity.setSource(mbe.getSource());
                        prPlanEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        prPlanEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        prPlanEntity.setDrawingCode(mbe.getDrawingCode());

                        prPlanEntity.setProductBatch(orderIndex.getOrderInfo().getOrderCode());
                        prPlanEntity.setMaterialId(materialId);
                        prPlanEntity.setMaterialName(product.getProductName());
                        prPlanEntity.setContractId(soi.getContractId());
                        prPlanEntity.setOrderCode(orderIndex.getOrderInfo().getOrderCode());
                        prPlanEntity.setProductionType(PLAN_CREATE);
                        prPlanEntity.setProductType(PRODUCT_TYPE);
                        prPlanEntity.setMaterialNum(-differenceQty);
                        prPlanEntity.setIssueFlag(PRODUCE_PLAN);
                        prPlanEntity.setMaterialUnit(bomInfo.getMaterialUnit());
                        prPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
                        prPlanEntity.setSequenceNumber(product.getSequenceNumber().toString());
                        prPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        prPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                        prPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                        prPlanEntity.setMaterialCode(mbe.getMaterialCode());
                        Integer planNumber = productionPlanService.getNextNumber();
                        prPlanEntity.setPlanNumber(planNumber);
                        prPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                        ///todo 计划生产时间
                        prPlanEntity.setPlanProductDate(product.getDeliveryDate());
                        ///todo 最晚生产时间为交货时间-生产所需时间
                        prPlanEntity.setLatestProductDate(product.getDeliveryDate());
                        //添加记录
                        ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(prPlanEntity);
                        //生产单号
                        String productionNumber = frontCompWithZore(planNumber,11);
                        productionPlanEntity.setProductionNumber(productionNumber);
                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                        mri.setId(mriList.get(0).getId());
                        mri.setOrderNeedQty(needQty+orderNeedQty);
                        mri.setStayProductionQty(-differenceQty+stayProductionQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mri);
                        newPurOrders(product.getProductId(),-differenceQty,soi.getContractId(),createDate,id,orderIndex.getOrderInfo().getOrderCode(),product.getDeliveryDate(),purchaseStayNumber,product.getSequenceNumber().toString());
                    }
                }
            }
        }
    }


    /****
     * 修改按钮
     * @param index
     */
    @Transactional
    public void update(SellerOrderIndex index,List<SellerOrderProductInfo> oldSopiList){
        //获取当前时间，赋值给创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String createDate = dateFormat.format(date);
        //获取当前订单
        SellerOrderEntity soe = ExchangeUtils.exchangeObject(index.getOrderInfo(),SellerOrderEntity.class);
        //获取订单的产品id
        List<SellerOrderProductInfo> productInfoList = index.getProductInfos();
        //获取订单的合同号
        String contractId = index.getOrderInfo().getContractId();
        String orderCode = index.getOrderInfo().getOrderCode();
        Integer orderId = index.getOrderInfo().getId();
        //订单在确认后修改要重新计算，在确认前修改不用重新计算
        if(index.getOrderInfo().getProgress()!=0 && index.getOrderInfo().getProgress()!=1){
            ProductionPlanEntity ppe = new ProductionPlanEntity();
            ppe.setOrderCode(orderCode);
            ppe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
            ppe.setStatus(STATUS_DEFAULT);
            List<ProductionPlanEntity> ppeList = productionPlanManager.getListBy(ppe);
            if(ppeList.size() != 0){
                for(ProductionPlanEntity ppEntity : ppeList){
                    if(ppEntity.getIssueQty() == 0){
                        MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                        mre.setMaterialId(ppEntity.getMaterialId());
                        mre.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        mre.setStatus(STATUS_DEFAULT);
                        List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                        if(mreList.size() != 0){
                            mre = mreList.get(0);
                            mre.setStayProductionQty(mre.getStayProductionQty()-ppEntity.getMaterialNum());
                            materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        }
                    }
                }
            }
            productionPlanService.deleteProductionPlan(orderCode);
            // 递归删除原来的订单数
            if (oldSopiList.size()!=0){
                for (int i=0;i<oldSopiList.size();i++){
                    Integer materialId = oldSopiList.get(i).getProductId();
                    Float orderQty = oldSopiList.get(i).getProductNumber();
                    //剪掉订单需求总数
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(materialId);
                    List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                    if (mreList.size()!=0){
                        Float oldNeedQty = mreList.get(0).getOrderNeedQty();
                        oldNeedQty = oldNeedQty-orderQty;
                        if (oldNeedQty<0){
                            oldNeedQty=0f;
                        }
                        mre.setOrderNeedQty(oldNeedQty);
                        mre.setId(mreList.get(0).getId());
                    }
                    materialRepertoryManager.updateByPrimaryKeySelective(mre);
                    deleteQty(materialId,orderQty,soe.getId());
                }
            }
            //计算原来待采购单的数量
            PurchaseOrdersStayEntity purchaseOrdersStayEntity = new PurchaseOrdersStayEntity();
            purchaseOrdersStayEntity.setOrderId(soe.getId());
            purchaseOrdersStayEntity.setPurchaseStayStatus(0);
            purchaseOrdersStayEntity.setStatus(1);
            List<PurchaseOrdersStayEntity> purchaseOrdersStayEntityList = purchaseOrdersStayManager.getListBy(purchaseOrdersStayEntity);
            if (purchaseOrdersStayEntityList.size()!=0){
                for (int i=0;i<purchaseOrdersStayEntityList.size();i++){
                    Integer materialId =purchaseOrdersStayEntityList.get(i).getMaterialId();
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(materialId);
                    List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                    if (mreList.size()!=0){
                        Float oldStayQty = mreList.get(0).getStayPurchaseQty();
                        if (purchaseOrdersStayEntityList.size()!=0){
                            oldStayQty = oldStayQty-purchaseOrdersStayEntityList.get(i).getStayPurQty();
                        }
                        mre.setStayPurchaseQty(oldStayQty);
                        mre.setId(mreList.get(0).getId());
                    }
                    materialRepertoryManager.updateByPrimaryKeySelective(mre);
                }
            }
            //把当前订单的所有带采购记录变成失效
            purchaseOrdersStayManager.updateStayPurchaseStatusByOrderId(soe.getId());
        }

    }

    public Result<Void> deleteQty(Integer materialId,Float orderQty,Integer orderId){
        MaterialRelationEntity mri = new MaterialRelationEntity();
        mri.setMaterialParentId(materialId);
        mri.setStatus(1);
        List<MaterialRelationEntity> mriList = materialRelationManager.getListBy(mri);
        if (mriList.size()!=0){
            for (int i=0;i<mriList.size();i++){
                //需要几个当前部件
                Float needQty = mriList.get(i).getQuantity()*orderQty;
                //剪掉订单需求总数
                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(mriList.get(i).getMaterialChildId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if (mreList.size()!=0){
                    Float oldNeedQty = mreList.get(0).getOrderNeedQty();
                    oldNeedQty = oldNeedQty-needQty;
                    if (oldNeedQty<0){
                        oldNeedQty=0f;
                    }
                    mre.setOrderNeedQty(oldNeedQty);
                    mre.setId(mreList.get(0).getId());
                }
                materialRepertoryManager.updateByPrimaryKeySelective(mre);
                deleteQty(mriList.get(i).getMaterialChildId(),needQty,orderId);
            }
        }
        return ResultUtils.success();
    }

    @Transactional
    public Result<Void> newPurOrders(Integer id, Float quantity, String contractId, String createDate, Integer orderId,String orderCode,String deliveryDate,Integer purchaseStayNumber,String sequenceNumber){
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
        //待生产数
        Float stayProductionQty = 0.00f;

        SellerOrderEntity sellerOrderEntity = new SellerOrderEntity();
        MaterialRelationEntity mri = new MaterialRelationEntity();
        mri.setMaterialParentId(id);
        mri.setStatus(1);
        List<MaterialRelationEntity> mriList = materialRelationManager.getListBy(mri);
        if (mriList.size()!=0){
            for (int i=0;i<mriList.size();i++){
                //需要几个当前部件
                Float needQty = mriList.get(i).getQuantity()*quantity;
                MaterialBomEntity mbi = materialBomManager.getEntityById(mriList.get(i).getMaterialChildId());
                SellerOrderEntity soi = new SellerOrderEntity();
                //先判断是否是外购
                if ("外购".equals(mbi.getSource())) {
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity materialRepertoryInfo = new MaterialRepertoryEntity();
                    materialRepertoryInfo.setMaterialId(mriList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> materialRepertoryInfoList =  materialRepertoryManager.getListBy(materialRepertoryInfo);
                    if (materialRepertoryInfoList.size()!=0){
                        inventoryQty = materialRepertoryInfoList.get(0).getInventoryQty();
                        stayDeliveredQty = materialRepertoryInfoList.get(0).getStayDeliveredQty();
                        stayInboundQty = materialRepertoryInfoList.get(0).getStayInboundQty();
                        orderNeedQty = materialRepertoryInfoList.get(0).getOrderNeedQty();
                        stayPurchaseQty = materialRepertoryInfoList.get(0).getStayPurchaseQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-(needQty+orderNeedQty);
                        if (differenceQty<0){
                            //如果可用数量小于0，则需要采购，生成待采购单
                            PurchaseOrdersStayEntity posi = new PurchaseOrdersStayEntity();
                            posi.setMaterialId(mriList.get(i).getMaterialChildId());
                            posi.setStayPurQty(-differenceQty);
                            //采购原因取订单合同号
                            posi.setPurchaseReason(contractId);
                            posi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            //最晚采购日期先不考虑，所以先放个当前日期
                            posi.setLatestPurDate(createDate);
                            posi.setOrderId(orderId);
                            posi.setPurchaseStayNumber(purchaseStayNumber);
                            PurchaseOrdersStayEntity poe = purchaseOrdersStayManager.insertBy(posi);
                            //拼接生成待采购编码
                            String purchaseStayCode = "Pur" +frontCompWithZore(purchaseStayNumber,8);
                            posi.setId(poe.getId());
                            posi.setMaterialCode(mbi.getMaterialCode());
                            posi.setMaterialName(mbi.getMaterialName());
                            posi.setMaterialType(mbi.getType());
                            posi.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
                            posi.setOriginalMaterial(mbi.getOriginalMaterial());
                            posi.setPurchaseStayCode(purchaseStayCode);
                            //根据id修改这条待采购记录
                            purchaseOrdersStayManager.updateByPrimaryKeySelective(posi);
                            //更新物料-仓库表的订单需求数和待采购总数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryInfo.setStayPurchaseQty(-differenceQty+stayPurchaseQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                            //待生产
                            soi.setId(orderId);
                            soi.setProgress(2);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                        }else {
                            //待发货
                            soi.setId(orderId);
                            soi.setProgress(4);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                            //更新物料-仓库表的订单需求数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                        }
                    }
                }else{
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                    mre.setMaterialId(mriList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> mreList =  materialRepertoryManager.getListBy(mre);
                    if (mreList.size()!=0){
                        inventoryQty = mreList.get(0).getInventoryQty();
                        stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
                        stayInboundQty = mreList.get(0).getStayInboundQty();
                        orderNeedQty = mreList.get(0).getOrderNeedQty();
                        stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
                        stayProductionQty = mreList.get(0).getStayProductionQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty+stayProductionQty-(needQty+orderNeedQty);
                        if (differenceQty>=0){
                            //待发货
                            soi.setProgress(4);
                            soi.setId(id);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                            //修改订单需求数
                            mre.setId(mreList.get(0).getId());
                            mre.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        }else{

                            ProductionPlanEntity proPlanEntity = new ProductionPlanEntity();
                            String numberValue = null;
                            numberValue = materialRepertoryDetailService.getNumberValue(mbi.getNumber(), numberValue);
                            proPlanEntity.setFactoryMaterialCode(mbi.getFactoryMaterialCode());
                            proPlanEntity.setNumber(numberValue);
                            proPlanEntity.setSource(mbi.getSource());
                            proPlanEntity.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
                            proPlanEntity.setOriginalMaterial(mbi.getOriginalMaterial());
                            proPlanEntity.setDrawingCode(mbi.getDrawingCode());

                            proPlanEntity.setProductBatch(orderCode);
                            proPlanEntity.setMaterialNum(-differenceQty);
                            proPlanEntity.setMaterialId(mbi.getId());
                            proPlanEntity.setMaterialName(mbi.getMaterialName());
                            proPlanEntity.setMaterialUnit(mbi.getMaterialUnit());
                            proPlanEntity.setContractId(contractId);
                            proPlanEntity.setSequenceNumber(sequenceNumber+"_"+(i+1));
                            proPlanEntity.setProductionType(PLAN_CREATE);
                            proPlanEntity.setOrderCode(orderCode);
                            proPlanEntity.setIssueFlag(PRODUCE_PLAN);
                            proPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                            proPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                            proPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            proPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                            proPlanEntity.setMaterialCode(mbi.getMaterialCode());
                            Integer typeFlag = mbi.getFlag();
                            if (typeFlag == 1) {
                                proPlanEntity.setProductType(PRODUCT_TYPE);
                            } else {
                                proPlanEntity.setProductType(MATERIAL_TYPE);
                            }

                            ///todo 计划生产时间
                            proPlanEntity.setPlanProductDate(deliveryDate);
                            ///todo 最晚生产时间为交货时间-生产所需时间
                            proPlanEntity.setLatestProductDate(deliveryDate);
                            proPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
                            Integer planNumber = productionPlanService.getNextNumber();
                            proPlanEntity.setPlanNumber(planNumber);
                            //添加记录
                            ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(proPlanEntity);
                            //生产单号
                            String productionNumber = frontCompWithZore(planNumber,11);
                            productionPlanEntity.setProductionNumber(productionNumber);
                            productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                            //修改订单需求数
                            mre.setId(mreList.get(0).getId());
                            mre.setOrderNeedQty(needQty+orderNeedQty);
                            mre.setStayProductionQty(-differenceQty+stayProductionQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(mre);
                            newPurOrders(mriList.get(i).getMaterialChildId(),-differenceQty,contractId,createDate,orderId,orderCode,deliveryDate,purchaseStayNumber,sequenceNumber+"_"+(i+1));
                        }
                    }
                }
            }
        }else {
            //待生产
            sellerOrderEntity.setId(orderId);
            sellerOrderEntity.setProgress(2);
            sellerOrderManager.updateByPrimaryKeySelective(sellerOrderEntity);
        }
        return ResultUtils.success();
    }

    @Transactional
    public Result<Void> newUpdatPurOrders(Integer id, Float quantity, String contractId, String createDate, Integer orderId,String orderCode,String deliveryDate,Integer purchaseStayNumber,String sequenceNumber){
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
        //待生产数
        Float stayProductionQty = 0.00f;

        SellerOrderEntity sellerOrderEntity = new SellerOrderEntity();
        MaterialRelationEntity mri = new MaterialRelationEntity();
        mri.setMaterialParentId(id);
        mri.setStatus(1);
        List<MaterialRelationEntity> mriList = materialRelationManager.getListBy(mri);
        if (mriList.size()!=0){
            for (int i=0;i<mriList.size();i++){
                //查询出仓库里面这个物料的数据
                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(mriList.get(i).getMaterialChildId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                //需要几个当前部件
                Float needQty = mriList.get(i).getQuantity()*quantity;
                MaterialBomEntity mbi = materialBomManager.getEntityById(mriList.get(i).getMaterialChildId());
                SellerOrderEntity soi = new SellerOrderEntity();
                //先判断是否是外购
                if ("外购".equals(mbi.getSource())) {
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity materialRepertoryInfo = new MaterialRepertoryEntity();
                    materialRepertoryInfo.setMaterialId(mriList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> materialRepertoryInfoList =  materialRepertoryManager.getListBy(materialRepertoryInfo);
                    if (materialRepertoryInfoList.size()!=0){
                        inventoryQty = materialRepertoryInfoList.get(0).getInventoryQty();
                        stayDeliveredQty = materialRepertoryInfoList.get(0).getStayDeliveredQty();
                        stayInboundQty = materialRepertoryInfoList.get(0).getStayInboundQty();
                        orderNeedQty = materialRepertoryInfoList.get(0).getOrderNeedQty();
                        stayPurchaseQty = materialRepertoryInfoList.get(0).getStayPurchaseQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty-(needQty+orderNeedQty);
                        if (differenceQty<0){
                            //如果可用数量小于0，则需要采购，生成待采购单
                            PurchaseOrdersStayEntity posi = new PurchaseOrdersStayEntity();
                            posi.setMaterialId(mriList.get(i).getMaterialChildId());
                            posi.setStayPurQty(-differenceQty);
                            //采购原因取订单合同号
                            posi.setPurchaseReason(contractId);
                            posi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            //最晚采购日期先不考虑，所以先放个当前日期
                            posi.setLatestPurDate(createDate);
                            posi.setOrderId(orderId);
                            posi.setMaterialCode(mbi.getMaterialCode());
                            posi.setMaterialName(mbi.getMaterialName());
                            posi.setMaterialType(mbi.getType());
                            posi.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
                            posi.setOriginalMaterial(mbi.getOriginalMaterial());
                            posi.setPurchaseStayNumber(purchaseStayNumber);
                            PurchaseOrdersStayEntity poe = purchaseOrdersStayManager.insertBy(posi);
                            //拼接生成待采购编码
                            String purchaseStayCode = "Pur" +frontCompWithZore(purchaseStayNumber,8);
                            posi.setId(poe.getId());
                            posi.setPurchaseStayCode(purchaseStayCode);
                            //根据id修改这条待采购记录
                            purchaseOrdersStayManager.updateByPrimaryKeySelective(posi);
                            //更新物料-仓库表的订单需求数和待采购总数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryInfo.setStayPurchaseQty(-differenceQty+stayPurchaseQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                            //待生产
                            soi.setId(orderId);
                            soi.setProgress(2);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                        }else {
                            //待发货
                            soi.setId(orderId);
                            soi.setProgress(4);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                            //更新物料-仓库表的订单需求数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                        }
                    }
                }else{
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                    materialRepertoryEntity.setMaterialId(mriList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> materialRepertoryEntityList =  materialRepertoryManager.getListBy(materialRepertoryEntity);
                    if (materialRepertoryEntityList.size()!=0){
                        inventoryQty = materialRepertoryEntityList.get(0).getInventoryQty();
                        stayDeliveredQty = materialRepertoryEntityList.get(0).getStayDeliveredQty();
                        stayInboundQty = materialRepertoryEntityList.get(0).getStayInboundQty();
                        orderNeedQty = materialRepertoryEntityList.get(0).getOrderNeedQty();
                        stayPurchaseQty = materialRepertoryEntityList.get(0).getStayPurchaseQty();
                        stayProductionQty = materialRepertoryEntityList.get(0).getStayProductionQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayInboundQty+stayPurchaseQty+stayProductionQty-(needQty+orderNeedQty);
                        if (differenceQty>=0){
                            //待发货
                            soi.setProgress(4);
                            soi.setId(orderId);
                            sellerOrderManager.updateByPrimaryKeySelective(soi);
                            //修改订单需求数
                            materialRepertoryEntity.setId(materialRepertoryEntityList.get(0).getId());
                            materialRepertoryEntity.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                        }else{

                            ProductionPlanEntity proPlanEntity = new ProductionPlanEntity();
                            String numberValue = null;
                            numberValue = materialRepertoryDetailService.getNumberValue(mbi.getNumber(), numberValue);
                            proPlanEntity.setFactoryMaterialCode(mbi.getFactoryMaterialCode());
                            proPlanEntity.setNumber(numberValue);
                            proPlanEntity.setSource(mbi.getSource());
                            proPlanEntity.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
                            proPlanEntity.setOriginalMaterial(mbi.getOriginalMaterial());
                            proPlanEntity.setDrawingCode(mbi.getDrawingCode());

                            proPlanEntity.setProductBatch(orderCode);
                            proPlanEntity.setMaterialNum(-differenceQty);
                            proPlanEntity.setMaterialId(mbi.getId());
                            proPlanEntity.setMaterialName(mbi.getMaterialName());
                            proPlanEntity.setMaterialUnit(mbi.getMaterialUnit());
                            proPlanEntity.setOriginalMaterial(mbi.getOriginalMaterial());
                            proPlanEntity.setContractId(contractId);
                            proPlanEntity.setProductionType(PLAN_CREATE);
                            proPlanEntity.setOrderCode(orderCode);
                            proPlanEntity.setIssueFlag(PRODUCE_PLAN);
                            proPlanEntity.setSequenceNumber(sequenceNumber+"_"+(i+1));
                            proPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                            proPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                            proPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            proPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                            proPlanEntity.setMaterialCode(mbi.getMaterialCode());
                            Integer typeFlag = mbi.getFlag();
                            if (typeFlag == 1) {
                                proPlanEntity.setProductType(PRODUCT_TYPE);
                            } else {
                                proPlanEntity.setProductType(MATERIAL_TYPE);
                            }

                            ///todo 计划生产时间
                            proPlanEntity.setPlanProductDate(deliveryDate);
                            ///todo 最晚生产时间为交货时间-生产所需时间
                            proPlanEntity.setLatestProductDate(deliveryDate);
                            proPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);

                            Integer planNumber = productionPlanService.getNextNumber();
                            proPlanEntity.setPlanNumber(planNumber);
                            //添加记录
                            ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(proPlanEntity);
                            //生产单号
                            String productionNumber = frontCompWithZore(planNumber,11);
                            productionPlanEntity.setProductionNumber(productionNumber);
                            productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                            //修改订单需求数
                            materialRepertoryEntity.setId(materialRepertoryEntityList.get(0).getId());
                            materialRepertoryEntity.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryEntity.setStayProductionQty(-differenceQty+stayProductionQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                            newUpdatPurOrders(mriList.get(i).getMaterialChildId(),-differenceQty,contractId,createDate,orderId,orderCode,deliveryDate,purchaseStayNumber,sequenceNumber+"_"+(i+1));
                        }
                    }
                }
            }
        }else {
            //待生产
            sellerOrderEntity.setId(orderId);
            sellerOrderEntity.setProgress(2);
            sellerOrderManager.updateByPrimaryKeySelective(sellerOrderEntity);
        }
        return ResultUtils.success();
    }

    public List<SellerOrderEntity> getContractIdList(SellerOrderInfo sellerOrderInfo){
        SellerOrderEntity sellerOrderEntity = ExchangeUtils.exchangeObject(sellerOrderInfo, SellerOrderEntity.class);
        sellerOrderEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        return sellerOrderManager.getListBy(sellerOrderEntity);
    }

    @Override
    public InputStream getFileInputStream(String filePath) {
        return FileUtils.getInputStream(env, filePath);
    }

    @Override
    protected String getRedisNumberKeyPrefix() {
        return null;
    }

    @Transactional
    public void saveConfirmProduct(Integer id, List<Integer> sellerOrderProductIds){
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
        //待生产数
        Float stayProductionQty = 0.00f;
        //待检测数
        Float stayCheckQty = 0.00f;

        String createDate = getDateFormat("yyyy/MM/dd HH:mm:ss");

        //获取订单主表
        SellerOrderEntity soe = sellerOrderManager.getEntityById(id);

        for (Integer sellerOrderProductId : sellerOrderProductIds){
            Integer purchaseStayNumber = purchaseOrdersStayService.getNextNumber();
            SellerOrderProductEntity sope = sellerOrderProductManager.getEntityById(sellerOrderProductId);
            MaterialBomEntity mbe = materialBomManager.getEntityById(sope.getProductId());
            //取出这个订单中的产品数量
            Float needQty = Float.parseFloat(sope.getProductNumber().toString());
            // 判断订单中最外层的物料需不需要采购
            if ("外购".equals(mbe.getSource())) {
                //根据物料id去仓库表查询数据
                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(mbe.getId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if (mreList.size() != 0) {
                    inventoryQty = mreList.get(0).getInventoryQty();
                    stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
                    stayInboundQty = mreList.get(0).getStayInboundQty();
                    orderNeedQty = mreList.get(0).getOrderNeedQty();
                    stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
                    stayCheckQty = mreList.get(0).getStayCheckQty();
                    //算出当前物料可用的数量
                    Float differenceQty = inventoryQty + stayDeliveredQty + stayCheckQty + stayInboundQty + stayPurchaseQty - (needQty + orderNeedQty);
                    if (differenceQty < 0) {
                        //如果可用数量小于0，则需要采购，生成待采购单
                        PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
                        pose.setMaterialId(mbe.getId());
                        pose.setStayPurQty(-differenceQty);
                        //采购原因取订单合同号
                        pose.setPurchaseReason(soe.getContractId());
                        pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        //最晚采购日期先不考虑，所以先放个当前日期
                        pose.setLatestPurDate(createDate);
                        pose.setOrderId(soe.getId());
                        pose.setMaterialCode(mbe.getMaterialCode());
                        pose.setMaterialName(mbe.getMaterialName());
                        pose.setMaterialType(mbe.getType());
                        pose.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        pose.setOriginalMaterial(mbe.getOriginalMaterial());
                        pose.setSellerOrderProductId(sellerOrderProductId);
                        pose.setPurchaseStayNumber(purchaseStayNumber);
                        pose.setOrderNeedQty(needQty);
                        //拼接生成待采购编码
                        String purchaseStayCode = "Pur" + frontCompWithZore(purchaseStayNumber, 8);
                        pose.setPurchaseStayCode(purchaseStayCode);
                        PurchaseOrdersStayEntity poe = purchaseOrdersStayManager.insertBy(pose);
                        //更新物料-仓库表的订单需求数和待采购总数
                        mre.setId(mreList.get(0).getId());
                        mre.setOrderNeedQty(needQty + orderNeedQty);
                        mre.setStayPurchaseQty(-differenceQty + stayPurchaseQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        //修改当条明细为已确认
                        sope.setMaterialStatus(STATUS_CONFIRM);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                        soe.setProgress(2);
                        soe.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soe);
                    } else {
                        //修改订单需求数
                        mre.setId(mreList.get(0).getId());
                        mre.setOrderNeedQty(needQty + orderNeedQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        //修改当条明细为已确认
                        sope.setMaterialStatus(STATUS_CONFIRM);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                        soe.setProgress(4);
                        soe.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soe);
                    }
                }
            } else {
                //根据物料id去仓库表查询数据
                MaterialRepertoryEntity mre = new MaterialRepertoryEntity();
                mre.setMaterialId(mbe.getId());
                List<MaterialRepertoryEntity> mreList = materialRepertoryManager.getListBy(mre);
                if (mreList.size() != 0) {
                    inventoryQty = mreList.get(0).getInventoryQty();
                    stayDeliveredQty = mreList.get(0).getStayDeliveredQty();
                    stayInboundQty = mreList.get(0).getStayInboundQty();
                    orderNeedQty = mreList.get(0).getOrderNeedQty();
                    stayPurchaseQty = mreList.get(0).getStayPurchaseQty();
                    stayProductionQty = mreList.get(0).getStayProductionQty();
                    stayCheckQty = mreList.get(0).getStayCheckQty();
                    //算出当前物料可用的数量
                    Float differenceQty = inventoryQty + stayDeliveredQty + stayCheckQty + stayInboundQty + stayPurchaseQty + stayProductionQty - (needQty + orderNeedQty);
                    if (differenceQty >= 0) {
                        //修改订单需求数
                        mre.setId(mreList.get(0).getId());
                        mre.setOrderNeedQty(needQty + orderNeedQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        //修改当条明细为已确认
                        sope.setMaterialStatus(STATUS_CONFIRM);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                        soe.setProgress(2);
                        soe.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soe);
                    } else {
                        ProductionPlanEntity prPlanEntity = new ProductionPlanEntity();
                        String numberValue = null;
                        numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                        prPlanEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                        prPlanEntity.setNumber(numberValue);
                        prPlanEntity.setSource(mbe.getSource());
                        prPlanEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                        prPlanEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                        prPlanEntity.setDrawingCode(mbe.getDrawingCode());
                        prPlanEntity.setProductBatch(soe.getOrderCode());
                        prPlanEntity.setMaterialId(mbe.getId());
                        prPlanEntity.setMaterialName(mbe.getMaterialName());
                        prPlanEntity.setContractId(soe.getContractId());
                        prPlanEntity.setOrderCode(soe.getOrderCode());
                        prPlanEntity.setProductionType(PLAN_CREATE);
                        prPlanEntity.setProductType(PRODUCT_TYPE);
                        prPlanEntity.setMaterialNum(-differenceQty);
                        prPlanEntity.setIssueFlag(PRODUCE_PLAN);
                        prPlanEntity.setSellerOrderProductId(sellerOrderProductId);
                        prPlanEntity.setMaterialUnit(mbe.getMaterialUnit());
                        prPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
                        prPlanEntity.setSequenceNumber(sope.getSequenceNumber().toString());
                        prPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        prPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                        prPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                        prPlanEntity.setMaterialCode(mbe.getMaterialCode());
                        Integer planNumber = productionPlanService.getNextNumber();
                        prPlanEntity.setPlanNumber(planNumber);
                        prPlanEntity.setOrderNeedQty(needQty);
                        prPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                        ///todo 计划生产时间
                        prPlanEntity.setPlanProductDate(sope.getDeliveryDate());
                        ///todo 最晚生产时间为交货时间-生产所需时间
                        prPlanEntity.setLatestProductDate(sope.getDeliveryDate());
                        //添加记录
                        ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(prPlanEntity);
                        //生产单号
                        String productionNumber = frontCompWithZore(planNumber, 11);
                        productionPlanEntity.setProductionNumber(productionNumber);
                        productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);
                        mre.setId(mreList.get(0).getId());
                        mre.setOrderNeedQty(needQty + orderNeedQty);
                        mre.setStayProductionQty(-differenceQty + stayProductionQty);
                        materialRepertoryManager.updateByPrimaryKeySelective(mre);
                        //修改当条明细为已确认
                        sope.setMaterialStatus(STATUS_CONFIRM);
                        sellerOrderProductManager.updateByPrimaryKeySelective(sope);
                        soe.setProgress(4);
                        soe.setId(id);
                        sellerOrderManager.updateByPrimaryKeySelective(soe);
                        newSellerProduct(mbe.getId(), -differenceQty, soe.getContractId(), createDate, id, soe.getOrderCode(), sope.getDeliveryDate(), sope.getSequenceNumber().toString(),sellerOrderProductId);
                    }
                }
            }
            //增加日志
            SellerOrderLogEntity logEntity = new SellerOrderLogEntity();
            logEntity.setOrderId(sope.getOrderId());
            logEntity.setOperator(LoginFilter.getCurrentUser().getUserName());
            logEntity.setOperation("确认产品");
            logEntity.setContent("产品编码"+mbe.getMaterialCode()+"已确认");
            sellerOrderLogManager.insertBy(logEntity);
        }
    }

    @Transactional
    public void newSellerProduct(Integer id, Float quantity, String contractId, String createDate, Integer orderId,String orderCode,String deliveryDate,String sequenceNumber,Integer sellerOrderProductId){
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
        //待生产数
        Float stayProductionQty = 0.00f;
        //待检测数
        Float stayCheckQty = 0.00f;

        SellerOrderEntity sellerOrderEntity = new SellerOrderEntity();
        MaterialRelationEntity mre = new MaterialRelationEntity();
        mre.setMaterialParentId(id);
        mre.setStatus(1);
        List<MaterialRelationEntity> mreList = materialRelationManager.getListBy(mre);
        if (mreList.size()!=0){
            for (int i=0;i<mreList.size();i++){
                //需要几个当前部件
                Float needQty = mreList.get(i).getQuantity()*quantity;
                MaterialBomEntity mbe = materialBomManager.getEntityById(mreList.get(i).getMaterialChildId());
                SellerOrderEntity soi = new SellerOrderEntity();
                //先判断是否是外购
                if ("外购".equals(mbe.getSource())) {
                    Integer purchaseStayNumber = purchaseOrdersStayService.getNextNumber();
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity materialRepertoryInfo = new MaterialRepertoryEntity();
                    materialRepertoryInfo.setMaterialId(mreList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> materialRepertoryInfoList =  materialRepertoryManager.getListBy(materialRepertoryInfo);
                    if (materialRepertoryInfoList.size()!=0){
                        inventoryQty = materialRepertoryInfoList.get(0).getInventoryQty();
                        stayDeliveredQty = materialRepertoryInfoList.get(0).getStayDeliveredQty();
                        stayInboundQty = materialRepertoryInfoList.get(0).getStayInboundQty();
                        orderNeedQty = materialRepertoryInfoList.get(0).getOrderNeedQty();
                        stayPurchaseQty = materialRepertoryInfoList.get(0).getStayPurchaseQty();
                        stayCheckQty = materialRepertoryInfoList.get(0).getStayCheckQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayCheckQty+stayInboundQty+stayPurchaseQty-(needQty+orderNeedQty);
                        if (differenceQty<0){
                            //如果可用数量小于0，则需要采购，生成待采购单
                            PurchaseOrdersStayEntity pose = new PurchaseOrdersStayEntity();
                            pose.setMaterialId(mreList.get(i).getMaterialChildId());
                            pose.setStayPurQty(-differenceQty);
                            //采购原因取订单合同号
                            pose.setPurchaseReason(contractId);
                            pose.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            //最晚采购日期先不考虑，所以先放个当前日期
                            pose.setLatestPurDate(createDate);
                            pose.setOrderId(orderId);
                            pose.setPurchaseStayNumber(purchaseStayNumber);
                            //拼接生成待采购编码
                            String purchaseStayCode = "Pur" +frontCompWithZore(purchaseStayNumber,8);
                            pose.setMaterialCode(mbe.getMaterialCode());
                            pose.setMaterialName(mbe.getMaterialName());
                            pose.setMaterialType(mbe.getType());
                            pose.setSellerOrderProductId(sellerOrderProductId);
                            pose.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                            pose.setOriginalMaterial(mbe.getOriginalMaterial());
                            pose.setPurchaseStayCode(purchaseStayCode);
                            pose.setOrderNeedQty(needQty);
                            pose.setSellerOrderProductId(sellerOrderProductId);
                            //根据id修改这条待采购记录
                            purchaseOrdersStayManager.insertBy(pose);
                            //更新物料-仓库表的订单需求数和待采购总数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryInfo.setStayPurchaseQty(-differenceQty+stayPurchaseQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                        }else {
                            //更新物料-仓库表的订单需求数
                            materialRepertoryInfo.setId(materialRepertoryInfoList.get(0).getId());
                            materialRepertoryInfo.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryInfo);
                        }
                    }
                }else{
                    //根据物料id去仓库表查询数据
                    MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                    materialRepertoryEntity.setMaterialId(mreList.get(i).getMaterialChildId());
                    List<MaterialRepertoryEntity> materialRepertoryEntityList =  materialRepertoryManager.getListBy(materialRepertoryEntity);
                    if (mreList.size()!=0){
                        inventoryQty = materialRepertoryEntityList.get(0).getInventoryQty();
                        stayDeliveredQty = materialRepertoryEntityList.get(0).getStayDeliveredQty();
                        stayInboundQty = materialRepertoryEntityList.get(0).getStayInboundQty();
                        orderNeedQty = materialRepertoryEntityList.get(0).getOrderNeedQty();
                        stayPurchaseQty = materialRepertoryEntityList.get(0).getStayPurchaseQty();
                        stayProductionQty = materialRepertoryEntityList.get(0).getStayProductionQty();
                        stayCheckQty = materialRepertoryEntityList.get(0).getStayCheckQty();
                        //算出当前物料可用的数量
                        Float differenceQty = inventoryQty+stayDeliveredQty+stayCheckQty+stayInboundQty+stayPurchaseQty+stayProductionQty-(needQty+orderNeedQty);
                        if (differenceQty>=0){
                            //修改订单需求数
                            materialRepertoryEntity.setId(materialRepertoryEntityList.get(0).getId());
                            materialRepertoryEntity.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                        }else{
                            ProductionPlanEntity proPlanEntity = new ProductionPlanEntity();
                            String numberValue = null;
                            numberValue = materialRepertoryDetailService.getNumberValue(mbe.getNumber(), numberValue);
                            proPlanEntity.setFactoryMaterialCode(mbe.getFactoryMaterialCode());
                            proPlanEntity.setNumber(numberValue);
                            proPlanEntity.setSource(mbe.getSource());
                            proPlanEntity.setMaterialBomParamValueStr(mbe.getMaterialBomParamValueStr());
                            proPlanEntity.setOriginalMaterial(mbe.getOriginalMaterial());
                            proPlanEntity.setDrawingCode(mbe.getDrawingCode());
                            proPlanEntity.setProductBatch(orderCode);
                            proPlanEntity.setMaterialNum(-differenceQty);
                            proPlanEntity.setMaterialId(mbe.getId());
                            proPlanEntity.setMaterialName(mbe.getMaterialName());
                            proPlanEntity.setMaterialUnit(mbe.getMaterialUnit());
                            proPlanEntity.setContractId(contractId);
                            proPlanEntity.setProductionType(PLAN_CREATE);
                            proPlanEntity.setOrderCode(orderCode);
                            proPlanEntity.setIssueFlag(PRODUCE_PLAN);
                            proPlanEntity.setSellerOrderProductId(sellerOrderProductId);
                            proPlanEntity.setPreparedById(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                            proPlanEntity.setPreparedByName(LoginFilter.getCurrentUser().getUserName());
                            proPlanEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                            proPlanEntity.setPreparedTime(getDateFormat("yyyy-MM-dd HH:mm:ss"));
                            proPlanEntity.setOrderNeedQty(needQty);
                            proPlanEntity.setMaterialCode(mbe.getMaterialCode());
                            proPlanEntity.setSequenceNumber(sequenceNumber+"_"+(i+1));
                            Integer typeFlag = mbe.getFlag();
                            if (typeFlag == 1) {
                                proPlanEntity.setProductType(PRODUCT_TYPE);
                            } else {
                                proPlanEntity.setProductType(MATERIAL_TYPE);
                            }
                            ///todo 计划生产时间
                            proPlanEntity.setPlanProductDate(deliveryDate);
                            ///todo 最晚生产时间为交货时间-生产所需时间
                            proPlanEntity.setLatestProductDate(deliveryDate);
                            proPlanEntity.setProductionStatus(PRODUCTION_STATUS_UNCOMPLETE);
                            Integer planNumber = productionPlanService.getNextNumber();
                            proPlanEntity.setPlanNumber(planNumber);
                            //添加记录
                            ProductionPlanEntity productionPlanEntity = productionPlanManager.insertBy(proPlanEntity);
                            //生产单号
                            String productionNumber = frontCompWithZore(planNumber,11);
                            productionPlanEntity.setProductionNumber(productionNumber);
                            productionPlanManager.updateByPrimaryKeySelective(productionPlanEntity);

                            //修改订单需求数
                            materialRepertoryEntity.setId(materialRepertoryEntityList.get(0).getId());
                            materialRepertoryEntity.setOrderNeedQty(needQty+orderNeedQty);
                            materialRepertoryEntity.setStayProductionQty(-differenceQty+stayProductionQty);
                            materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                            newSellerProduct(mreList.get(i).getMaterialChildId(),-differenceQty,contractId,createDate,orderId,orderCode,deliveryDate,sequenceNumber+"_"+(i+1),sellerOrderProductId);
                        }
                    }
                }
            }
        }else {
            //待生产
            sellerOrderEntity.setId(orderId);
            sellerOrderEntity.setProgress(2);
            sellerOrderManager.updateByPrimaryKeySelective(sellerOrderEntity);
        }
    }

    @Transactional
    public void updateSellerOrder(Integer id){
        SellerOrderEntity sellerOrderEntity = sellerOrderManager.getEntityById(id);
        PurchaseOrdersStayEntity stayEntity = new PurchaseOrdersStayEntity();
        stayEntity.setOrderId(id);
        stayEntity.setStatus(STATUS_DEFAULT);
        stayEntity.setPurchaseStayStatus(STATUS_INVALID);
        List<PurchaseOrdersStayEntity> stayEntityList = purchaseOrdersStayManager.getListBy(stayEntity);
        if (stayEntityList.size()!=0){
            //循环减掉带采购数和订单需求数
            for (int i=0;i<stayEntityList.size();i++){
                Integer materialId = stayEntityList.get(i).getMaterialId();
                MaterialRepertoryEntity repertoryEntity = new MaterialRepertoryEntity();
                repertoryEntity.setMaterialId(materialId);
                repertoryEntity.setStatus(STATUS_DEFAULT);
                repertoryEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                List<MaterialRepertoryEntity> repertoryEntityList = materialRepertoryManager.getListBy(repertoryEntity);
                if (repertoryEntityList.size()!=0){
                    MaterialRepertoryEntity materialRepertoryEntity = new MaterialRepertoryEntity();
                    //减掉仓库总表里面的带采购数
                    materialRepertoryEntity.setStayPurchaseQty(repertoryEntityList.get(0).getStayPurchaseQty()-stayEntityList.get(i).getStayPurQty());
                    //减掉仓库总表里面的订单需求数
                    materialRepertoryEntity.setOrderNeedQty(repertoryEntityList.get(0).getOrderNeedQty()-stayEntityList.get(i).getOrderNeedQty());
                    materialRepertoryEntity.setId(repertoryEntityList.get(0).getId());
                    materialRepertoryManager.updateByPrimaryKeySelective(materialRepertoryEntity);
                }
            }
            //把所有带采购单变成失效
            purchaseOrdersStayManager.updateStayPurchaseStatusByOrderId(id);
        }
        ProductionPlanEntity planEntity = new ProductionPlanEntity();
        planEntity.setOrderCode(sellerOrderEntity.getOrderCode());
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
            }
            //删除未发料过的生产单
            productionPlanService.deleteProductionPlan(sellerOrderEntity.getOrderCode());
        }
        //把订单产品表里面的状态变成bom待下发
        //sellerOrderProductManager.updateSellerOrderProductStatusByOrderId(id);
        ///todo 把原来已经下发的重新计算
        SellerOrderProductEntity orderProductEntity = new SellerOrderProductEntity();
        orderProductEntity.setOrderId(id);
        orderProductEntity.setMaterialStatus(STATUS_CONFIRM);
        List<SellerOrderProductEntity> orderProductEntityList = sellerOrderProductManager.getListBy(orderProductEntity);
        if (orderProductEntityList.size()!=0){
            List<Integer> ids = new ArrayList<>();
            for (SellerOrderProductEntity sellerOrderProductEntity : orderProductEntityList){
                ids.add(sellerOrderProductEntity.getId());
            }
            Integer purchaseStayNumber = purchaseOrdersStayService.getNextNumber();
            saveConfirmProduct(id,ids);
        }

        //增加日志
        SellerOrderLogEntity logEntity = new SellerOrderLogEntity();
        logEntity.setOrderId(id);
        logEntity.setOperator(LoginFilter.getCurrentUser().getUserName());
        logEntity.setOperation("修改订单");
        logEntity.setContent("销售订单被修改,已下发的不需要重新下发");
        sellerOrderLogManager.insertBy(logEntity);
    }
}
