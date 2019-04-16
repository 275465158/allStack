package com.ecms.web.controller;

import javax.annotation.Resource;

import com.ecms.utils.ExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PurchaseShippingMethodInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PurchaseShippingMethodEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PurchaseShippingMethodService;
import com.ecms.utils.ResultUtils;
import java.util.List;

@RestController
@RequestMapping("purchase/shipping/method")
public class PurchaseShippingMethodController
		extends BaseController<PurchaseShippingMethodEntity, PurchaseShippingMethodInfo> {

	@Resource
	private PurchaseShippingMethodService methodService;

	@Override
	protected BaseService<PurchaseShippingMethodEntity, PurchaseShippingMethodInfo> getBaseService() {
		return methodService;
	}

	@RequestMapping("status/update")
	public Result<Void> updatePaymentStatus(int id, int status) {
		methodService.updateStatus(id, status);
		return ResultUtils.success();
	}

	@RequestMapping("getList")
	public Result<List<PurchaseShippingMethodInfo>> getList(PurchaseShippingMethodInfo purchaseShippingMethodInfo){
		ExchangeUtils.convertBlank2Null(purchaseShippingMethodInfo);
		List<PurchaseShippingMethodInfo> list = methodService.getListBy(purchaseShippingMethodInfo);
		return ResultUtils.success(list);
	}

}
