package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.ecms.common.Pager;
import com.ecms.utils.ExchangeUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.PurchaseOrderInfo;
import com.ecms.bean.SupplierInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.bean.WarehousingEntryMaterialModal;
import com.ecms.common.Result;
import com.ecms.dal.entity.WarehousingEntryEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.PurchaseOrderService;
import com.ecms.service.SupplierService;
import com.ecms.service.WarehousingEntryDetailService;
import com.ecms.service.WarehousingEntryService;
import com.ecms.utils.InfoEditorSupport;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("/materiel/entry")
public class WarehousingEntryController extends BaseController<WarehousingEntryEntity, WarehousingEntryMaterialModal> {

	@Resource
	private WarehousingEntryService service;
	@Resource
	private PurchaseOrderService purchaseOrderService;
	@Resource
	private SupplierService supplierService;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private WarehousingEntryDetailService warehousingEntryDetailService;
	@Override
	protected BaseService<WarehousingEntryEntity, WarehousingEntryMaterialModal> getBaseService() {
		return service;
	}

	@RequestMapping("insertByPurchaseOrder")
	public Result<Void> insert(Integer id){
		WarehousingEntryMaterialModal entryInfo = new WarehousingEntryMaterialModal();
		PurchaseOrderInfo poInfo = purchaseOrderService.getModel(id);
		entryInfo.setMaterialId(poInfo.getMaterialId());
		entryInfo.setSupplierId(poInfo.getSupplierId());
		entryInfo.setPurchaseOrderId(id);
		entryInfo.setQty(poInfo.getQty());
		entryInfo.setPurchaseCause(poInfo.getReason());
		entryInfo.setRepertoryId(poInfo.getRepertoryId());
		entryInfo.setEstimatedDate(poInfo.getEstimatedDate());
		service.saveOrUpdate(entryInfo);
		return ResultUtils.success();
	}
	
	@RequestMapping("info")
	public Result<WarehousingEntryMaterialModal> info(Integer id){
		WarehousingEntryMaterialModal entryModal = service.info(id);
		return ResultUtils.success(entryModal);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		InfoEditorSupport infoEditor = new InfoEditorSupport(MaterialBomInfo.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "updated", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(MaterialBomInfo.class, "materialBom", infoEditor);
		binder.registerCustomEditor(PurchaseOrderInfo.class, "purchaseOrderInfo", new InfoEditorSupport(PurchaseOrderInfo.class));
		binder.registerCustomEditor(SupplierInfo.class, "supplier", new InfoEditorSupport(SupplierInfo.class));
	}

	@RequestMapping("updateQty")
	public Result<Void> updateQtyById(Integer id,Float qualifiedQty,Float unqualifiedQty,Float qualifiedRate){
		WarehousingEntryDetailInfo warehousingEntryDetailInfo = new WarehousingEntryDetailInfo();
		warehousingEntryDetailInfo.setId(id);
		warehousingEntryDetailInfo.setUnqualifiedQty(unqualifiedQty);
		warehousingEntryDetailInfo.setQualifiedRate(qualifiedRate);
		warehousingEntryDetailInfo.setQualifiedQty(qualifiedQty);
		warehousingEntryDetailService.updateByPrimaryKeySelective(warehousingEntryDetailInfo);
		return ResultUtils.success();
	}

	@RequestMapping("list")
	public Result<Pager<WarehousingEntryMaterialModal>> list(int pageNum, WarehousingEntryMaterialModal warehousingEntryMaterialModal) {
		MaterialBomInfo materialBomInfo = new MaterialBomInfo();
		if(warehousingEntryMaterialModal.getMaterialCode() != null && !"".equals(warehousingEntryMaterialModal.getMaterialCode())){
			materialBomInfo.setMaterialCode(warehousingEntryMaterialModal.getMaterialCode());
		}
		if(warehousingEntryMaterialModal.getMaterialName() != null && !"".equals(warehousingEntryMaterialModal.getMaterialName())){
			materialBomInfo.setMaterialName(warehousingEntryMaterialModal.getMaterialName());
		}

		List<MaterialBomInfo> mbiList = bomService.getListBy(materialBomInfo);
		if(mbiList.size() != 0){
			for(int i=0;i<mbiList.size();i++){

			}
			warehousingEntryMaterialModal.setMaterialId(mbiList.get(0).getId());
		}
		ExchangeUtils.convertBlank2Null(warehousingEntryMaterialModal);

		Pager<WarehousingEntryMaterialModal> pager = getBaseService().getModelList(pageNum, DEFAULT_PAGE_SIZE, warehousingEntryMaterialModal);
		return ResultUtils.success(pager);
	}

	/**
	 * 不分页查询
	 * @param pageNum
	 * @param warehousingEntryMaterialModal
	 * @return
	 */
	@RequestMapping("totalList")
	public Result<List<WarehousingEntryMaterialModal>> totalList(int pageNum, WarehousingEntryMaterialModal warehousingEntryMaterialModal) {
		MaterialBomInfo materialBomInfo = new MaterialBomInfo();
		if(warehousingEntryMaterialModal.getMaterialCode() != null && !"".equals(warehousingEntryMaterialModal.getMaterialCode())){
			materialBomInfo.setMaterialCode(warehousingEntryMaterialModal.getMaterialCode());
		}
		if(warehousingEntryMaterialModal.getMaterialName() != null && !"".equals(warehousingEntryMaterialModal.getMaterialName())){
			materialBomInfo.setMaterialName(warehousingEntryMaterialModal.getMaterialName());
		}

		List<MaterialBomInfo> mbiList = bomService.getListBy(materialBomInfo);
		if(mbiList.size() != 0){
			for(int i=0;i<mbiList.size();i++){

			}
			warehousingEntryMaterialModal.setMaterialId(mbiList.get(0).getId());
		}
		ExchangeUtils.convertBlank2Null(warehousingEntryMaterialModal);

		List<WarehousingEntryMaterialModal> pager = getBaseService().getListBy(warehousingEntryMaterialModal);
		return ResultUtils.success(pager);
	}
}
