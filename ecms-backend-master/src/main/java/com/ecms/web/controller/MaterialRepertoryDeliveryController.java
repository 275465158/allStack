package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.*;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

/**
 * 收货
 * @author Richie
 * @version 2018/10/25 17:19
 */
@RestController
@RequestMapping("materialRepertoryDelivery")
public class MaterialRepertoryDeliveryController extends BaseController<MaterialRepertoryDeliveryEntity, MaterialRepertoryDeliveryInfo> {

    @Resource
    private MaterialRepertoryDeliveryService materialRepertoryDeliveryService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Override
    protected BaseService<MaterialRepertoryDeliveryEntity, MaterialRepertoryDeliveryInfo> getBaseService() {
        return materialRepertoryDeliveryService;
    }

    @RequestMapping("/insertDelivery")
    public Result<Void> insertDelivery(MaterialRepertoryDeliveryInfo materialRepertoryDeliveryInfo){
        Integer deliveryNumber = materialRepertoryDeliveryService.getNextNumber();
        materialRepertoryDeliveryService.insertDelivery(materialRepertoryDeliveryInfo,deliveryNumber);
        return ResultUtils.success();
    }

    @RequestMapping("/searchList")
    public Result<List<MaterialRepertoryDeliveryInfo>> searchList(Integer id,String materialCode){
        /*PurchaseOrdersListInfo poli = purchaseOrdersListService.getModel(id);
        PurchaseOrdersInfo poi = purchaseOrdersService.getModel(poli.getPurchaseOrdersId());
        MaterialRepertoryDeliveryInfo mrdi = new MaterialRepertoryDeliveryInfo();
        mrdi.setPurchaseCode(poi.getPurchaseCode());*/
        MaterialRepertoryDeliveryInfo mrdi = new MaterialRepertoryDeliveryInfo();
        mrdi.setPurchaseOrdersListId(id);
        mrdi.setStatus(1);
        mrdi.setMaterialCode(materialCode);
        List<MaterialRepertoryDeliveryInfo> mrdiList = materialRepertoryDeliveryService.getListBy(mrdi);
        return ResultUtils.success(mrdiList);
    }

    /**
     * 获取待入库待检测列表
     * @param pageNum
     * @param pageSize
     * @param materialRepertoryDeliveryInfo
     * @return
     */
    @RequestMapping("/getStayInboundPage")
    public Result<Pager<MaterialRepertoryDeliveryInfo>> getStayInboundPage(int pageNum,int pageSize,MaterialRepertoryDeliveryInfo materialRepertoryDeliveryInfo){
        ExchangeUtils.convertBlank2Null(materialRepertoryDeliveryInfo);
        materialRepertoryDeliveryInfo.setDeliveryStatus(STATUS_DEFAULT);
        materialRepertoryDeliveryInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        Pager<MaterialRepertoryDeliveryInfo> pager = materialRepertoryDeliveryService.getModelListByLike(pageNum,pageSize, materialRepertoryDeliveryInfo, true);
        List<MaterialRepertoryDeliveryInfo> materialRepertoryDeliveryInfoList = pager.getList();
        if(materialRepertoryDeliveryInfoList.size() != 0 ){
            for(MaterialRepertoryDeliveryInfo mrdi : materialRepertoryDeliveryInfoList){
                Integer materialId = mrdi.getMaterialId();
                String materialParamValue = getParamValue(materialId);
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mrdi.setOriginalMaterial(mbe.getOriginalMaterial());
                mrdi.setParamValue(materialParamValue);
                Integer supplierId = mrdi.getSupplierId();
                SupplierEntity se = supplierService.getEntity(supplierId);
                mrdi.setSupplierName(se.getSupplierName());
                mrdi.setEmployeeId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
                mrdi.setCompletedQty(mrdi.getInventoryQty());
            }
        }
        return ResultUtils.success(pager);
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
