package com.ecms.web.controller;

import com.ecms.bean.ProduceScheduleInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProduceScheduleEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProduceScheduleService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("produceSchedule")
public class ProduceScheduleController extends BaseController<ProduceScheduleEntity, ProduceScheduleInfo>  {

	@Resource
	private ProduceScheduleService produceScheduleService;

	@Override
	protected BaseService<ProduceScheduleEntity, ProduceScheduleInfo> getBaseService() {
		return produceScheduleService;
	}

	@RequestMapping("/all")
	public Result<Pager<ProduceScheduleInfo>> getProduceSchedule(int pageNum, ProduceScheduleInfo produceScheduleInfo) {
		ExchangeUtils.convertBlank2Null(produceScheduleInfo);
		Pager<ProduceScheduleInfo> pager = produceScheduleService.getModelList(pageNum, DEFAULT_PAGE_SIZE, produceScheduleInfo);
		return ResultUtils.success(pager);
	}


	@RequestMapping("/insertSchedule")
	public Result<Void> insertSchedule(ProduceScheduleInfo produceScheduleInfo) {
		produceScheduleService.insertByInfo(produceScheduleInfo);
		return ResultUtils.success();
	}

}
