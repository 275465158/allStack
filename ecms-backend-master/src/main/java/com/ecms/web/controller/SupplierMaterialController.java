package com.ecms.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.ecms.bean.*;
import com.ecms.service.*;
import com.ecms.web.filter.LoginFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SupplierMaterialEntity;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.utils.SupplierMaterialEditor;

@RestController
@RequestMapping("supplier/material")
public class SupplierMaterialController extends BaseController<SupplierMaterialEntity, SupplierMaterialInfo> {

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private SupplierMaterialService service;

	@Resource
	private MaterialBomService materialBomService;
	@Resource
	private SupplierService supplierService;
	@Resource
	private PurchasePaymentTypeService purchasePaymentTypeService;
	@Resource
	private PurchaseInvoiceTypeService purchaseInvoiceTypeService;
	@Resource
	private PurchasePaymentPeriodService purchasePaymentPeriodService;
	@Resource
	private PurchaseShippingMethodService purchaseShippingMethodService;

	@Override
	protected BaseService<SupplierMaterialEntity, SupplierMaterialInfo> getBaseService() {
		return service;
	}

	@Override
	@RequestMapping("list")
	public Result<Pager<SupplierMaterialInfo>> list(int pageNum, SupplierMaterialInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		Pager<SupplierMaterialInfo> pager = service.getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		return ResultUtils.success(pager);
	}

	@RequestMapping("save")
	public Result<Void> saveList(SupplierMaterialModal materialList) {
		materialList.getMaterialList().stream().forEach(material -> {
			service.saveOrUpdate(material);
		});
		return ResultUtils.success();
	}

	@RequestMapping("getPrice")
	public Result<List<SupplierMaterialInfo>> getPrice(Integer materialId,Integer supplierId){
		SupplierMaterialInfo smi = new SupplierMaterialInfo();
		smi.setSupplierId(supplierId);
		smi.setMaterialId(materialId);
		smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SupplierMaterialInfo> list = service.getListBy(smi);
		return ResultUtils.success(list);
	}

	@RequestMapping("updatePrice")
	public Result<SupplierMaterialInfo> updatePrice(SupplierMaterialInfo materialInfo){
		List<Map<String,String>> priceMap = materialInfo.getPriceMap();
		if (priceMap.size()!=0){
			for (int i=0;i<priceMap.size();i++){
				Map<String,String> map = priceMap.get(i);
				SupplierMaterialInfo supplierMaterialInfo = new SupplierMaterialInfo();
				supplierMaterialInfo.setId(Integer.parseInt(map.get("id")));
				supplierMaterialInfo.setPrice(Float.parseFloat(map.get("price")));
				service.saveOrUpdate(supplierMaterialInfo);
			}
		}
		return ResultUtils.success();
	}

	/**
	 * @param jsonStr
	 * @param supplierId
	 * @return
	 * @author pratice
	 * @date 2018年10月29日
	 */
	
    @Transactional
    @RequestMapping("saveSupplierMaterial")
	public Result<SupplierMaterialInfo> saveSupplierMaterial(String jsonStr, Integer supplierId) {
		service.deleteBySupplierId(supplierId);
		List<SupplierMaterialInfo> supplierMateriallist = JSON.parseArray(jsonStr, SupplierMaterialInfo.class);
		for (int i = 0; i < supplierMateriallist.size(); i++) {
			Integer materialId = supplierMateriallist.get(i).getMaterialId();
			MaterialBomInfo mbi = materialBomService.getModel(materialId);
			supplierMateriallist.get(i).setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
			supplierMateriallist.get(i).setMaterialName(mbi.getMaterialName());
			supplierMateriallist.get(i).setMaterialCode(mbi.getMaterialCode());
			supplierMateriallist.get(i).setOriginalMaterial(mbi.getOriginalMaterial());
			supplierMateriallist.get(i).setType(mbi.getType());
			supplierMateriallist.get(i).setFactoryMaterialCode(mbi.getFactoryMaterialCode());
			supplierMateriallist.get(i).setId(null);
			supplierMateriallist.get(i).setSupplierId(supplierId);
			service.saveOrUpdate(supplierMateriallist.get(i));
		}
		return ResultUtils.success();

	}

	/**
	 * 往供应商里面插入数据
	 * @param supplierMaterialInfo
	 * @return
	 */
	@RequestMapping("saveSupplierMaterialMany")
	public Result<Void> saveSupplierMaterialMany(SupplierMaterialInfo supplierMaterialInfo){
		List<Map<String,String>> materialMap = supplierMaterialInfo.getMaterialMap();
		if (materialMap.size()!=0){
			for(int i=0;i<materialMap.size();i++){
				Map<String,String> map = materialMap.get(i);
				SupplierMaterialInfo smi = new SupplierMaterialInfo();
				MaterialBomInfo mbi = materialBomService.getModel(Integer.parseInt(map.get("materialId")));
				smi.setMaterialId(Integer.parseInt(map.get("materialId")));
				Float minQty = 0f;
				if (map.get("minQty")!=""){
					minQty = Float.parseFloat(map.get("minQty"));
				}
				smi.setMinQty(minQty);
				smi.setPrice(Float.parseFloat(map.get("price")));
				String purchaseDays = "0";
				if (map.get("purchaseDays")!=""){
					purchaseDays = map.get("purchaseDays");
				}
				smi.setPurchaseDays(purchaseDays);
				String purchaseMethod = "0";
				if (map.get("purchaseMethod")!=""){
					purchaseMethod = map.get("purchaseMethod");
				}
				smi.setPurchaseMethod(purchaseMethod);
				smi.setSupplierId(Integer.parseInt(map.get("supplierId")));
				Double weight = 0.0;
				if (map.get("weight") != ""){
					weight = Double.parseDouble(map.get("weight"));
				}
				smi.setWeight(weight);
				smi.setMaterialCode(mbi.getMaterialCode());
				smi.setMaterialName(mbi.getMaterialName());
				smi.setOriginalMaterial(mbi.getOriginalMaterial());
				smi.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
				smi.setType(mbi.getType());
				smi.setFactoryMaterialCode(mbi.getFactoryMaterialCode());
				smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				service.insertBy(smi);
			}
		}
    	return ResultUtils.success();
	}

