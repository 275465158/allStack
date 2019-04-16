/**
 * 
 */
package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.bean.BomTaskDetailInfo;
import com.ecms.bean.BomTaskInfo;
import com.ecms.bean.SellerOrderInfo;
import com.ecms.common.Result;
import com.ecms.common.SellerOrderProgress;
import com.ecms.dal.entity.BomTaskEntity;
import com.ecms.service.BaseService;
import com.ecms.service.BomTaskDetailService;
import com.ecms.service.BomTaskService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.SellerOrderService;
import com.ecms.utils.CollectionEditorSupport;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description
 */
@RestController
@RequestMapping("bomtask")
public class BomTaskController extends BaseController<BomTaskEntity, BomTaskInfo> {

	@Resource
	private BomTaskService service;
	@Resource
	private BomTaskDetailService detailService;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private SellerOrderService sellerOrderService;
	/* (non-Javadoc)
	 * @see com.ecms.web.controller.BaseController#getBaseService()
	 */
	@Override
	protected BaseService<BomTaskEntity, BomTaskInfo> getBaseService() {
		return service;
	}
	
	@RequestMapping("info")
	public Result<BomTaskInfo> info(int id){
		BomTaskInfo taskInfo = service.getModel(id);
		BomTaskDetailInfo searchDetail = new BomTaskDetailInfo();
		searchDetail.setTaskId(id);
		List<BomTaskDetailInfo> detailList = detailService.getListBy(searchDetail);
		detailList.stream().forEach(detail->{
			detail.setMaterialBom(bomService.getModel(detail.getMaterialId()));
		});
		taskInfo.setTaskDetail(detailList);
		return ResultUtils.success(taskInfo);
	}
	
	@Override
	public Result<BomTaskInfo> saveOrUpdate(BomTaskInfo info){
		if(info.getDraftsman().equals("")) {
		info.setDraftsman(LoginFilter.getCurrentUser().getUserName());
		}
		BomTaskInfo savedInfo = service.saveOrUpdate(info);
		info.getTaskDetail().stream().forEach(detail->{
			detail.setTaskId(savedInfo.getId());
			detailService.saveOrUpdate(detail);
		});
		return ResultUtils.success(savedInfo);
	}
	
	@RequestMapping("complete")
	public Result<BomTaskInfo> complete(int id){
		BomTaskInfo info = service.getModel(id);
		info.setTaskProgress("完成");
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		info.setCompletedDate(shortdateFormat.format(new Date()));
		BomTaskInfo savedInfo = service.saveOrUpdate(info);
		SellerOrderInfo sellerOrderInfo = new SellerOrderInfo();
		sellerOrderInfo = sellerOrderService.getModel(savedInfo.getOrderId());
		sellerOrderInfo.setProgress(SellerOrderProgress.CONFIRM.getId());
		sellerOrderInfo.setProgressDisplay(SellerOrderProgress.CONFIRM.getName());
		sellerOrderService.saveOrUpdate(sellerOrderInfo);
		return ResultUtils.success(savedInfo);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder)throws ServletException{
		binder.registerCustomEditor(Collection.class, "taskDetail", new CollectionEditorSupport(BomTaskDetailInfo.class));
	}
}
