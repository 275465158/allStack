package com.ecms.web.controller;

import java.util.List;

import javax.annotation.Resource;

import com.ecms.dal.entity.PickingDetailEntity;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.PickingDetailInfo;
import com.ecms.bean.PickingInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.PickingEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterielInventoryService;
import com.ecms.service.PickingDetailService;
import com.ecms.service.PickingService;
import com.ecms.utils.ResultUtils;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("/materiel/picking")
public class PickingController extends BaseController<PickingEntity, PickingInfo> {

	@Resource
	private PickingService service;
	@Resource
	private PickingDetailService detailService;
	@Resource
	private MaterielInventoryService inventoryService;

	@Override
	protected BaseService<PickingEntity, PickingInfo> getBaseService() {
		return service;
	}

	@RequestMapping("info")
	public Result<PickingInfo> info(Integer pickingId) {
		PickingInfo info = service.getModel(pickingId);
		return ResultUtils.success(info);
	}

	@RequestMapping("pickedById")
	public Result<Void> pickedById(Integer id) {
		PickingInfo info = service.getModel(id);
		PickingDetailInfo searchDetail = new PickingDetailInfo();
		searchDetail.setPickingId(id);
		List<PickingDetailInfo> detailList = detailService.getListBy(searchDetail);
		detailList.stream().forEach(detail -> {
			MaterielInventoryInfo invInfo = inventoryService.getModel(detail.getMaterielId());
			float remainQty = invInfo.getQty() - detail.getQty();
			if (remainQty >= 0.0f) {
				invInfo.setQty(remainQty);
				inventoryService.saveOrUpdate(invInfo);
			}
		});
		return ResultUtils.success();
	}

	@RequestMapping("insertByInfo")
	public Result<PickingDetailEntity> insertByInfo(PickingInfo pickingInfo){
		pickingInfo.setPreparedBy(LoginFilter.getCurrentUser().getUserName());
		pickingInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		PickingEntity pickingEntity = service.insertBy(pickingInfo);
		PickingDetailEntity pickingDetailEntity = new PickingDetailEntity();
		if(pickingEntity.getId() != null){
			PickingDetailInfo pickingDetailInfo = new PickingDetailInfo();
			pickingDetailInfo.setPickingId(pickingEntity.getId());
			pickingDetailInfo.setMaterielId(pickingInfo.getMaterielId());
			pickingDetailInfo.setQty(pickingInfo.getTotalNum());
			pickingDetailInfo.setUnit(pickingInfo.getMaterialUnit());
			pickingDetailInfo.setStatus(STATUS_DEFAULT);
			pickingDetailEntity = detailService.insertBy(pickingDetailInfo);
		}
		return ResultUtils.success(pickingDetailEntity);
	}
}
