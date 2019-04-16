package com.ecms.web.controller;

import com.ecms.bean.MaterialParameterInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialParameterEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialParameterService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("materialParameter")
public class MaterialParameterController extends BaseController<MaterialParameterEntity, MaterialParameterInfo> {

	@Resource
	private MaterialParameterService materialParameterService;
	
	@Override
	protected BaseService<MaterialParameterEntity, MaterialParameterInfo> getBaseService() {
		return materialParameterService;
	}

	/**
	 * 查询参数列表
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialParameterInfo>> detail(Integer id){
		MaterialParameterInfo materialParameterInfo = new MaterialParameterInfo();
		materialParameterInfo.setMaterialId(id);
		List<MaterialParameterInfo> list = materialParameterService.getListBy(materialParameterInfo);
		return ResultUtils.success(list);
	}

}
