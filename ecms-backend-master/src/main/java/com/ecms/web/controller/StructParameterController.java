package com.ecms.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.StructParameterInfo;
import com.ecms.dal.entity.StructParameterEntity;
import com.ecms.service.BaseService;

@RestController
@RequestMapping("structparameter")
public class StructParameterController extends BaseController<StructParameterEntity, StructParameterInfo>{
	@Override
	protected BaseService<StructParameterEntity, StructParameterInfo> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}
