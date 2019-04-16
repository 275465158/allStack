package com.ecms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecms.bean.*;
import com.ecms.common.BusinessConstants;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerOrderEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.SellerOrderManager;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ecms.common.BusinessConstants.*;

@RestController
@RequestMapping("seller/order")
public class SellerOrderController extends BaseController<SellerOrderEntity, SellerOrderInfo> {

    private static final Logger logger = LoggerFactory.getLogger(SellerOrderController.class);
    @Value("${env}")
    private String env;
    @Resource
    private SellerOrderService sellerOrderService;
    @Resource
    private SellerOrderManager sellerOrderManager;
    @Resource
    private SellerCustomerService sellerCustomerService;
    @Resource
    private MaterialBomService materialBomService;
    @Resource
    private BomTaskService bomTaskService;
    @Resource
    private BomTaskDetailService bomTaskDetailService;
    @Resource
    private SellerOrderProductService sellerOrderProductService;
    @Resource
    private MaterialSendService materialSendService;
    @Resource
    private MaterialSendDetailService materialSendDetailService;
    @Resource
    private MaterialRepertoryService materialRepertoryService;
    @Resource
    private PurchaseOrdersStayService purchaseOrdersStayService;
    @Resource
    private ProductionPlanService productionPlanService;

    @RequestMapping("table")
    public Result<SellerOrderTable> table(int pageNum, SellerOrderInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        String orderStr = "";
        if (info.getFlag()==1){
            orderStr = "id desc";
            info.setFlag(null);
        }else {
            orderStr = "progress asc,id desc";
            info.setFlag(null);
        }
        Pager<SellerOrderInfo> pager = sellerOrderService.getModelListByLikeOrder(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE, info,orderStr,false);
        SellerOrderTable table = new SellerOrderTable();
        table.setPager(pager);
        return ResultUtils.success(table);
    }

    @RequestMapping("index")
    public Result<SellerOrderIndex> index(@RequestParam(value = "id", required = false, defaultValue = "0") int id) {
        logger.info("---SellerOrderController index, begin");
        SellerOrderIndex index = sellerOrderService.getIndexById(id);
        logger.info("---getIndexById, end");
        if (index.getOrderInfo()!=null){
            String orderCode = index.getOrderInfo().getOrderCode();
            List<SellerOrderProductInfo> sopiList = index.getProductInfos();
            if (sopiList.size()!=0){
                for (int i=0;i<sopiList.size();i++){
                    Integer materialId = sopiList.get(i).getProductId();
                    MaterialRepertoryInfo mri = new MaterialRepertoryInfo();
                    if (materialId!=null){
                        mri.setMaterialId(materialId);
                        mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<MaterialRepertoryInfo> mriList = materialRepertoryService.getListBy(mri);
                        Float inventoryQty = 0f;
                        if (mriList.size()!=0){
                            inventoryQty = mriList.get(0).getInventoryQty();
                            sopiList.get(i).setInventoryQty(inventoryQty);
                        }
                    }else{
                        sopiList.get(i).setInventoryQty(0f);
                    }
                    MaterialSendInfo msi = new MaterialSendInfo();
                    msi.setOrderCode(orderCode);
                    msi.setStatus(STATUS_DEFAULT);
                    msi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<MaterialSendInfo> msiList = materialSendService.getListBy(msi);
                    Float alreadySendQty = 0f;
                    if (msiList.size()!=0){
                        Integer sendId = msiList.get(0).getId();
                        MaterialSendDetailInfo msdi = new MaterialSendDetailInfo();
                        msdi.setMaterialId(materialId);
                        msdi.setSendId(sendId);
                        msdi.setStatus(STATUS_DEFAULT);
                        msdi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        List<MaterialSendDetailInfo> msdiList = materialSendDetailService.getListBy(msdi);
                        if (msdiList.size()!=0){
                            for (int j=0;j<msdiList.size();j++){
                                alreadySendQty = alreadySendQty+msdiList.get(j).getAlreadySendQty();
                            }
                            sopiList.get(i).setAlreadySendQty(alreadySendQty);
                        }
                    }
                    Float stayDeliveryQty = 0f;
                    stayDeliveryQty = sopiList.get(i).getProductNumber()-alreadySendQty;
                    if (stayDeliveryQty<0){
                        sopiList.get(i).setStayDeliveryQty(0f);
                    }else {
                        sopiList.get(i).setStayDeliveryQty(stayDeliveryQty);
                    }
                }
            }
        }
        index.setCustomerInfos(sellerCustomerService.getValidList());
        MaterialBomInfo materialBomInfo = new MaterialBomInfo();
        materialBomInfo.setFlag(1);
        index.setMaterialBomInfos(materialBomService.getListBy(materialBomInfo));
        logger.info("---SellerOrderController index, end");
        return ResultUtils.success(index);
    }

