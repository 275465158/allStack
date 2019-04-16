package com.ecms.web.controller;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.ProcessFormatInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.ProcessFormatEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialBomService;
import com.ecms.service.ProcessFormatService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengli
 * 2018/9/3
 */
@RestController
@RequestMapping("processFormat")
public class ProcessFormatController extends BaseController<ProcessFormatEntity,ProcessFormatInfo> {

	@Resource
	private ProcessFormatService processFormatService;

	@Resource
	private MaterialBomService materialBomService;

	@Override
	protected BaseService<ProcessFormatEntity,ProcessFormatInfo> getBaseService() {
		return processFormatService;
	}

	/**
	 * 根据id查询工艺相关的格式
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchProcessFormatById")
	public Result<List<ProcessFormatInfo>> searchProcessFormatById(Integer id){
		ProcessFormatInfo processFormatInfo = new ProcessFormatInfo();
		processFormatInfo.setProcessId(id);
		List<ProcessFormatInfo> processFormatInfoList = processFormatService.getListBy(processFormatInfo);
		return ResultUtils.success(processFormatInfoList);
	}

	/**
	 * 先根据部件id查到工艺id，再根据工艺id查询相关的格式
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchByMaterialId")
	public Result<List<ProcessFormatInfo>> searchByMaterialId(Integer id){
		MaterialBomInfo mbi = materialBomService.getModel(id);
		Integer processId = mbi.getProcessId();
		List<ProcessFormatInfo> processFormatInfoList = new ArrayList<>();
		if (processId != null){
			ProcessFormatInfo pfi = new ProcessFormatInfo();
			pfi.setProcessId(processId);
			processFormatInfoList = processFormatService.getListBy(pfi);
		}
		return ResultUtils.success(processFormatInfoList);
	}
}
