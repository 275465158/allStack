package com.ecms.web.controller;

import javax.annotation.Resource;

import com.ecms.utils.ExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchasePaymentPeriodInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchasePaymentPeriodEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PurchasePaymentPeriodService;
import com.ecms.utils.ResultUtils;

import java.util.List;

@RestController
@RequestMapping("purchase/payment/period")
public class PurchasePaymentPeriodController
		extends BaseController<PurchasePaymentPeriodEntity, PurchasePaymentPeriodInfo> {

	@Resource
	private PurchasePaymentPeriodService paymentService;

	@Override
	protected BaseService<PurchasePaymentPeriodEntity, PurchasePaymentPeriodInfo> getBaseService() {
		return paymentService;
	}

	@RequestMapping("status/update")
	public Result<Void> updatePaymentStatus(int id, int status) {
		paymentService.updateStatus(id, status);
		return ResultUtils.success();
	}
	@RequestMapping("getList")
	public Result<List<PurchasePaymentPeriodInfo>> getList(PurchasePaymentPeriodInfo purchasePaymentPeriodInfo){
		ExchangeUtils.convertBlank2Null(purchasePaymentPeriodInfo);
		List<PurchasePaymentPeriodInfo> list = paymentService.getListBy(purchasePaymentPeriodInfo);
		return ResultUtils.success(list);
	}
}
