package com.ecms.web.controller;

import com.ecms.bean.TradeMarkInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.TradeMarkEntity;
import com.ecms.service.BaseService;
import com.ecms.service.TradeMarkService;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("tradeMark")
public class TradeMarkController extends BaseController<TradeMarkEntity, TradeMarkInfo> {

	@Resource
	private TradeMarkService tradeMarkService;

	@Override
	protected BaseService<TradeMarkEntity, TradeMarkInfo> getBaseService() {
		return tradeMarkService;
	}

	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param tradeMarkInfo
	 * @return
	 */
	@RequestMapping("/searchPager")
	public Result<Pager<TradeMarkInfo>> searchPager(int pageNum, int pageSize, TradeMarkInfo tradeMarkInfo){
		Pager<TradeMarkInfo> pager = tradeMarkService.getModelListByLike(pageNum,pageSize,tradeMarkInfo,true);
		return ResultUtils.success(pager);
	}

	/**
	 * 更新状态
	 * @param id
	 * @return
	 */
	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		tradeMarkService.updateStatus(id);
		return ResultUtils.success();
	}

	/**
	 * 新增商标
	 * @param tradeMarkInfo
	 * @return
	 */
	@RequestMapping("insertTradeMark")
	public Result<Void> insertBy(TradeMarkInfo tradeMarkInfo) {
		TradeMarkInfo tmi = new TradeMarkInfo();
		tmi.setTradeMark(tradeMarkInfo.getTradeMark());
		tmi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<TradeMarkInfo> list = tradeMarkService.getListBy(tmi);
		tradeMarkInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		if (list.size()==0){
			tradeMarkService.insertBy(tradeMarkInfo);
		}else {
			return ResultUtils.failure("该公司已经存在该商标名称");
		}
		return ResultUtils.success();
	}

	/**
	 * 查询list
	 * @return
	 */
	@RequestMapping("searchList")
	public Result<List<TradeMarkInfo>> searchList(){
		TradeMarkInfo tmi = new TradeMarkInfo();
		tmi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<TradeMarkInfo> tmiList = tradeMarkService.getListBy(tmi);
		return ResultUtils.success(tmiList);
	}
}
