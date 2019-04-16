package com.ecms.web.controller;

import com.ecms.bean.ScheduleDetailInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ScheduleDetailEntity;
import com.ecms.service.BaseService;
import com.ecms.service.ScheduleDetailService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("scheduleDetail")
public class ScheduleDetailController extends BaseController<ScheduleDetailEntity, ScheduleDetailInfo>  {

	@Resource
	private ScheduleDetailService scheduleDetailService;

	@Override
	protected BaseService<ScheduleDetailEntity, ScheduleDetailInfo> getBaseService() {
		return scheduleDetailService;
	}

	/**
	 * 根据月份获取当月详情列表
	 * @param scheduleDetailInfo
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<ScheduleDetailInfo>> getScheduleDetail(ScheduleDetailInfo scheduleDetailInfo) {
		ExchangeUtils.convertBlank2Null(scheduleDetailInfo);
		List<ScheduleDetailInfo> list = scheduleDetailService.getListBy(scheduleDetailInfo);
		return ResultUtils.success(list);
	}

	/**
	 * 获取月计划列表
	 * @param scheduleDetailInfo
	 * @return
	 */
	@RequestMapping("scheduleList")
	public Result<List<Map<String,Object>>> getScheduleList(ScheduleDetailInfo scheduleDetailInfo) {
		ExchangeUtils.convertBlank2Null(scheduleDetailInfo);
		String scheduleYear = "";
		//默认查询当前年份数据
		if(scheduleDetailInfo.getScheduleYear() == null){
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			scheduleYear = year+"";
		}else{
			scheduleYear = scheduleDetailInfo.getScheduleYear().toString();
		}
		List<Map<String,Object>> list = scheduleDetailService.getScheduleList(scheduleDetailInfo,scheduleYear);
		return ResultUtils.success(list);
	}

	/**
	 * 获取查询年份下拉菜单数据
	 * @param scheduleDetailInfo
	 * @return
	 */
	@RequestMapping("selectionList")
	public Result<List<Map<String,Object>>> getSelectionList(ScheduleDetailInfo scheduleDetailInfo) {
		ExchangeUtils.convertBlank2Null(scheduleDetailInfo);
		List<Map<String,Object>> list = scheduleDetailService.getSelectionList(scheduleDetailInfo);
		return ResultUtils.success(list);
	}

	/**
	 * 更新月计划详情表
	 * @param scheduleDetailInfo
	 * @return
	 */
	@RequestMapping("updateDetail")
	public Result<Void> updateScheduleDetail(ScheduleDetailInfo scheduleDetailInfo) {
		scheduleDetailService.updateScheduleDetail(scheduleDetailInfo);
		return ResultUtils.success();
	}
}
