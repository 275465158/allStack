package com.ecms.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.DeliveryDetailInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.DeliveryDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DeliveryDetailService;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("/materiel/delivery/detail")
public class DeliveryDetailController extends BaseController<DeliveryDetailEntity, DeliveryDetailInfo> {

	@Resource
	private DeliveryDetailService service;
	@Override
	protected BaseService<DeliveryDetailEntity, DeliveryDetailInfo> getBaseService() {
		return service;
	}

	@RequestMapping("listByDeliveryId")
	public Result<Pager<DeliveryDetailInfo>> listByDeliveryId(Integer deliveryId){
		DeliveryDetailInfo info = new DeliveryDetailInfo();
		info.setDeliveryId(deliveryId);
		Pager<DeliveryDetailInfo> pager = service.getModelList(1, DEFAULT_PAGE_SIZE, info);
		return ResultUtils.success(pager);
	}
}
