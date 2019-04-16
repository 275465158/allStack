package com.ecms.web.controller;

import com.ecms.bean.CheckStandardInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.CheckStandardEntity;
import com.ecms.service.BaseService;
import com.ecms.service.CheckStandardService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author zhengli
 * 2018/9/3
 */
/**
 * @author pratice
 *
 */
@RestController
@RequestMapping("checkStandard")
public class CheckStandardController extends BaseController<CheckStandardEntity,CheckStandardInfo> {

	@Resource
	private CheckStandardService checkStandardService;

	@Override
	protected BaseService<CheckStandardEntity,CheckStandardInfo> getBaseService() {
		return checkStandardService;
	}

	@RequestMapping("/searchCheckStandardList")
	public Result<Pager<CheckStandardInfo>> searchCheckStandardList(int pageNum,CheckStandardInfo checkStandardInfo){
		ExchangeUtils.convertBlank2Null(checkStandardInfo);
		Pager<CheckStandardInfo> pager = checkStandardService.getModelList(pageNum, DEFAULT_PAGE_SIZE, checkStandardInfo);
		return ResultUtils.success(pager);
	}


}
