package com.ecms.web.controller;

import com.ecms.annotation.SaveLog;
import com.ecms.bean.PostInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.PostEntity;
import com.ecms.service.BaseService;
import com.ecms.service.EmployeeService;
import com.ecms.service.PostService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("post")
public class PostController extends BaseController<PostEntity, PostInfo> {

	@Resource
	private PostService postService;
	@Resource
	private EmployeeService employeeService;

	@Override
	protected BaseService<PostEntity, PostInfo> getBaseService() {
		return postService;
	}

	@RequestMapping("all")
	public Result<Pager<PostInfo>> all(int pageNum, int pageSize, PostInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		info.setPostNumber(info.getPostNumber());
		if (info.getStatus() == 2) {
			info.setStatus(null);
		}
		Pager<PostInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, true);
		for (PostInfo postInfo : pager.getList()) {
			postInfo.setCount(employeeService.getCountByPostId(postInfo.getId()));
		}
		return ResultUtils.success(pager);
	}

	@Override
	@RequestMapping("insert")
	public Result<Void> insertBy(PostInfo info) {
		info.setPostNumber(postService.getNextNumber());
		getBaseService().insertBy(info);
		return ResultUtils.success();
	}

	@SaveLog
	@RequestMapping("/status/update")
	public Result<Void> updateStatus(int id) {
		postService.updateStatus(id);
		return ResultUtils.success();
	}

}
