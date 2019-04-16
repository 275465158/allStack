package com.ecms.web.controller;

import com.ecms.bean.SellerPayRuleInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerPayRuleEntity;
import com.ecms.service.BaseService;
import com.ecms.service.SellerPayRuleService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("sellerPayRule")
public class SellerPayRuleController extends BaseController<SellerPayRuleEntity, SellerPayRuleInfo> {

	@Resource
	private SellerPayRuleService sellerPayRuleService;

	@Override
	protected BaseService<SellerPayRuleEntity, SellerPayRuleInfo> getBaseService() {
		return sellerPayRuleService;
	}

	@RequestMapping("/all")
	public Result<List<SellerPayRuleInfo>> all() {
		List<SellerPayRuleInfo> list = sellerPayRuleService.getValidCompanyList();
		return ResultUtils.success(list);
	}

	@RequestMapping("/searchPager")
	public Result<Pager<SellerPayRuleInfo>> searchPager(int pageNum, int pageSize, SellerPayRuleInfo sellerPayRuleInfo){
		Pager<SellerPayRuleInfo> pager = sellerPayRuleService.getModelListByLike(pageNum,pageSize,sellerPayRuleInfo,true);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		sellerPayRuleService.updateStatus(id);
		return ResultUtils.success();
	}
	@RequestMapping("insertPayRule")
	public Result<Void> insertBy(SellerPayRuleInfo sellerPayRuleInfo) {
		SellerPayRuleInfo sii = new SellerPayRuleInfo();
		sii.setName(sellerPayRuleInfo.getName());
		sii.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SellerPayRuleInfo> list = sellerPayRuleService.getListBy(sii);
		sellerPayRuleInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		if (list.size()==0){
			sellerPayRuleService.insertBy(sellerPayRuleInfo);
		}else {
			return ResultUtils.failure("该公司已经存在该付款方式");
		}
		return ResultUtils.success();
	}

}
