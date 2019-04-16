package com.ecms.web.controller;

import com.ecms.bean.MaterialBomParamSpecificationInfo;
import com.ecms.bean.ProductParameterSpecificationInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomParamSpecificationEntity;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("materialBomParamSpecification")
public class MaterialBomParamSpecificationController extends BaseController<MaterialBomParamSpecificationEntity, MaterialBomParamSpecificationInfo> {

	@Resource
	private MaterialBomParamSpecificationService materialBomParamSpecificationService;

	@Resource
	private ProductParameterSpecificationService productParameterSpecificationService;

	
	@Override
	protected BaseService<MaterialBomParamSpecificationEntity, MaterialBomParamSpecificationInfo> getBaseService() {
		return materialBomParamSpecificationService;
	}

	/**
	 * 查询参数规格名称数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialBomParamSpecificationInfo>> detail(Integer id){
		MaterialBomParamSpecificationInfo materialBomParamSpecificationInfo = new MaterialBomParamSpecificationInfo();
		materialBomParamSpecificationInfo.setMaterialId(id);
		materialBomParamSpecificationInfo.setStatus(1);
		List<MaterialBomParamSpecificationInfo> list = materialBomParamSpecificationService.getListBy(materialBomParamSpecificationInfo);
		if (list.size()!=0){
			for (int i = 0;i < list.size();i++){
				ProductParameterSpecificationInfo productParameterSpecificationInfo = productParameterSpecificationService.getModel(list.get(i).getMaterialParamSpecificationId());
				if (productParameterSpecificationInfo != null){
					list.get(i).setProductParameterSpecificationInfo(productParameterSpecificationInfo);
				}
			}
		}
		return ResultUtils.success(list);
	}

	@RequestMapping("/deleteParam")
	public Result<Void> deleteParamName(Integer id){
		materialBomParamSpecificationService.deleteBy(id);
		return ResultUtils.success();
	}
}
