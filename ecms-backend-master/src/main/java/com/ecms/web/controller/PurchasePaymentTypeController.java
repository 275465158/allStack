package com.ecms.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchasePaymentTypeInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchasePaymentTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PurchasePaymentTypeService;
import com.ecms.utils.ResultUtils;
import java.util.List;
import com.ecms.utils.ExchangeUtils;

@RestController
@RequestMapping("purchase/payment/type")
public class PurchasePaymentTypeController extends BaseController<PurchasePaymentTypeEntity, PurchasePaymentTypeInfo> {
	@Resource
	private PurchasePaymentTypeService paymentService;

	@Override
	protected BaseService<PurchasePaymentTypeEntity, PurchasePaymentTypeInfo> getBaseService() {
		return paymentService;
	}

	@RequestMapping("status/update")
	public Result<Void> updatePaymentStatus(int id, int status) {
		paymentService.updateStatus(id, status);
		return ResultUtils.success();
	}
	@RequestMapping("getList")
	public Result<List<PurchasePaymentTypeInfo>> getList(PurchasePaymentTypeInfo purchasePaymentTypeInfo){
		ExchangeUtils.convertBlank2Null(purchasePaymentTypeInfo);
		List<PurchasePaymentTypeInfo> list = paymentService.getListBy(purchasePaymentTypeInfo);
		return ResultUtils.success(list);
	}

}
