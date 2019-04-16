package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ecms.common.Pager;
import com.ecms.dal.entity.DepartmentEntity;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecms.bean.RepertoryInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.RepertoryEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DepartmentService;
import com.ecms.service.RepertoryService;
import com.ecms.utils.ResultUtils;

@Controller
@RequestMapping("repertory")
public class RepertoryController extends BaseController<RepertoryEntity, RepertoryInfo> {

	@Resource
	private RepertoryService repertoryService;
	@Resource
	private DepartmentService departmentService;

	@Override
	protected BaseService<RepertoryEntity, RepertoryInfo> getBaseService() {
		return repertoryService;
	}

	@RequestMapping("/info")
	public Result<RepertoryInfo> info(
			@RequestParam(value = "repertoryId", required = false, defaultValue = "0") int repertoryId) {
		RepertoryInfo repInfo = new RepertoryInfo();
		if (repertoryId > 0) {
			repInfo = repertoryService.getModel(repertoryId);
		}
		return ResultUtils.success(repInfo);
	}

	@RequestMapping("list")
	public Result<Pager<RepertoryInfo>> list(int pageNum, RepertoryInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		Pager<RepertoryInfo> pager = getBaseService().getModelListByLike(pageNum, DEFAULT_PAGE_SIZE, info, false);
		List<RepertoryInfo> repertoryInfoList = pager.getList();
		if(repertoryInfoList.size() != 0){
			for(RepertoryInfo repertoryInfo : repertoryInfoList){
				DepartmentEntity de = departmentService.getEntity(repertoryInfo.getRepertoryDepartmentId());
				if(de != null){
					repertoryInfo.setRepertoryDepartmentName(de.getDepartmentName());
				}
			}
		}
		return ResultUtils.success(pager);
	}

	@InitBinder
	protected void init(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("/getList")
	public Result<List<RepertoryInfo>> getList(RepertoryInfo repertoryInfo){
		ExchangeUtils.convertBlank2Null(repertoryInfo);
		repertoryInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<RepertoryInfo> list = repertoryService.getListBy(repertoryInfo);
		return ResultUtils.success(list);
	}
	@RequestMapping("/saveOrUpdateRepertoy")
	public Result<RepertoryInfo> saveOrUpdate(RepertoryInfo info) {
		info.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		return ResultUtils.success(getBaseService().saveOrUpdate(info));
	}

	/**
	 * 进入仓库权限
	 * @param repertoryId 仓库id
	 * @return
	 */
	@RequestMapping("/inAuthority")
	public Result<Void> inAuthority(Integer repertoryId){
		boolean flag = false;
		Integer loginEmployeeId = Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId());
		Integer roleNum = LoginFilter.getCurrentUser().getRole();
		if(roleNum != 511){
			RepertoryInfo ri = repertoryService.getModel(repertoryId);
			String repertoryManagerStr = ri.getRepertoryManager();
			repertoryManagerStr = repertoryManagerStr.substring(1,repertoryManagerStr.length()-1);
			if(!"".equals(repertoryManagerStr)){
				String[] arrayStr = repertoryManagerStr.split("}");

				for(int i=0;i<arrayStr.length;i++){
					String arrStr = "";
					String firstStr = arrayStr[i].substring(0,1);
					if(firstStr.indexOf(",") == 0){
						arrStr = arrayStr[i].substring(1,arrayStr[i].length()-1)+"\"}";
					}else {
						arrStr = arrayStr[i]+"}";
					}
					System.err.println(arrStr);

					JSONObject repertoryManagerJs = JSONObject.fromObject(arrStr);
					Integer employeeId = Integer.parseInt(repertoryManagerJs.get("id").toString());
					if(employeeId.equals(loginEmployeeId)){
						flag = true;
						break;
					}
				}
			}else{
				flag = true;
			}
		}else{
			flag = true;
		}
		if(flag == false){
			return ResultUtils.failure("您没有该权限，请联系管理员！");
		}else{
			return ResultUtils.success();

		}
	}
	
}
