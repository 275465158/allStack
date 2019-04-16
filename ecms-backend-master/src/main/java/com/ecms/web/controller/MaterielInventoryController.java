package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.ecms.dal.entity.MaterielEntity;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.service.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.bean.WarehousingEntryInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterielInventoryEntity;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.InfoEditorSupport;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("materiel/inventory")
public class MaterielInventoryController extends BaseController<MaterielInventoryEntity, MaterielInventoryInfo> {

	@Resource
	private MaterielInventoryService service;
	@Resource
	private WarehousingEntryDetailService entryDetailService;
	@Resource
	private MaterielService materielService;
	@Resource
	private SupplierService supplierService;
	@Override
	protected BaseService<MaterielInventoryEntity, MaterielInventoryInfo> getBaseService() {
		return service;
	}

	@RequestMapping("fromEntry")
	public Result<Void> insertFormEntry(Integer entryDetailId){
		WarehousingEntryDetailInfo entryDetailInfo = entryDetailService.getModel(entryDetailId);
		entryDetailInfo.setEntryStatus("0");
		entryDetailService.saveOrUpdate(entryDetailInfo);
		System.out.println(entryDetailId);
		MaterielInfo materielInfo = materielService.getByMaterialId(entryDetailInfo.getEntryInfo().getMaterialId());
		if(null==materielInfo) {
			//仓库中没有相应的物品
			materielInfo = new MaterielInfo();
			materielInfo.setMaterialId(entryDetailInfo.getEntryInfo().getMaterialId());
			materielInfo.setRepertoryId(entryDetailInfo.getEntryInfo().getRepertoryId());
			materielInfo.setCompanyId(entryDetailInfo.getEntryInfo().getCompanyId());
			materielInfo = ExchangeUtils.exchangeObject(materielService.saveOrUpdate(materielInfo), MaterielInfo.class);
		}else {
			//存在物品
			System.out.println(materielInfo.getId());
		}
		MaterielInventoryInfo invInfo = new MaterielInventoryInfo();
		invInfo.setCompanyId(entryDetailInfo.getEntryInfo().getCompanyId());
		invInfo.setMaterielId(materielInfo.getId());
		invInfo.setShelfPosition(entryDetailInfo.getEntryInfo().getShelfPosition());
		invInfo.setSupplierId(entryDetailInfo.getEntryInfo().getSupplierId());
		invInfo.setMaterielBatch(entryDetailInfo.getEntryInfo().getMaterielbatch());
		invInfo.setPurchaseCause(entryDetailInfo.getEntryInfo().getPurchaseCause());
		invInfo.setOriginOfMateriel(entryDetailInfo.getEntryInfo().getOriginOfMateriel());
		invInfo.setStockFrom(entryDetailInfo.getEntryInfo().getStockFrom());
		invInfo.setExpiryDate(entryDetailInfo.getEntryInfo().getExpiryDate());
		invInfo.setConsignee(entryDetailInfo.getEntryInfo().getConsignee());
		invInfo.setEntryId(entryDetailInfo.getId());
		invInfo.setQty(entryDetailInfo.getEntryQty());
		service.saveOrUpdate(invInfo);
		return ResultUtils.success();
	}
	
	@RequestMapping("seachByNameAndCode")
	public Result<Pager<MaterielInventoryInfo>> pagerForSearch(String materialName, String materialCode){
		MaterialBomInfo materialBom = new MaterialBomInfo();
		materialBom.setMaterialName(materialName);
		materialBom.setMaterialCode(materialCode);
		MaterielInfo materiel = new MaterielInfo();
		materiel.setMaterialBom(materialBom);
		MaterielInventoryInfo info = new MaterielInventoryInfo();
		info.setMateriel(materiel);
		if(null==materialName) {
			materialName="";
		}
		if(null==materialCode) {
			materialCode="";
		}
		Pager<MaterielInventoryInfo> pager = service.getModelList(1, DEFAULT_PAGE_SIZE, materialName, materialCode);
		return ResultUtils.success(pager);
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		InfoEditorSupport infoEditor = new InfoEditorSupport(WarehousingEntryInfo.class);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "updated", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(WarehousingEntryInfo.class, "entryInfo", infoEditor);
		binder.registerCustomEditor(MaterielInfo.class, "materiel", new InfoEditorSupport(MaterielInfo.class));
	}


	/**
	 * 更新库存
	 * @param materielInventoryInfo
	 * @return
	 */
	@RequestMapping("/saveOrUpdateObject")
	public Result<MaterielInventoryInfo> saveOrUpdateObject(MaterielInventoryInfo materielInventoryInfo) {
		List<Map<String,Object>> tableDataRow = materielInventoryInfo.getTableDataRow();
		if(tableDataRow.size() != 0){
			Float reduceOnhand = 0.00f;
			for(Map<String,Object> rowData : tableDataRow){
				MaterielInventoryInfo mii = new MaterielInventoryInfo();
				mii.setId(Integer.parseInt(rowData.get("id").toString()));
				mii.setQty(Float.parseFloat(rowData.get("qty").toString()));
				reduceOnhand += Float.parseFloat(rowData.get("qty").toString());
				service.saveOrUpdate(mii);
			}
			//更新库存
			Integer materielTableId = materielInventoryInfo.getMaterielTableId();
			MaterielEntity me = materielService.getEntity(materielTableId);
			MaterielInfo materielInfo = new MaterielInfo();
			materielInfo.setId(materielTableId);
			materielInfo.setOnhand(me.getOnhand()-reduceOnhand);
			materielService.saveOrUpdate(materielInfo);
		}
		return ResultUtils.success();
	}

	/**
	 * 不分页查询物料列表
	 * @param info
	 * @return
	 */
	@RequestMapping("/getListNoPageSize")
	public Result<List<MaterielInventoryInfo>> getListBy(MaterielInventoryInfo info) {
		List<MaterielInventoryInfo> list = service.getListBy(info);
		if(list.size() != 0){
			//查询供应商名称
			Integer id;
			for(MaterielInventoryInfo mii : list){
				id = mii.getSupplierId();
				SupplierEntity se = supplierService.getEntity(id);
				mii.setSupplierName(se.getSupplierName());
			}
		}
		return ResultUtils.success(list);
	}
}

