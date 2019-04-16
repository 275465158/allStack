package com.ecms.web.controller;

import com.ecms.bean.ToolTypeInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ToolTypeEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ToolTypeService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

/**
 * @author zhengli
 * 2018/9/4
 */
@RestController
@RequestMapping("toolType")
public class ToolTypeController extends BaseController<ToolTypeEntity,ToolTypeInfo> {

	@Resource
	private ToolTypeService toolTypeService;

	@Override
	protected BaseService<ToolTypeEntity,ToolTypeInfo> getBaseService() {
		return toolTypeService;
	}

	/**
	 * 工具类型列表查询
	 * @param pageNum
	 * @param toolTypeInfo
	 * @return
	 */
	@RequestMapping("/searchToolTypePager")
	public Result<Pager<ToolTypeInfo>> searchToolTypePager(int pageNum,int pageSize, ToolTypeInfo toolTypeInfo) {
		ExchangeUtils.convertBlank2Null(toolTypeInfo);
		Pager<ToolTypeInfo> pager = toolTypeService.getModelListByLike(pageNum, pageSize, toolTypeInfo,true);
		return ResultUtils.success(pager);
	}

	/**
	 * 新增工具类型
	 * @param toolTypeInfo
	 * @return
	 */
	@RequestMapping("/saveToolType")
	public Result<Void> saveTool(ToolTypeInfo toolTypeInfo){
		toolTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		ToolTypeInfo toolTypeInfo1 = new ToolTypeInfo();
		toolTypeInfo1.setToolTypeName(toolTypeInfo.getToolTypeName());
		//新建一个对象，把新增的名称放入新对象，把名字当条件查询数据库是否有这个名字的数据
		List<ToolTypeInfo> toolTypeInfoList = toolTypeService.getListBy(toolTypeInfo1);
		//如果没有这个名字的数据，就新增
		if(toolTypeInfoList.size()==0){
			toolTypeService.saveOrUpdate(toolTypeInfo);
		}else {
			return ResultUtils.failure("工具类型名称已存在");
		}
		return ResultUtils.success();
	}

	/**
	 * 停用/启用工具类型
	 * @param id
	 */
	@RequestMapping("/updateStatus")
	public Result<Void> updateStatus(Integer id){
		toolTypeService.updateStatus(id);
		return ResultUtils.success();
	}

	/**
	 * 查询工具列表
	 * @param toolTypeInfo
	 * @return
	 */
	@RequestMapping("/searchToolTypeList")
	public Result<List<ToolTypeInfo>> searchToolTypeList(ToolTypeInfo toolTypeInfo){
		ExchangeUtils.convertBlank2Null(toolTypeInfo);
		toolTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		toolTypeInfo.setStatus(STATUS_DEFAULT);
		List<ToolTypeInfo> toolTypeInfoList = toolTypeService.getListBy(toolTypeInfo);
		if (toolTypeInfoList.size()!=0){
			for (int i = 0;i < toolTypeInfoList.size();i++){
				toolTypeInfoList.get(i).setDefaultModelNumber(0);
			}
		}
		return ResultUtils.success(toolTypeInfoList);
	}
}
