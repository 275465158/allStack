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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

/**
 * 仓库物料明细controller
 * @author Richie
 * @version 2018/10/25 17:19
 */
@RestController
@RequestMapping("materialRepertoryDetail")
public class MaterialRepertoryDetailController extends BaseController<MaterialRepertoryDetailEntity, MaterialRepertoryDetailInfo> {

    @Resource
    private MaterialRepertoryDetailService materialRepertoryDetailService;

    @Resource
    private MaterialRepertoryService materialRepertoryService;

    @Resource
    private MaterialBomService materialBomService;

    @Resource
    private MaterialBomParamNameService materialBomParamNameService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private RepertoryService repertoryService;

    @Resource
    private ProductTypeService productTypeService;

    @Resource
    private EmployeeService employeeService;

    @Override
    protected BaseService<MaterialRepertoryDetailEntity, MaterialRepertoryDetailInfo> getBaseService() {
        return materialRepertoryDetailService;
    }

    /**
     * 模糊查询列表
     * @param pageNum
     * @param pageSize
     * @param info
     * @return
     */
    @RequestMapping("listByLike")
    public Result<Pager<MaterialRepertoryDetailInfo>> listByLike(int pageNum, int pageSize, MaterialRepertoryDetailInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        if(pageNum == 1){
            pageNum = 0;
        }else{
            pageNum = pageNum*pageSize-pageSize;
        }
        ExchangeUtils.convertBlank2Null(info);
        Pager<MaterialRepertoryDetailInfo> pager = materialRepertoryDetailService.getModelListByLike(pageNum,pageSize,info,false);
        Integer repertoryId = info.getRepertoryId();
        String materialCode = info.getMaterialCode();
        String materialName = info.getMaterialName();
        String storageLocation = info.getStorageLocation();
        String factoryMaterialCode = info.getFactoryMaterialCode();
        String number = info.getNumber();
        String originalMaterial = info.getOriginalMaterial();
        String materialBomParamValueStr = info.getMaterialBomParamValueStr();
        String drawingCode = info.getDrawingCode();
        String source = info.getSource();
        List<String> mbpvList = new ArrayList<>();
        List<String> fmcList = new ArrayList<>();
        List<String> dcList = new ArrayList<>();
        factoryMaterialCode = getParamLikeString(factoryMaterialCode, fmcList);
        drawingCode = getParamLikeString(drawingCode, dcList);
        materialBomParamValueStr = getParamLikeString(materialBomParamValueStr, mbpvList);
        List<MaterialRepertoryDetailInfo> mrdiList = materialRepertoryDetailService.getListByLike(pageNum, pageSize, repertoryId,
                materialCode,materialName,storageLocation,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
        List<MaterialRepertoryDetailInfo> getPageSizeList = materialRepertoryDetailService.getPageSizeList(repertoryId,materialCode,
                materialName,storageLocation,factoryMaterialCode,number,originalMaterial,materialBomParamValueStr,source,drawingCode);
        if(mrdiList.size() != 0){
            for(MaterialRepertoryDetailInfo mrdi : mrdiList){
                MaterialBomInfo mbi = new MaterialBomInfo();
                mbi.setId(mrdi.getMaterialId());
                List<MaterialBomInfo> mbe = materialBomService.getListBy(mbi);
                if(mbe.size() != 0){
                    mrdi.setOriginalMaterial(mbe.get(0).getOriginalMaterial());
                    mrdi.setMaterialUnit(mbe.get(0).getMaterialUnit());
                    mrdi.setSource(mbe.get(0).getSource());
                }
                mrdi.setMaterialParam(getParamValue(mrdi.getMaterialId()));
                MaterialRepertoryInfo mri = new MaterialRepertoryInfo();
                mri.setMaterialId(mrdi.getMaterialId());
                List<MaterialRepertoryInfo> materialRepertoryInfoList = materialRepertoryService.getListBy(mri);
                if(materialRepertoryInfoList.size() != 0){
                    mrdi.setStayPurchaseQty(materialRepertoryInfoList.get(0).getStayPurchaseQty());
                    mrdi.setOrderNeedQty(materialRepertoryInfoList.get(0).getOrderNeedQty());
                    mrdi.setStayDeliveredQty(materialRepertoryInfoList.get(0).getStayDeliveredQty());
                    mrdi.setInventorySafeQty(materialRepertoryInfoList.get(0).getInventorySafeQty());
                    mrdi.setStayInboundQty(materialRepertoryInfoList.get(0).getStayInboundQty());
                }
            }
        }
        pager.setList(mrdiList);
        int sizeTotal = getPageSizeList.size();
        pager.setTotal(sizeTotal);
        if(pager.getPageNum() == 0){
            pager.setPageNum(1);
        }
        if(sizeTotal <= pageSize){
            pager.setPages(1);
        }else{
            int pages = getPageSizeList.size()%pageSize==0?getPageSizeList.size()/pageSize:getPageSizeList.size()/pageSize+1;
            pager.setPages(pages);
        }
        return ResultUtils.success(pager);
    }

    private String getParamLikeString(String paramStr, List<String> pstrList) {
        if (paramStr != null) {
            String[] mbpvArr = paramStr.split("\\s+");
            if (mbpvArr.length != 0) {
                for (int i = 0; i < mbpvArr.length; i++) {
                    pstrList.add(mbpvArr[i]);
                }
                if (pstrList.size() != 0) {
                    paramStr = "";
                    for (int i = 0; i < pstrList.size(); i++) {
                        if (i < pstrList.size() - 1) {
                            paramStr += pstrList.get(i) + "%";
                        } else {
                            paramStr += pstrList.get(i);
                        }

                    }
                }
            }
        }
        return paramStr;
    }

    /**
     * 模糊查询明细列表
     * @param pageNum
     * @param pageSize
     * @param info
     * @return
     */
    @RequestMapping("detailListByLike")
    public Result<Pager<MaterialRepertoryDetailInfo>> detailListByLike(int pageNum, int pageSize, MaterialRepertoryDetailInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        Pager<MaterialRepertoryDetailInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
        List<MaterialRepertoryDetailInfo> mrdiList = pager.getList();
        if(mrdiList.size() != 0){
            Float useQty = 0f;
            for(MaterialRepertoryDetailInfo mrdi : mrdiList){
                Integer materialId = mrdi.getMaterialId();
                useQty = materialRepertoryDetailService.getUseQty(mrdi.getId());
                mrdi.setInventoryQty(mrdi.getInventoryQty()+useQty);
                MaterialBomEntity mbe = materialBomService.getEntity(materialId);
                mrdi.setOriginalMaterial(mbe.getOriginalMaterial());
                mrdi.setMaterialParam(getParamValue(materialId));
                mrdi.setMaterialUnit(mbe.getMaterialUnit());

                Integer emplyeeId = mrdi.getOperatingPersonnelId();
                EmployeeEntity ee = employeeService.getEntity(emplyeeId);
                mrdi.setOperatingPersonnelName(ee.getName());

                if(mrdi.getSupplierId() != null && !"".equals(mrdi.getSupplierId())){
                    SupplierEntity se = supplierService.getEntity(mrdi.getSupplierId());
                    mrdi.setSupplierName(se.getSupplierName());
                }
            }
        }
        Iterator<MaterialRepertoryDetailInfo> it = mrdiList.iterator();
        while(it.hasNext()){
            Float inventoryQty = it.next().getInventoryQty();
            if(inventoryQty.toString().equals("0.0")){
                it.remove();
            }else{
                if(inventoryQty < 0){
                    it.remove();
                }
            }
        }
        return ResultUtils.success(pager);
    }

    /**
     * 获取明细列表
     * @param info
     * @return
     */
    @RequestMapping("getDetailList")
    public Result<List<MaterialRepertoryDetailInfo>> getMrdiList(MaterialRepertoryDetailInfo info) {
        info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        ExchangeUtils.convertBlank2Null(info);
        List<MaterialRepertoryDetailInfo> mrdiList = materialRepertoryDetailService.getDetailList(info.getMaterialId(),info.getCompanyId());
        if(mrdiList.size() != 0){
            for(MaterialRepertoryDetailInfo mrdi : mrdiList){
                RepertoryEntity pe = repertoryService.getEntity(mrdi.getRepertoryId());
                mrdi.setRepertoryName(pe.getRepertoryName());
            }
        }
        return ResultUtils.success(mrdiList);
    }

    /**
     * 获取发料明细列表
     * @param materialRepertoryDetailInfo
     * @return
     */
    @RequestMapping("getIssueMaterialList")
    public Result<List<MaterialRepertoryDetailInfo>> getIssueMaterialList(int pageNum, int pageSize,MaterialRepertoryDetailInfo materialRepertoryDetailInfo) {
        Float surplusNeedQty = materialRepertoryDetailInfo.getSurplusNeedQty();
        materialRepertoryDetailInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
        materialRepertoryDetailInfo.setSurplusNeedQty(null);
        ExchangeUtils.convertBlank2Null(materialRepertoryDetailInfo);
        //根据时间正序排序，先入库的先出库
        List<MaterialRepertoryDetailInfo> mrdiList = materialRepertoryDetailService.getListByOrder(materialRepertoryDetailInfo);
//        Pager<MaterialRepertoryDetailInfo> mrdiPager = materialRepertoryDetailService.getModelListByLikeOrder(pageNum,100000000,materialRepertoryDetailInfo,created,false);
        if(mrdiList.size() != 0){
            Float useQty = 0f;
            for(MaterialRepertoryDetailInfo mrdi : mrdiList){
                useQty = materialRepertoryDetailService.getUseQty(mrdi.getId());
                mrdi.setInventoryQty(mrdi.getInventoryQty()+useQty);
                RepertoryEntity pe = repertoryService.getEntity(mrdi.getRepertoryId());
                mrdi.setRepertoryName(pe.getRepertoryName());
                if(mrdi.getSupplierId() != null){
                    SupplierEntity se = supplierService.getEntity(mrdi.getSupplierId());
                    mrdi.setSupplierName(se.getSupplierName());
                }
                if(mrdi.getMaterialId() != null){
                    MaterialBomInfo mbi = materialBomService.getModel(mrdi.getMaterialId());
                    if(mbi.getNumber() != null){
                        ProductTypeInfo productTypeInfo = new ProductTypeInfo();
                        productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                        productTypeInfo.setStatus(STATUS_DEFAULT);
                        productTypeInfo.setNumber(mbi.getNumber());
                        List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
                        if(ptiList.size() != 0){
                            mrdi.setNumberType(ptiList.get(0).getType());
                        }
                    }
                }
            }
            //删除库存为0的记录，不返回到页面
            Iterator<MaterialRepertoryDetailInfo> it = mrdiList.iterator();
            while(it.hasNext()){
                String x = it.next().getInventoryQty().toString();
                if(x.equals("0.0")){
                    it.remove();
                }else{
                    if(Float.parseFloat(x) < 0){
                        it.remove();
                    }
                }
            }
            //根据剩余发料数量和库存数，默认自动计算发料
            if(mrdiList.size() != 0){
                Float inventoryQty = 0f;
                for(MaterialRepertoryDetailInfo mrdi :mrdiList){
                    inventoryQty = mrdi.getInventoryQty();
                    if(inventoryQty < surplusNeedQty){
                        mrdi.setProductionIssueQty(inventoryQty);
                        surplusNeedQty = surplusNeedQty-inventoryQty;
                    }else{
                        mrdi.setProductionIssueQty(surplusNeedQty);
                        break;
                    }
                }
            }

        }
        return ResultUtils.success(mrdiList);
    }

    /**
     * 出库保存
     * @param info
     */
    @RequestMapping("saveOutbound")
    public Result<Void> saveOutbound(MaterialRepertoryDetailInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        materialRepertoryDetailService.saveOutbound(info);
        return ResultUtils.success();
    }

    /**
     * 保存入库
     * @param info
     */
    @RequestMapping("saveInboundInfo")
    public Result<Void> saveInboundInfo(MaterialRepertoryDetailInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        materialRepertoryDetailService.saveInboundInfo(info);
        return ResultUtils.success();
    }

    /**
     * 保存物料移位
     * @param info
     * @return
     */
    @RequestMapping("saveShiftPosition")
    public Result<Void> saveShiftPosition(MaterialRepertoryDetailInfo info) {
        ExchangeUtils.convertBlank2Null(info);
        materialRepertoryDetailService.saveShiftPosition(info);
        return ResultUtils.success();
    }

    /**
     * 待入库列表入库
     * @param info
     */
    @RequestMapping("insertInbound")
    public Result<Void> insertInbound(MaterialRepertoryDetailInfo info){
        ExchangeUtils.convertBlank2Null(info);
        materialRepertoryDetailService.insertInbound(info);
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

    /**
     * 移动端接口,模糊查询列表
     * @param pageNum
     * @param pageSize
     * @param concatFieldStr 参数
     * @param repertoryId
     * @return
     */
    @RequestMapping("listByLikeForMobile")
    public Result<Pager<MaterialRepertoryDetailInfo>> listByLikeForMobile(int pageNum, int pageSize, String concatFieldStr,Integer repertoryId) {
        if(pageNum == 1){
            pageNum = 0;
        }else{
            pageNum = pageNum*pageSize-pageSize;
        }
        MaterialRepertoryDetailInfo materialRepertoryDetailInfo = new MaterialRepertoryDetailInfo();
        Pager<MaterialRepertoryDetailInfo> pager = materialRepertoryDetailService.getModelListByLike(pageNum,pageSize,materialRepertoryDetailInfo,false);
        String [] concatFieldArr = concatFieldStr.split("\\s+");
        List<String> concatFieldList = Arrays.asList(concatFieldArr);
        List<MaterialRepertoryDetailInfo> mrdiList = materialRepertoryDetailService.getMobileListByLike(pageNum, pageSize, repertoryId, concatFieldList);
        List<MaterialRepertoryDetailInfo> getPageSizeList = materialRepertoryDetailService.getMobilePageListByLike(repertoryId, concatFieldList);
        if(mrdiList.size() != 0){
            for(MaterialRepertoryDetailInfo mrdi : mrdiList){
                MaterialBomInfo mbi = new MaterialBomInfo();
                mbi.setId(mrdi.getMaterialId());
                List<MaterialBomInfo> mbe = materialBomService.getListBy(mbi);
                if(mbe.size() != 0){
                    mrdi.setOriginalMaterial(mbe.get(0).getOriginalMaterial());
                    mrdi.setMaterialUnit(mbe.get(0).getMaterialUnit());
                    mrdi.setSource(mbe.get(0).getSource());
                }
                mrdi.setMaterialParam(getParamValue(mrdi.getMaterialId()));
                MaterialRepertoryInfo mri = new MaterialRepertoryInfo();
                mri.setMaterialId(mrdi.getMaterialId());
                List<MaterialRepertoryInfo> materialRepertoryInfoList = materialRepertoryService.getListBy(mri);
                if(materialRepertoryInfoList.size() != 0){
                    mrdi.setStayPurchaseQty(materialRepertoryInfoList.get(0).getStayPurchaseQty());
                    mrdi.setOrderNeedQty(materialRepertoryInfoList.get(0).getOrderNeedQty());
                    mrdi.setStayDeliveredQty(materialRepertoryInfoList.get(0).getStayDeliveredQty());
                    mrdi.setInventorySafeQty(materialRepertoryInfoList.get(0).getInventorySafeQty());
                    mrdi.setStayInboundQty(materialRepertoryInfoList.get(0).getStayInboundQty());
                }
            }
        }
        pager.setList(mrdiList);
        int sizeTotal = getPageSizeList.size();
        pager.setTotal(sizeTotal);
        if(pager.getPageNum() == 0){
            pager.setPageNum(1);
        }
        if(sizeTotal <= pageSize){
            pager.setPages(1);
        }else{
            int pages = getPageSizeList.size()%pageSize==0?getPageSizeList.size()/pageSize:getPageSizeList.size()/pageSize+1;
            pager.setPages(pages);
        }
        return ResultUtils.success(pager);
    }

}