    @RequestMapping("save")
    public Result<Void> save(Integer currentId, HttpServletRequest request) {
        String orderInfoStr = request.getParameter("orderInfo");
        String productInfosStr = request.getParameter("productInfos");
        SellerOrderInfo orderInfo = JSONObject.parseObject(orderInfoStr, SellerOrderInfo.class);
        SellerOrderProductInfo sopi = new SellerOrderProductInfo();
        sopi.setOrderId(orderInfo.getId());
        sopi.setStatus(1);
        //List<SellerOrderProductInfo> oldSopiList = sellerOrderProductService.getListBy(sopi);
        List<SellerOrderProductInfo> productInfos = JSONObject.parseArray(productInfosStr, SellerOrderProductInfo.class);
        SellerOrderIndex index = new SellerOrderIndex();
        index.setOrderInfo(orderInfo);
        index.setProductInfos(productInfos);
        index.setCurrentId(currentId);
        saveOrder(index);
        if (index.getCurrentId()!=0){
            //sellerOrderService.update(index,oldSopiList);
            sellerOrderService.updateSellerOrder(orderInfo.getId());
        }
        Integer orderId = index.getOrderInfo().getId();
        orderInfo.setProgress(1);
        orderInfo.setId(orderId);
        //根据订单产品里面的判断订单是否已完成
        orderInfo.setSellerOrderStatus(STATUS_DEFAULT);


        SellerOrderProductInfo productInfo = new SellerOrderProductInfo();
        productInfo.setOrderId(orderId);
        productInfo.setStatus(STATUS_DEFAULT);
        productInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<SellerOrderProductInfo> productInfoList = sellerOrderProductService.getListBy(productInfo);
        if (productInfoList.size()!=0){
            for (int i=0;i<productInfoList.size();i++){
                if (productInfoList.get(i).getProductId()==null){
                    SellerOrderProductInfo sellerOrderProductInfo = new SellerOrderProductInfo();
                    sellerOrderProductInfo.setId(productInfoList.get(i).getId());
                    sellerOrderProductInfo.setMaterialStatus(STATUS_INVALID);
                    sellerOrderProductService.updateByPrimaryKeySelective(sellerOrderProductInfo);
                }
                if (productInfoList.get(i).getMaterialStatus()<2){
                    orderInfo.setSellerOrderStatus(STATUS_INVALID);
                }
            }
            sellerOrderService.updateByPrimaryKeySelective(orderInfo);
        }
        return ResultUtils.success();
    }

    @Override
    protected BaseService<SellerOrderEntity, SellerOrderInfo> getBaseService() {
        return sellerOrderService;
    }

    public Result<Void> saveOrder(@RequestBody SellerOrderIndex index){
        SellerOrderEntity soe = new SellerOrderEntity();
        soe.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        soe.setContractId(index.getOrderInfo().getContractId());
        Long resultSize = sellerOrderManager.getTotal(soe);
        if(index.getCurrentId() == 0){
            if ( !"".equals(index.getOrderInfo().getContractId())){
                if(resultSize == 0){
                    return saveOrUpdateOrder(index);
                }else{
                    throw new CommonBusinessException("合同编号不能重复！");
                }
            }else{
                return saveOrUpdateOrder(index);
            }
        }else{
            return saveOrUpdateOrder(index);
        }
    }

    private Result<Void> saveOrUpdateOrder(@RequestBody SellerOrderIndex index) {
        index = sellerOrderService.save(index);
        Integer sellerOrderId = index.getOrderInfo().getId();
        String contractId = index.getOrderInfo().getContractId();
        SellerOrderIndex orderIndex = sellerOrderService.getIndexById(sellerOrderId);

        BomTaskInfo taskInfo = new BomTaskInfo();
        taskInfo.setPurchaseId(contractId);
        taskInfo.setOrderId(sellerOrderId);
        List<BomTaskInfo> btiList = bomTaskService.getListBy(taskInfo);
        if(btiList.size() != 0){
            taskInfo.setId(btiList.get(0).getId());
        }
        taskInfo.setStartDate(index.getOrderInfo().getCreateDate());
        BomTaskInfo savedBomTaskInfo = bomTaskService.saveOrUpdate(taskInfo);
        orderIndex.getProductInfos().stream().forEach(product->{
            BomTaskDetailInfo detailInfo = new BomTaskDetailInfo();
            detailInfo.setTaskId(savedBomTaskInfo.getId());
            detailInfo.setDraftName(product.getProductName());
            detailInfo.setMaterialId(product.getProductId());
            bomTaskDetailService.saveOrUpdate(detailInfo);
        });
        return ResultUtils.success();
    }

