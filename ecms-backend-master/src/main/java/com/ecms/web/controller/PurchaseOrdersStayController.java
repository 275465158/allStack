package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseOrdersStayEntity;
import com.ecms.excel.PurchaseOrdersStayTemplate;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ecms.common.BusinessConstants.NO_ORDER;
import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("purchaseOrdersStay")
public class PurchaseOrdersStayController extends BaseController<PurchaseOrdersStayEntity, PurchaseOrdersStayInfo> {

	@Resource
	private PurchaseOrdersStayService purchaseOrdersStayService;

	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private SupplierMaterialService supplierMaterialService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private SellerOrderService sellerOrderService;

	@Override
	protected BaseService<PurchaseOrdersStayEntity, PurchaseOrdersStayInfo> getBaseService() {
		return purchaseOrdersStayService;
	}

	/**
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseOrdersStayInfo
	 * @return
	 */
	@RequestMapping("/purchaseList")
	public Result<Pager<PurchaseOrdersStayInfo>> purchaseList(int pageNum, int pageSize, PurchaseOrdersStayInfo purchaseOrdersStayInfo){
		purchaseOrdersStayInfo.setPurchaseStayStatus(0);
		purchaseOrdersStayInfo.setStatus(1);
		String orderStr = "material_id desc";
		Pager<PurchaseOrdersStayInfo> pager = purchaseOrdersStayService.getModelListByLikeOrder(pageNum,pageSize, purchaseOrdersStayInfo, orderStr,false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				Integer materialId = pager.getList().get(i).getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				mbi.setMaterialBomParamValueStr(getParamValue(materialId));
				SupplierMaterialInfo smi = new SupplierMaterialInfo();
				smi.setMaterialId(materialId);
				smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<SupplierMaterialInfo> smiList = supplierMaterialService.getListBy(smi);
				if (smiList.size()!=0){
					for (int j=0;j<smiList.size();j++){
						smiList.get(j).setSupplier(supplierService.getModel(smiList.get(j).getSupplierId()));
					}
				}
				mbi.setSmiList(smiList);
				ProductTypeInfo pti = new ProductTypeInfo();
				pti.setNumber(mbi.getNumber());
				pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
				if (ptiList.size()!=0){
					mbi.setType(ptiList.get(0).getType());
				}
				pager.getList().get(i).setMaterialBomInfo(mbi);
			}
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 下单页面跳转获取值
	 * @param ids
	 * @return
	 */
	@RequestMapping("purchaseListByIds")
	public Result<List<PurchaseOrdersStayInfo>> purchaseListByIds(PurchaseOrdersStayInfo purchaseOrdersStayInfo){
		List<PurchaseOrdersStayInfo> ordersStayInfoList = new ArrayList<>();
		List<Integer> ids = purchaseOrdersStayInfo.getIds();
		if (ids.size()!=0){
			for (int i=0;i<ids.size();i++){
				PurchaseOrdersStayInfo ordersStayInfo = purchaseOrdersStayService.getModel(ids.get(i));
				Integer materialId = ordersStayInfo.getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				mbi.setMaterialBomParamValueStr(getParamValue(materialId));
				SupplierMaterialInfo smi = new SupplierMaterialInfo();
				smi.setMaterialId(materialId);
				smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<SupplierMaterialInfo> smiList = supplierMaterialService.getListBy(smi);
				if (smiList.size()!=0){
					for (int j=0;j<smiList.size();j++){
						smiList.get(j).setSupplier(supplierService.getModel(smiList.get(j).getSupplierId()));
					}
				}
				mbi.setSmiList(smiList);
				ProductTypeInfo pti = new ProductTypeInfo();
				pti.setNumber(mbi.getNumber());
				pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
				if (ptiList.size()!=0){
					mbi.setType(ptiList.get(0).getType());
				}
				ordersStayInfo.setMaterialBomInfo(mbi);
				ordersStayInfoList.add(ordersStayInfo);
			}
		}
		return ResultUtils.success(ordersStayInfoList);
	}

	/**
	 * 保存供应商id到待采购单上
	 * @param purchaseOrdersId
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("saveSupplierId")
	public Result<Void> saveSupplierId(Integer purchaseOrdersId,Integer supplierId){
		purchaseOrdersStayService.saveSupplierId(purchaseOrdersId,supplierId);
		return ResultUtils.success();
	}

	/**
	 * 不合格评审-内部消耗
	 * @param purchaseOrdersStayInfo
	 * @return
	 */
	@RequestMapping("/internalLoss")
	public Result<Void> internalLoss(PurchaseOrdersStayInfo purchaseOrdersStayInfo){
		purchaseOrdersStayService.internalLoss(purchaseOrdersStayInfo);
		return ResultUtils.success();
	}

	/**
	 * 导出
	 * **/
	@Override
	protected List<PurchaseOrdersStayTemplate> getListForDownload() {
		return purchaseOrdersStayService.getExportList();
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
			return (null);
		}
	}

	@RequestMapping("/getStayPurchaseList")
	public Result<Pager<PurchaseOrdersStayInfo>> getStayPurchaseList(int pageNum, int pageSize, PurchaseOrdersStayInfo purchaseOrdersStayInfo){
		purchaseOrdersStayInfo.setPurchaseStayStatus(NO_ORDER);
		purchaseOrdersStayInfo.setStatus(STATUS_DEFAULT);
		Pager<PurchaseOrdersStayInfo> pager = purchaseOrdersStayService.getModelListByLike(pageNum,pageSize, purchaseOrdersStayInfo,false);
		if (pager.getList().size()!=0){
			for (int i=0;i<pager.getList().size();i++){
				Integer materialId = pager.getList().get(i).getMaterialId();
				MaterialBomInfo mbi = materialBomService.getModel(materialId);
				mbi.setMaterialBomParamValueStr(getParamValue(materialId));
				SupplierMaterialInfo smi = new SupplierMaterialInfo();
				smi.setMaterialId(materialId);
				smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<SupplierMaterialInfo> smiList = supplierMaterialService.getListBy(smi);
				if (smiList.size()!=0){
					for (int j=0;j<smiList.size();j++){
						smiList.get(j).setSupplier(supplierService.getModel(smiList.get(j).getSupplierId()));
					}
				}
				mbi.setSmiList(smiList);
				ProductTypeInfo pti = new ProductTypeInfo();
				pti.setNumber(mbi.getNumber());
				pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<ProductTypeInfo> ptiList = productTypeService.getListBy(pti);
				if (ptiList.size()!=0){
					mbi.setType(ptiList.get(0).getType());
				}
				pager.getList().get(i).setMaterialBomInfo(mbi);
				SellerOrderInfo soi = sellerOrderService.getModel(pager.getList().get(i).getOrderId());

				pager.getList().get(i).setContractId(soi.getContractId());
				pager.getList().get(i).setOrderCode(soi.getOrderCode());
			}
		}
		return ResultUtils.success(pager);
	}

}
