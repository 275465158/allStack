package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.ReturnedPurchaseEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Richie
 * @version 2018/12/25 14:18
 */
@RestController
@RequestMapping("returnedPurchase")
public class ReturnedPurchaseController extends BaseController<ReturnedPurchaseEntity,ReturnedPurchaseInfo> {
    @Resource
    private ReturnedPurchaseService returnedPurchaseService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Resource
    private MaterialBomParamSpecificationService materialBomParamSpecificationService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private PurchaseOrdersListService purchaseOrdersListService;

    @Override
    protected BaseService<ReturnedPurchaseEntity, ReturnedPurchaseInfo> getBaseService() {
        return returnedPurchaseService;
    }

    @RequestMapping("getListBylike")
    public Result<Pager<ReturnedPurchaseInfo>> listByLike(int pageNum, int pageSize, ReturnedPurchaseInfo returnedPurchaseInfo) {
        ExchangeUtils.convertBlank2Null(returnedPurchaseInfo);
        Pager<ReturnedPurchaseInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, returnedPurchaseInfo, false);
        List<ReturnedPurchaseInfo> rpiList = pager.getList();
        if(rpiList.size() != 0){
            for(ReturnedPurchaseInfo rpi : rpiList){
                MaterialBomEntity materialBomEntity = materialBomService.getEntity(rpi.getMaterialId());
                ProductTypeInfo productTypeInfo = new ProductTypeInfo();
                productTypeInfo.setNumber(materialBomEntity.getNumber());
                productTypeInfo.setCompanyId(materialBomEntity.getCompanyId());
                List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
                if(ptiList.size() != 0){
                    rpi.setNumberType(ptiList.get(0).getType());
                }
                rpi.setOriginalMaterial(materialBomEntity.getOriginalMaterial());
                rpi.setParamName(getParamValue(rpi.getMaterialId()));
            }
        }
        return ResultUtils.success(pager);
    }

    /***
     * 根据采购明细单号查询退货单
     * @param purchaseCode
     * @return
     */
    @RequestMapping("searchList")
    public Result<List<ReturnedPurchaseInfo>> searchList(Integer id,String materialCode){
        /*PurchaseOrdersListInfo poli = purchaseOrdersListService.getModel(id);
        ReturnedPurchaseInfo rpi = new ReturnedPurchaseInfo();
        rpi.setPurchaseOrdersId(poli.getPurchaseOrdersId());*/
        ReturnedPurchaseInfo rpi = new ReturnedPurchaseInfo();
        rpi.setPurchaseOrdersListId(id);
        rpi.setMaterialCode(materialCode);
        List<ReturnedPurchaseInfo> rpiList = returnedPurchaseService.getListBy(rpi);
        return ResultUtils.success(rpiList);
    }

    /****
     * 不合格评审--退货
     * @param returnedPurchaseInfo
     * @return
     */
    @RequestMapping("saveReturnedPurchase")
    public Result<Void> saveReturnedPurchase(ReturnedPurchaseInfo returnedPurchaseInfo){
        returnedPurchaseService.saveReturnedPurchase(returnedPurchaseInfo);
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
}
