package com.ecms.web.controller;

import javax.annotation.Resource;

import com.ecms.utils.ExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchaseInvoiceTypeInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseInvoiceTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PurchaseInvoiceTypeService;
import com.ecms.utils.ResultUtils;

import java.util.List;

@RestController
@RequestMapping("purchase/invoice/type")
public class PurchaseInvoiceTypeController extends BaseController<PurchaseInvoiceTypeEntity, PurchaseInvoiceTypeInfo> {

	@Resource
	private PurchaseInvoiceTypeService typeService;

	@Override
	protected BaseService<PurchaseInvoiceTypeEntity, PurchaseInvoiceTypeInfo> getBaseService() {
		return typeService;
	}

	@RequestMapping("status/update")
	public Result<Void> updateTypeStatus(int id, int status) {
		typeService.updateStatus(id, status);
		return ResultUtils.success();
	}
	@RequestMapping("getList")
	public Result<List<PurchaseInvoiceTypeInfo>> getList(PurchaseInvoiceTypeInfo purchaseInvoiceTypeInfo){
		ExchangeUtils.convertBlank2Null(purchaseInvoiceTypeInfo);
		List<PurchaseInvoiceTypeInfo> list = typeService.getListBy(purchaseInvoiceTypeInfo);
		return ResultUtils.success(list);
	}

}
