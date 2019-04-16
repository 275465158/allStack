package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseOrdersListEntity;
import com.ecms.excel.PurchaseOrdersListTemplate;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("purchaseOrdersList")
public class PurchaseOrdersListController extends BaseController<PurchaseOrdersListEntity, PurchaseOrdersListInfo> {

	@Resource
	private PurchaseOrdersListService purchaseOrdersListService;

	@Resource
	private PurchaseOrdersService purchaseOrdersService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private SupplierMaterialService supplierMaterialService;

	@Resource
	private ActivitiModelService activitiModelService;


	@Override
	protected BaseService<PurchaseOrdersListEntity, PurchaseOrdersListInfo> getBaseService() {
		return purchaseOrdersListService;
	}

	/**
	 * 导出
	 * **/
	@Override
	protected List<PurchaseOrdersListTemplate> getListForDownload() {
		return purchaseOrdersListService.getExportList();
	}


	/****
	 * 根据采购单主表的id查询明细
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchByPurchaseOrdersId")
	public Result<List<PurchaseOrdersListInfo>> searchByPurchaseOrdersId(Integer id){
		PurchaseOrdersListInfo poli = new PurchaseOrdersListInfo();
		poli.setPurchaseOrdersId(id);
		List<PurchaseOrdersListInfo> poliList = purchaseOrdersListService.getListBy(poli);
		List<PurchaseOrdersListInfo> ordersListInfoList = poliList;
		if (poliList.size()!=0){
			for (int i=0;i<poliList.size();i++){
				if (poliList.get(i)!=null){
					Integer materialId = poliList.get(i).getMaterialId();
					Integer supplierId = poliList.get(i).getSupplierId();
					SupplierMaterialInfo supplierMaterialInfo = new SupplierMaterialInfo();
					supplierMaterialInfo.setSupplierId(supplierId);
					supplierMaterialInfo.setMaterialId(materialId);
					supplierMaterialInfo.setStatus(STATUS_DEFAULT);
					supplierMaterialInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					List<SupplierMaterialInfo> supplierMaterialInfoList = supplierMaterialService.getListBy(supplierMaterialInfo);
					if (supplierMaterialInfoList.size()!=0){
						poliList.get(i).setWeight(supplierMaterialInfoList.get(0).getWeight());
					}
//					for (int j=0;j<ordersListInfoList.size();j++){
//						if (ordersListInfoList.get(j)!=null) {
//							if (poliList.get(i).getMaterialId().equals(ordersListInfoList.get(j).getMaterialId()) && poliList.get(i).getArriveDate().equals(ordersListInfoList.get(j).getArriveDate()) && poliList.get(i).getId() != ordersListInfoList.get(j).getId()) {
//								poliList.get(i).setPurchaseReason(poliList.get(i).getPurchaseReason() + "&" + ordersListInfoList.get(j).getPurchaseReason());
//								poliList.get(i).setPurchaseQty(poliList.get(i).getPurchaseQty() + ordersListInfoList.get(j).getPurchaseQty());
//								poliList.get(i).setPurchasePriceTotal(poliList.get(i).getPurchasePriceTotal().add(ordersListInfoList.get(j).getPurchasePriceTotal()));
//								poliList.set(j, null);
//							}
//						}
//					}
				}else{
					continue;
				}
			}
		}
		poliList.removeAll(Collections.singleton(null));
		return ResultUtils.success(poliList);
	}

	@RequestMapping("/purchasePrint")
	public Result<List<PurchaseOrdersListInfo>> purchasePrint(Integer id){
		PurchaseOrdersListInfo poli = new PurchaseOrdersListInfo();
		poli.setPurchaseOrdersId(id);
		List<PurchaseOrdersListInfo> poliList = purchaseOrdersListService.getListBy(poli);
		List<PurchaseOrdersListInfo> ordersListInfoList = poliList;
		if (poliList.size()!=0){
			for (int i=0;i<poliList.size();i++){
				if (poliList.get(i)!=null){
					Integer materialId = poliList.get(i).getMaterialId();
					MaterialBomInfo mbi = materialBomService.getModel(materialId);
					poliList.get(i).setUnit(mbi.getMaterialUnit());
					Integer supplierId = poliList.get(i).getSupplierId();
					SupplierMaterialInfo supplierMaterialInfo = new SupplierMaterialInfo();
					supplierMaterialInfo.setSupplierId(supplierId);
					supplierMaterialInfo.setMaterialId(materialId);
					supplierMaterialInfo.setStatus(STATUS_DEFAULT);
					supplierMaterialInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					List<SupplierMaterialInfo> supplierMaterialInfoList = supplierMaterialService.getListBy(supplierMaterialInfo);
					if (supplierMaterialInfoList.size()!=0){
						poliList.get(i).setWeight(supplierMaterialInfoList.get(0).getWeight());
					}
					for (int j=0;j<ordersListInfoList.size();j++){
						if (ordersListInfoList.get(j)!=null) {
							if (poliList.get(i).getMaterialId().equals(ordersListInfoList.get(j).getMaterialId()) && poliList.get(i).getArriveDate().equals(ordersListInfoList.get(j).getArriveDate()) && poliList.get(i).getId() != ordersListInfoList.get(j).getId()) {
								poliList.get(i).setPurchaseReason(poliList.get(i).getPurchaseReason() + "&" + ordersListInfoList.get(j).getPurchaseReason());
								poliList.get(i).setPurchaseQty(poliList.get(i).getPurchaseQty() + ordersListInfoList.get(j).getPurchaseQty());
								poliList.get(i).setPurchasePriceTotal(poliList.get(i).getPurchasePriceTotal().add(ordersListInfoList.get(j).getPurchasePriceTotal()));
								poliList.set(j, null);
							}
						}
					}
				}else{
					continue;
				}
			}
		}
		poliList.removeAll(Collections.singleton(null));
		return ResultUtils.success(poliList);
	}

	/****
	 * 分页查询明细（采购单核销页面）
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseOrdersListInfo
	 * @return
	 */
	@RequestMapping("/purchaseOrdersListPager")
	public Result<Pager<PurchaseOrdersListInfo>> purchaseOrdersListPager(int pageNum,int pageSize,PurchaseOrdersListInfo purchaseOrdersListInfo){
		ExchangeUtils.convertBlank2Null(purchaseOrdersListInfo);
		purchaseOrdersListInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		purchaseOrdersListInfo.setVerificatStatus(0);
		purchaseOrdersListInfo.setReviewStatus(0);
		Pager<PurchaseOrdersListInfo> pager = purchaseOrdersListService.getModelListByLike(pageNum,pageSize, purchaseOrdersListInfo, false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				PurchaseOrdersInfo poi = purchaseOrdersService.getModel(pager.getList().get(i).getPurchaseOrdersId());
				//pager.getList().get(i).setPurchaseReason(poi.getPurchaseReason());

				pager.getList().get(i).setPurchasePerson(poi.getPurchasePerson());
				SupplierInfo si = supplierService.getModel(poi.getSupplierId());
				Integer materialId = pager.getList().get(i).getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				mbi.setMaterialBomParamValueStr(getParamValue(materialId));
				pager.getList().get(i).setMaterialBomInfo(mbi);
				ProductTypeInfo pti = new ProductTypeInfo();
				pti.setNumber(mbi.getNumber());
				pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
				if (ptiList.size()!=0){
					pager.getList().get(i).setType(ptiList.get(0).getType());
				}
				pager.getList().get(i).setPurchaseCode(poi.getPurchaseCode());
				pager.getList().get(i).setSupplierName(si.getSupplierName());
			}
		}
		pager.getList().removeAll(Collections.singleton(null));
		return ResultUtils.success(pager);
	}

	@RequestMapping("/getPurchaseOrdersList")
	public Result<Pager<PurchaseOrdersListInfo>> getPurchaseOrdersList(int pageNum,int pageSize,PurchaseOrdersListInfo purchaseOrdersListInfo){
		ExchangeUtils.convertBlank2Null(purchaseOrdersListInfo);
		purchaseOrdersListInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		purchaseOrdersListInfo.setVerificatStatus(0);
		Pager<PurchaseOrdersListInfo> pager = purchaseOrdersListService.getModelListByLike(pageNum,pageSize, purchaseOrdersListInfo, false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				PurchaseOrdersInfo poi = purchaseOrdersService.getModel(pager.getList().get(i).getPurchaseOrdersId());
				//pager.getList().get(i).setPurchaseReason(poi.getPurchaseReason());
				if(poi.getReviewStatus() != 0){
					pager.getList().set(i,null);
				}else{
					pager.getList().get(i).setPurchasePerson(poi.getPurchasePerson());
					SupplierInfo si = supplierService.getModel(poi.getSupplierId());
					Integer materialId = pager.getList().get(i).getMaterialId();
					MaterialBomInfo mbi = materialBomService.getModel(materialId);
					mbi.setMaterialBomParamValueStr(getParamValue(materialId));
					pager.getList().get(i).setMaterialBomInfo(mbi);
					ProductTypeInfo pti = new ProductTypeInfo();
					pti.setNumber(mbi.getNumber());
					pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
					List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
					if (ptiList.size()!=0){
						pager.getList().get(i).setType(ptiList.get(0).getType());
					}
					pager.getList().get(i).setPurchaseCode(poi.getPurchaseCode());
					pager.getList().get(i).setSupplierName(si.getSupplierName());
				}
			}
			pager.getList().removeAll(Collections.singleton(null));
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 重写的核销界面分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseOrdersListInfo
	 * @return
	 */
	@RequestMapping("purchaseOrdersList")
	public Result<Pager<PurchaseOrdersListInfo>> purchaseOrdersList(int pageNum,int pageSize,PurchaseOrdersListInfo purchaseOrdersListInfo){
		ExchangeUtils.convertBlank2Null(purchaseOrdersListInfo);
		purchaseOrdersListInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		purchaseOrdersListInfo.setVerificatStatus(0);
		Pager<PurchaseOrdersListInfo> pager = purchaseOrdersListService.getModelListByLike(pageNum,pageSize, purchaseOrdersListInfo, false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				PurchaseOrdersInfo ordersInfo = purchaseOrdersService.getModel(pager.getList().get(i).getPurchaseOrdersId());
				pager.getList().get(i).setPurchaseDate(ordersInfo.getPurchaseDate());
			}
		}
		return ResultUtils.success(pager);
	}



	/****
	 * 根据明细id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<PurchaseOrdersListInfo> seacrhById(Integer id){
		PurchaseOrdersListInfo poli = purchaseOrdersListService.getModel(id);
		MaterialBomInfo mbi = materialBomService.getModel(poli.getMaterialId());
		ProductTypeInfo pti = new ProductTypeInfo();
		pti.setNumber(mbi.getNumber());
		pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
		if (ptiList.size()!=0){
			mbi.setType(ptiList.get(0).getType());
		}
		mbi.setMaterialBomParamValueStr(getParamValue(mbi.getId()));
		PurchaseOrdersInfo poi = purchaseOrdersService.getModel(poli.getPurchaseOrdersId());
		SupplierInfo si = supplierService.getModel(poi.getSupplierId());
		poli.setMaterialBomInfo(mbi);
		poli.setSupplierName(si.getSupplierName());
		poli.setPurchaseCode(poi.getPurchaseCode());
		poli.setPurchaseDate(poi.getPurchaseDate());
		//poli.setPurchaseReason(poi.getPurchaseReason());
		poli.setPurchasePerson(poi.getPurchasePerson());
		//获取供应商物料中维护的物料重量
		SupplierMaterialInfo smi = new SupplierMaterialInfo();
		smi.setMaterialId(poli.getMaterialId());
		smi.setSupplierId(poi.getSupplierId());
		List<SupplierMaterialInfo> smiList = supplierMaterialService.getListBy(smi);
		if (smiList.size()!=0){
			poli.setWeight(smiList.get(0).getWeight());
			poli.setSupplierMaterialId(smiList.get(0).getId());
		}else {
			poli.setWeight(0.0);
		}
		return ResultUtils.success(poli);
	}

	/****
	 * 不合格评审--换货
	 * @param purchaseOrdersListInfo
	 * @return
	 */
	@RequestMapping("change")
	public Result<Void> change(PurchaseOrdersListInfo purchaseOrdersListInfo){
		purchaseOrdersListService.change(purchaseOrdersListInfo);
		return ResultUtils.success();
	}

	/****
	 * 采购单核销
	 * @param purchaseOrdersListInfo
	 * @return
	 */
	@RequestMapping("/verifivat")
	public Result<PurchaseOrdersListInfo> verifivat(PurchaseOrdersListInfo purchaseOrdersListInfo){
		purchaseOrdersListService.verifivat(purchaseOrdersListInfo);
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
