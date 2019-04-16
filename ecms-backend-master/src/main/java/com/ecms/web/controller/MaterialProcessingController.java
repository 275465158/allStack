package com.ecms.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterialProcessingInfo;
import com.ecms.dal.entity.MaterialProcessingEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialProcessingService;

@RestController
@RequestMapping("/material/processing")
public class MaterialProcessingController extends BaseController<MaterialProcessingEntity, MaterialProcessingInfo> {

	@Resource
	private MaterialProcessingService service;
	@Override
	protected BaseService<MaterialProcessingEntity, MaterialProcessingInfo> getBaseService() {
		return service;
	}

}
