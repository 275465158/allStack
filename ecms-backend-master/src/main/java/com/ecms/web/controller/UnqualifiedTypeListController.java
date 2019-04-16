package com.ecms.web.controller;

import com.ecms.bean.UnqualifiedTypeListInfo;
import com.ecms.dal.entity.UnqualifiedTypeListEntity;
import com.ecms.service.BaseService;
import com.ecms.service.UnqualifiedTypeListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("unqualifiedTypeList")
public class UnqualifiedTypeListController extends BaseController<UnqualifiedTypeListEntity, UnqualifiedTypeListInfo> {

	@Resource
	private UnqualifiedTypeListService unqualifiedTypeListService;

	@Override
	protected BaseService<UnqualifiedTypeListEntity, UnqualifiedTypeListInfo> getBaseService() {
		return unqualifiedTypeListService;
	}


}