	/**
	 * 修改供应商里面的物料
	 * @param supplierMaterialInfo
	 * @return
	 */
	@RequestMapping("saveSupplierMaterialOne")
	public Result<Void> saveSupplierMaterialOne(SupplierMaterialInfo supplierMaterialInfo){
		supplierMaterialInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		service.saveOrUpdate(supplierMaterialInfo);
		return ResultUtils.success();
	}

	/**
	 * @param supplierId
	 * @return
	 * @author pratice
	 * @date 2018年11月5日
	 */
	@RequestMapping("listBySupplierId")
	public Result<List<SupplierMaterialInfo>> listBySupplierId(Integer supplierId) {
		SupplierMaterialInfo supplierMaterialInfo = new SupplierMaterialInfo();
		supplierMaterialInfo.setSupplierId(supplierId);
		List<SupplierMaterialInfo> list = service.getListBy(supplierMaterialInfo);
		// 有bug
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Integer materialId = list.get(i).getMaterialId();
				MaterialBomInfo materialBomInfo = materialBomService.getModel(materialId);
				materialBomInfo.setMaterialBomParamValueStr(getParamValue(materialId));
				list.get(i).setMaterialName(materialBomInfo.getMaterialName());
				list.get(i).setMaterialCode(materialBomInfo.getMaterialCode());
				list.get(i).setMaterialBomParamValueStr(materialBomInfo.getMaterialBomParamValueStr());
				list.get(i).setOriginalMaterial(materialBomInfo.getOriginalMaterial());
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 根据供应商id查询该供应商下有什么物料
	 * @param pageNum
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("searchPagerBySupplierId")
	public Result<Pager<SupplierMaterialInfo>> searchPagerBySupplierId(int pageNum,int pageSize,SupplierMaterialInfo supplierMaterialInfo){
//		SupplierMaterialInfo supplierMaterialInfo = new SupplierMaterialInfo();
//		supplierMaterialInfo.setSupplierId(supplierId);
		Pager<SupplierMaterialInfo> pager = service.getModelListByLike(pageNum, pageSize, supplierMaterialInfo,false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				Integer materialId = pager.getList().get(i).getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				pager.getList().get(i).setMaterialBom(mbi);
			}
		}
		return ResultUtils.success(pager);
	}

	@RequestMapping("listByMaterialId")
	public Result<List<SupplierMaterialInfo>> listByMaterialId(Integer materialId) {
		SupplierMaterialInfo smi = new SupplierMaterialInfo();
		smi.setMaterialId(materialId);
		// 查询当前物料再什么供应商里面可以购买
		List<SupplierMaterialInfo> smiList = service.getListBy(smi);
		// 查询物料实体
		MaterialBomInfo mbi = materialBomService.getModel(materialId);
		if (smiList.size() != 0) {
			for (int i = 0; i < smiList.size(); i++) {
				Integer supplierId = smiList.get(i).getSupplierId();
				// 查询供应商实体
				SupplierInfo si = supplierService.getModel(supplierId);
				smiList.get(i).setSupplier(si);
				smiList.get(i).setMaterialBom(mbi);
				// 根据id查询结算方式名称
				if (si.getPaymentType()!=null){
					PurchasePaymentTypeInfo ppti = purchasePaymentTypeService.getModel(si.getPaymentType());
					if (ppti.getId() != null) {
						smiList.get(i).setPaymentName(ppti.getPaymentName());
					}
				}
				// 根据id查询发票类型名称
				if (si.getInvoiceType()!=null){
					PurchaseInvoiceTypeInfo piti = purchaseInvoiceTypeService.getModel(si.getInvoiceType());
					if (piti.getId() != null) {
						smiList.get(i).setTypeName(piti.getTypeName());
					}
				}
				// 根据id查询付款时间名称
				if (si.getPaymentPeriod()!=null){
					PurchasePaymentPeriodInfo pppi = purchasePaymentPeriodService.getModel(si.getPaymentPeriod());
					if (pppi.getId() != null) {
						smiList.get(i).setPeriodName(pppi.getPeriodName());
					}
				}
				// 根据id查询送货方式名称
				if (si.getShippingMethod()!=null){
					PurchaseShippingMethodInfo psmi = purchaseShippingMethodService.getModel(si.getShippingMethod());
					if (psmi.getId() != null) {
						smiList.get(i).setMethodName(psmi.getMethodName());
					}
				}
			}
		}
		return ResultUtils.success(smiList);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		SupplierMaterialEditor materialEditor = new SupplierMaterialEditor();
		binder.registerCustomEditor(Collection.class, "materialList", materialEditor);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		service.updateStatus(id);
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
