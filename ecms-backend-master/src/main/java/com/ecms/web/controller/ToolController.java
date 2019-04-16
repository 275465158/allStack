package com.ecms.web.controller;

import com.ecms.bean.ToolInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.ToolEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ToolService;
import com.ecms.service.ToolTypeService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhengli
 * 2018/9/3
 */
@RestController
@RequestMapping("tool")
public class ToolController extends BaseController<ToolEntity,ToolInfo> {

	@Resource
	private ToolService toolService;

	@Resource
	private ToolTypeService toolTypeService;

	@Override
	protected BaseService<ToolEntity,ToolInfo> getBaseService() {
		return toolService;
	}

	/**
	 * 工具列表查询
	 * @param pageNum
	 * @param toolInfo
	 * @return
	 */
	@RequestMapping("/searchToolList")
	public Result<Pager<ToolInfo>> searchToolList(int pageNum,int pageSize, ToolInfo toolInfo) {
		ExchangeUtils.convertBlank2Null(toolInfo);
		Pager<ToolInfo> pager = toolService.getModelListByLike(pageNum, pageSize, toolInfo,true);
		for (ToolInfo ti : pager.getList()){
			String toolSpec = "";
			ti.setIdToPage("TN"+ti.getId());
			if (ti.getToolMinSpec()!=null){
				toolSpec = ti.getToolMinSpec().toString();
				if (ti.getToolMaxSpec()!=null){
					toolSpec = toolSpec + "-" + ti.getToolMaxSpec();
				}
			}else {
				if (ti.getToolMaxSpec()!=null){
					toolSpec = ti.getToolMaxSpec().toString();
				}
			}
			ti.setToolSpec(toolSpec);
			if(ti.getToolTypeId()!=null){
				ti.setToolTypeName(toolTypeService.getModel(ti.getToolTypeId()).getToolTypeName());
			}
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 新增工具
	 * @param toolInfo
	 * @return
	 */
	@RequestMapping("/saveTool")
	public Result<Void> saveTool(ToolInfo toolInfo){
		toolInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		ToolInfo ti = new ToolInfo();
		ti.setToolName(toolInfo.getToolName());
		List<ToolInfo> list =  toolService.getListBy(ti);
		if (list.size() == 0){
			toolInfo.setToolCode("TN"+toolService.getNextNumber());
			toolService.saveOrUpdate(toolInfo);
		}else {
			return ResultUtils.failure("工具名称已存在");
		}
		return ResultUtils.success();
	}

}
