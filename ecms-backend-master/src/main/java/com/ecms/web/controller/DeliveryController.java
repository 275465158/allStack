package com.ecms.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.DeliveryDetailInfo;
import com.ecms.bean.DeliveryInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.DeliveryEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DeliveryDetailService;
import com.ecms.service.DeliveryService;
import com.ecms.service.MaterielInventoryService;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("/materiel/delivery")
public class DeliveryController extends BaseController<DeliveryEntity, DeliveryInfo> {

	@Resource
	private DeliveryService service;
	@Resource
	private DeliveryDetailService detailService;
	@Resource
	private MaterielInventoryService inventoryService;
	@Override
	protected BaseService<DeliveryEntity, DeliveryInfo> getBaseService() {
		return service;
	}

	@RequestMapping("info")
	public Result<DeliveryInfo> info(Integer deliveryId){
		DeliveryInfo info = service.getModel(deliveryId);
		return ResultUtils.success(info);
	}
	
	@RequestMapping("deliveryById")
	public  Result<Void> deliveryById(Integer deliveryId){
		DeliveryInfo info = service.getModel(deliveryId);
		DeliveryDetailInfo searchDetail = new DeliveryDetailInfo();
		searchDetail.setDeliveryId(deliveryId);
		List<DeliveryDetailInfo> detailList = detailService.getListBy(searchDetail);
		detailList.stream().forEach(detail ->{
			MaterielInventoryInfo invInfo = inventoryService.getModel(detail.getMaterielId());
			float remainQty = invInfo.getQty()-detail.getQty();
			if(remainQty>=0.0) {
				invInfo.setQty(remainQty);
				inventoryService.saveOrUpdate(invInfo);
			}
		});
		
		return ResultUtils.success();
	}
}
