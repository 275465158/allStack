package com.ecms.web.controller;

import com.ecms.bean.SellerPayTypeInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.SellerPayTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.SellerPayTypeService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("sellerPayType")
public class SellerPayTypeController extends BaseController<SellerPayTypeEntity, SellerPayTypeInfo> {

	@Resource
	private SellerPayTypeService sellerPayTypeService;

	@Override
	protected BaseService<SellerPayTypeEntity, SellerPayTypeInfo> getBaseService() {
		return sellerPayTypeService;
	}

	@RequestMapping("/all")
	public Result<List<SellerPayTypeInfo>> all() {
		List<SellerPayTypeInfo> list = sellerPayTypeService.getValidCompanyList();
		return ResultUtils.success(list);
	}

	@RequestMapping("/searchPager")
	public Result<Pager<SellerPayTypeInfo>> searchPager(int pageNum, int pageSize, SellerPayTypeInfo sellerPayTypeInfo){
		Pager<SellerPayTypeInfo> pager = sellerPayTypeService.getModelListByLike(pageNum,pageSize,sellerPayTypeInfo,true);
		return ResultUtils.success(pager);
	}

	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		sellerPayTypeService.updateStatus(id);
		return ResultUtils.success();
	}
	@RequestMapping("insertPayType")
	public Result<Void> insertBy(SellerPayTypeInfo sellerPayTypeInfo) {
		SellerPayTypeInfo sii = new SellerPayTypeInfo();
		sii.setName(sellerPayTypeInfo.getName());
		sii.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<SellerPayTypeInfo> list = sellerPayTypeService.getListBy(sii);
		sellerPayTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		if (list.size()==0){
			sellerPayTypeService.insertBy(sellerPayTypeInfo);
		}else {
			return ResultUtils.failure("该公司已经存在该付款方式");
		}
		return ResultUtils.success();
	}

}
