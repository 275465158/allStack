package com.ecms.web.controller;

import com.ecms.bean.SellerInvoiceInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerInvoiceEntity;
import com.ecms.service.BaseService;
import com.ecms.service.SellerInvoiceService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("sellerInvoice")
public class SellerInvoiceController extends BaseController<SellerInvoiceEntity, SellerInvoiceInfo> {

	@Resource
	private SellerInvoiceService sellerInvoiceService;

	@Override
	protected BaseService<SellerInvoiceEntity, SellerInvoiceInfo> getBaseService() {
		return sellerInvoiceService;
	}

	@RequestMapping("/all")
	public Result<List<SellerInvoiceInfo>> all() {
		List<SellerInvoiceInfo> list = sellerInvoiceService.getValidCompanyList();
		return ResultUtils.success(list);
	}
	@RequestMapping("/searchPager")
	public Result<Pager<SellerInvoiceInfo>> searchPager(int pageNum, int pageSize, SellerInvoiceInfo sellerInvoiceInfo){
		Pager<SellerInvoiceInfo> pager = sellerInvoiceService.getModelListByLike(pageNum,pageSize,sellerInvoiceInfo,true);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		sellerInvoiceService.updateStatus(id);
		return ResultUtils.success();
	}

	@RequestMapping("/searchByName")
	public Result<List<SellerInvoiceInfo>> searchByName(String name){
		SellerInvoiceInfo sii = new SellerInvoiceInfo();
		sii.setName(name);
		List<SellerInvoiceInfo> list = sellerInvoiceService.getListBy(sii);
		return ResultUtils.success(list);
	}

	@RequestMapping("insertSii")
	public Result<Void> insertBy(SellerInvoiceInfo sellerInvoiceInfo) {
		SellerInvoiceInfo sii = new SellerInvoiceInfo();
		sii.setName(sellerInvoiceInfo.getName());
		sii.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SellerInvoiceInfo> list = sellerInvoiceService.getListBy(sii);
		sellerInvoiceInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		if (list.size()==0){
			sellerInvoiceService.insertBy(sellerInvoiceInfo);
		}else {
			return ResultUtils.failure("该公司已经存在该发票名称");
		}
		return ResultUtils.success();
	}


}
