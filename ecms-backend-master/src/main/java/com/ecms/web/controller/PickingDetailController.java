package com.ecms.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.PickingDetailInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.PickingDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.PickingDetailService;
import com.ecms.utils.ResultUtils;

@RestController
@RequestMapping("/materiel/picking/detail")
public class PickingDetailController extends BaseController<PickingDetailEntity, PickingDetailInfo> {

	@Resource
	private PickingDetailService service;
	@Override
	protected BaseService<PickingDetailEntity, PickingDetailInfo> getBaseService() {
		return service;
	}

	@RequestMapping("listByPickingId")
	public Result<Pager<PickingDetailInfo>> listByPickingId(Integer pickingId){
		PickingDetailInfo info = new PickingDetailInfo();
		info.setPickingId(pickingId);
		Pager<PickingDetailInfo> pager = service.getModelList(1, DEFAULT_PAGE_SIZE, info);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/saveOrUpdate")
	public Result<PickingDetailInfo> saveOrUpdate(PickingDetailInfo info) {

		return ResultUtils.success(getBaseService().saveOrUpdate(info));
	}
}