    @RequestMapping("confirm")
    public Result<Void> confirm(int id) {
        Integer purchaseStayNumber = purchaseOrdersStayService.getNextNumber();
        SellerOrderIndex orderIndex = sellerOrderService.getIndexById(id);
        List<SellerOrderProductInfo> requestProductList = orderIndex.getProductInfos();
        if (requestProductList.size()!=0){
            for (int i=0;i<requestProductList.size();i++){
                if (requestProductList.get(i).getProductId()==null){
                    throw new CommonBusinessException("订单中有产品未添加");
                }
            }
            sellerOrderService.saveConfirm(id,purchaseStayNumber);
        }
        return ResultUtils.success();
    }

    @RequestMapping("getContractIdList")
    public Result<List<SellerOrderEntity>> getContractIdList(SellerOrderInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        List<SellerOrderEntity> soeList = sellerOrderService.getContractIdList(info);
        return ResultUtils.success(soeList);
    }

    protected String getRedisNumberKeyPrefix() {
        return null;
    }

    @RequestMapping("confirmProduct")
    public Result<Void> confirmProduct(Integer id,SellerOrderInfo info){
        List<Integer> sellerOrderProductIds = info.getSellerOrderProductIds();
        for (int i=0;i<sellerOrderProductIds.size();i++){
            SellerOrderProductInfo sopi = sellerOrderProductService.getModel(sellerOrderProductIds.get(i));
            MaterialBomInfo mbi = materialBomService.getModel(sopi.getProductId());
            if (sopi.getMaterialStatus()==2){
                throw new CommonBusinessException("物料编码为"+mbi.getMaterialCode()+"的产品已经确认");
            }
        }
        sellerOrderService.saveConfirmProduct(id,sellerOrderProductIds);
        SellerOrderProductInfo productInfo = new SellerOrderProductInfo();
        productInfo.setOrderId(id);
        productInfo.setStatus(STATUS_DEFAULT);
        productInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        List<SellerOrderProductInfo> productInfoList = sellerOrderProductService.getListBy(productInfo);
        Integer sellerOrderStatus = 1;
        if (productInfoList.size()!=0){
            for (int i=0;i<productInfoList.size();i++){
                if (productInfoList.get(i).getMaterialStatus()==0 || productInfoList.get(i).getMaterialStatus()==1){
                    sellerOrderStatus = 0;
                    continue;
                }
            }
        }
        //更新订单状态
        SellerOrderInfo orderInfo = new SellerOrderInfo();
        orderInfo.setSellerOrderStatus(sellerOrderStatus);
        orderInfo.setId(id);
        sellerOrderService.updateByPrimaryKeySelective(orderInfo);
        return ResultUtils.success();
    }

    @RequestMapping("bomPager")
    public Result<SellerOrderTable> bomPager(int pageNum, SellerOrderInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        String orderStr = "";
        if (info.getFlag()==1){
            orderStr = "id desc";
            info.setFlag(null);
        }else {
            orderStr = "seller_order_status asc,id desc";
            info.setFlag(null);
        }
        Pager<SellerOrderInfo> pager = sellerOrderService.getModelListByLikeOrder(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE, info,orderStr,false);
        List<SellerOrderInfo> sellerOrderInfoList = pager.getList();
        if (sellerOrderInfoList.size()!=0){
            for (int i=0;i<sellerOrderInfoList.size();i++){
                if (sellerOrderInfoList.get(i).getSellerOrderStatus() == 1){
                    sellerOrderInfoList.get(i).setSellerStatus("BOM制作完成");
                }else {
                    sellerOrderInfoList.get(i).setSellerStatus("BOM制作中");
                }
            }
        }
        SellerOrderTable table = new SellerOrderTable();
        table.setPager(pager);
        return ResultUtils.success(table);
    }

    @RequestMapping("/getUnfinishedSellerList")
    public Result<List<SellerOrderInfo>> getUnfinishedSellerList(Integer productId) {
        List<Integer> sopiList = sellerOrderProductService.getListByStatus(productId);
        HashSet h = new HashSet(sopiList);
        sopiList.clear();
        sopiList.addAll(h);
        Integer orderId = null;
        List<SellerOrderInfo> soiList = new ArrayList<SellerOrderInfo>();
        for(int i=0;i<sopiList.size();i++){
            SellerOrderInfo soi = sellerOrderService.getModel(sopiList.get(i));
            soiList.add(soi);
        }
        return ResultUtils.success(soiList);
    }

}
