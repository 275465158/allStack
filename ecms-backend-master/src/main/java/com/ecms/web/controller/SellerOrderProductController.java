package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerOrderProductEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("sellerOrderProduct")
public class SellerOrderProductController extends BaseController<SellerOrderProductEntity, SellerOrderProductInfo> {

    @Resource
    private SellerOrderProductService sellerOrderProductService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Resource
    private MaterialRepertoryService materialRepertoryService;

    @Resource
    private MaterialSendDetailService materialSendDetailService;

    @Resource
    private SellerOrderService sellerOrderService;

     @Override
    protected BaseService<SellerOrderProductEntity, SellerOrderProductInfo> getBaseService() {
        return sellerOrderProductService;
    }

    @RequestMapping("searchListById")
    public Result<List<SellerOrderProductInfo>> searchListById(Integer id){
        SellerOrderProductInfo sopi = new SellerOrderProductInfo();
        sopi.setOrderId(id);
        sopi.setStatus(1);
        List<SellerOrderProductInfo> sopiList = sellerOrderProductService.getListBy(sopi);
        if (sopiList.size()!=0){
            for (int i=0;i<sopiList.size();i++){
                Integer materialId = sopiList.get(i).getProductId();
                if (materialId != null){
                    MaterialBomInfo mbi = materialBomService.getModel(materialId);
                    //获取物料类型
                    ProductTypeInfo pti = new ProductTypeInfo();
                    pti.setNumber(mbi.getNumber());
                    pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                    List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
                    if (ptiList.size()!=0){
                        mbi.setType(ptiList.get(0).getType());
                    }
                    //获取物料参数
                    mbi.setMaterialBomParamValueStr(getParamValue(materialId));
                    sopiList.get(i).setMaterialBomInfo(mbi);
                    //获取物料库存
                    MaterialRepertoryInfo mri = new MaterialRepertoryInfo();
                    mri.setMaterialId(materialId);
                    List<MaterialRepertoryInfo> mriList = materialRepertoryService.getListBy(mri);
                    if (mriList.size()!=0){
                        sopiList.get(i).setInventoryQty(mriList.get(0).getInventoryQty());
                    }
                    //获取已发数量
                    SellerOrderInfo soi = sellerOrderService.getModel(sopiList.get(i).getOrderId());
                    Float alreadySendQty = materialSendDetailService.getAlreadySendQty(LoginFilter.getCurrentUser().getCompanyId(),soi.getContractId(),soi.getOrderCode(),materialId,sopiList.get(i).getId());
                    sopiList.get(i).setAlreadySendQty(alreadySendQty);
                }
            }
        }
        return ResultUtils.success(sopiList);
    }

    @RequestMapping("/editNewProduct")
    public Result<Void> editNewProduct(Integer orderId,Integer materialId,Integer sequenceNumber){
        sellerOrderProductService.editNewProduct(orderId,materialId,sequenceNumber);
        return ResultUtils.success();
    }

    /**
     * 获取拼接的参数值
     * @param id
     * @return
     */
    public String getParamValue(Integer id){
        //根据子id去查部件-参数表
        MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
        materialBomParamNameInfo.setMaterialId(id);
        materialBomParamNameInfo.setStatus(1);
        List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
        String materialBomParamNameValueStr = "";
        if (mbpniList.size()!=0){
            for (int j = 0;j < mbpniList.size(); j++){
                if (j<mbpniList.size()-1) {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
                }else {
                    materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
                }
            }
        }
        if (materialBomParamNameValueStr != ""){
            return (materialBomParamNameValueStr);
        }else {
            return ("");
        }
    }

    @RequestMapping("getOrderNeedList")
    public Result<List<SellerOrderProductInfo>> getOrderNeedList(SellerOrderProductInfo sellerOrderProductInfo){
        ExchangeUtils.convertBlank2Null(sellerOrderProductInfo);
        List<SellerOrderProductInfo> sopiList = sellerOrderProductService.getListBy(sellerOrderProductInfo);
        return ResultUtils.success(sopiList);
    }
}
