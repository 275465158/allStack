package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchaseOrderInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.bean.WarehousingEntryInfo;
import com.ecms.bean.WarehousingEntryMaterialModal;
import com.ecms.common.Result;
import com.ecms.dal.entity.WarehousingEntryDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PurchaseOrderService;
import com.ecms.service.WarehousingEntryDetailService;
import com.ecms.service.WarehousingEntryService;
import com.ecms.utils.InfoEditorSupport;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("/materiel/entry/detail")
public class WarehousingEntryDetailController
		extends BaseController<WarehousingEntryDetailEntity, WarehousingEntryDetailInfo> {

	@Resource
	private WarehousingEntryDetailService service;
	@Resource
	private WarehousingEntryService entryService;
	@Resource
	private PurchaseOrderService purchaseOrderService;
	@Override
	protected BaseService<WarehousingEntryDetailEntity, WarehousingEntryDetailInfo> getBaseService() {
		return service;
	}

	@RequestMapping("info")
	public Result<WarehousingEntryDetailInfo> info(Integer id){
		WarehousingEntryDetailInfo info = service.getModel(id);
		info.setEntryInfo(entryService.getModel(info.getEntryId()));
		return ResultUtils.success(info);
	}
	
	@RequestMapping("listByEntryId")
	public Result<List<WarehousingEntryDetailInfo>> listByEntryId(Integer id){
		List<WarehousingEntryDetailInfo> list = service.listByEntryId(id);
		return ResultUtils.success(list);
	}
	
	@RequestMapping("entry")
	public Result<WarehousingEntryDetailInfo> entry(WarehousingEntryDetailInfo info){
		WarehousingEntryMaterialModal entry = entryService.info(info.getEntryId());
		if(entry.getQty()<=entry.getArrivedTotalQty()) {
			PurchaseOrderInfo orderInfo = purchaseOrderService.getModel(entry.getPurchaseOrderId());
			orderInfo.setArrived(true);
			SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
			orderInfo.setArrivedDate(shortdateFormat.format(new Date()));
			purchaseOrderService.saveOrUpdate(orderInfo);
		}
		return ResultUtils.success(getBaseService().saveOrUpdate(info));
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		InfoEditorSupport infoEditor = new InfoEditorSupport(WarehousingEntryInfo.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "updated", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(WarehousingEntryInfo.class, "entryInfo", infoEditor);
	}
}
