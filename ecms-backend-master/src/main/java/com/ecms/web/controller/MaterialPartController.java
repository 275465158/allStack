package com.ecms.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.MaterialPartInfo;
import com.ecms.dal.entity.MaterialPartEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialPartService;

@RestController
@RequestMapping("/material/part")
public class MaterialPartController extends BaseController<MaterialPartEntity, MaterialPartInfo> {

	@Resource
	private MaterialPartService service;
	@Override
	protected BaseService<MaterialPartEntity, MaterialPartInfo> getBaseService() {
		return service;
	}

}
