package com.ecms.web.controller;

import com.ecms.bean.ProcessToolTypeInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcessToolTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ProcessToolTypeService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhengli
 * 2018/9/3
 */
@RestController
@RequestMapping("processToolType")
public class ProcessToolTypeController extends BaseController<ProcessToolTypeEntity,ProcessToolTypeInfo> {

	@Resource
	private ProcessToolTypeService processToolTypeService;

	@Override
	protected BaseService<ProcessToolTypeEntity,ProcessToolTypeInfo> getBaseService() {
		return processToolTypeService;
	}

	/**
	 * 查询工艺关联的工具类型
	 * @param id
	 */
	@RequestMapping("/searchToolTypeById")
	public Result<List<ProcessToolTypeInfo>> searchToolTypeById(Integer id){
		ProcessToolTypeInfo processToolTypeInfo = new ProcessToolTypeInfo();
		processToolTypeInfo.setProcessId(id);
		List<ProcessToolTypeInfo> processToolTypeInfoList = processToolTypeService.getListBy(processToolTypeInfo);
		return ResultUtils.success(processToolTypeInfoList);
	}
}
