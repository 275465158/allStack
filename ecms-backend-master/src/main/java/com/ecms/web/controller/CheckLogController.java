package com.ecms.web.controller;

import com.ecms.bean.CheckLogInfo;
import com.ecms.bean.PostInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.CheckLogEntity;
import com.ecms.dal.entity.PostEntity;
import com.ecms.service.BaseService;
import com.ecms.service.CheckLogService;
import com.ecms.service.PostService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("checkLog")
public class CheckLogController extends BaseController<CheckLogEntity, CheckLogInfo> {

	@Resource
	private CheckLogService checkLogService;

	@Override
	protected BaseService<CheckLogEntity, CheckLogInfo> getBaseService() {
		return checkLogService;
	}

	@RequestMapping("searchById")
	public Result<List<CheckLogInfo>> searchById(Integer id,Integer checkFlag){
		CheckLogInfo cli = new CheckLogInfo();
		cli.setCheckId(id);
		cli.setStatus(1);
		cli.setCheckFlag(checkFlag);
		cli.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<CheckLogInfo> cliList = checkLogService.getListBy(cli);
		return ResultUtils.success(cliList);
	}

}
