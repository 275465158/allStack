package com.ecms.web.controller;

import com.ecms.bean.MaterialBomParamNameInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomParamNameEntity;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("materialBomParamName")
public class MaterialBomParamNameController extends BaseController<MaterialBomParamNameEntity, MaterialBomParamNameInfo> {

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Override
	protected BaseService<MaterialBomParamNameEntity, MaterialBomParamNameInfo> getBaseService() {
		return materialBomParamNameService;
	}

	/**
	 * 查询参数名称数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialBomParamNameInfo>> detail(Integer id){
		MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
		materialBomParamNameInfo.setMaterialId(id);
		materialBomParamNameInfo.setStatus(1);
		List<MaterialBomParamNameInfo> list = materialBomParamNameService.getListBy(materialBomParamNameInfo);
		return ResultUtils.success(list);
	}

	@RequestMapping("/deleteParamName")
	public Result<Void> deleteParamName(Integer id){
		materialBomParamNameService.deleteBy(id);
		return ResultUtils.success();
	}
}
