package com.ecms.web.controller;

import com.ecms.bean.MaterialCheckInfo;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialCheckEntity;
import com.ecms.service.BaseService;
import com.ecms.service.MaterialCheckService;
import com.ecms.service.ProductAcceptanceService;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("materialCheck")
public class MaterialCheckController extends BaseController<MaterialCheckEntity, MaterialCheckInfo> {

	@Resource
	private MaterialCheckService materialCheckService;

	@Resource
	private ProductAcceptanceService productAcceptanceService;
	
	@Override
	protected BaseService<MaterialCheckEntity, MaterialCheckInfo> getBaseService() {
		return materialCheckService;
	}

	/**
	 * 查询验收标准
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialCheckInfo>> detail(Integer id){
		MaterialCheckInfo materialCheckInfo = new MaterialCheckInfo();
		materialCheckInfo.setMaterialId(id);
		materialCheckInfo.setStatus(1);
		//根据物料id去物料-验收标准中间表查询数据
		List<MaterialCheckInfo> list = materialCheckService.getListBy(materialCheckInfo);
		if (list.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				//根据中间表中的验收标准id去验收标准表中查询数据，然后放到list里面
				Integer checkId = list.get(i).getCheckId();
				list.get(i).setProductAcceptanceInfo(productAcceptanceService.getModel(checkId));
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 删除中间表数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteCheck")
	public Result<Void> deleteCheck(Integer id){
		MaterialCheckInfo materialCheckInfo = new MaterialCheckInfo();
		materialCheckInfo.setMaterialId(id);
		List<MaterialCheckInfo>  mciList = materialCheckService.getListBy(materialCheckInfo);
		if (mciList.size()!=0){
			Integer iId = mciList.get(0).getId();
			materialCheckService.deleteBy(iId);
		}
		return ResultUtils.success();
	}
}
