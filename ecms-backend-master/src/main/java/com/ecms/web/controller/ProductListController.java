package com.ecms.web.controller;

import com.ecms.bean.ProductListInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProductListEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProductListService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/productList")
public class ProductListController extends BaseController<ProductListEntity, ProductListInfo> {

	@Resource
	private ProductListService service;

	@Override
	protected BaseService<ProductListEntity, ProductListInfo> getBaseService() {
		return service;
	}
	@RequestMapping("list")
	public Result<Pager<ProductListInfo>> list(int pageNum, ProductListInfo info) {
		ExchangeUtils.convertBlank2Null(info);
//		info.getQueryId()
		Pager<ProductListInfo> pager = getBaseService().getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		service.splice(pager);
		return ResultUtils.success(pager);
	}

}
