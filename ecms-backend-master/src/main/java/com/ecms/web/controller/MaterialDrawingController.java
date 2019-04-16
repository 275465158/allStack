package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialDrawingEntity;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("materialDrawing")
public class MaterialDrawingController extends BaseController<MaterialDrawingEntity, MaterialDrawingInfo> {

	@Resource
	private MaterialDrawingService materialDrawingService;

	@Resource
	private ProductDrawingService productDrawingService;

	@Resource
	private MaterialBomService materialBomService;
	
	@Override
	protected BaseService<MaterialDrawingEntity, MaterialDrawingInfo> getBaseService() {
		return materialDrawingService;
	}

	/**
	 * 查询图纸数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialDrawingInfo>> detail(Integer id){
		MaterialDrawingInfo materialDrawingInfo = new MaterialDrawingInfo();
		materialDrawingInfo.setMaterialId(id);
		materialDrawingInfo.setStatus(1);
		//根据物料id去物料-图纸中间表查询数据
		List<MaterialDrawingInfo> list = materialDrawingService.getListBy(materialDrawingInfo);
		if (list.size()!=0){
			for (int i=0;i<list.size();i++){
				//根据中间表数据中的图纸id去图纸表查询数据，然后放到list里面
				Integer drawingId = list.get(i).getDrawingId();
				if(drawingId != null ){
					list.get(i).setProductDrawingInfo(productDrawingService.getModel(drawingId));
				}
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 删除中间表数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteDrawing")
	public Result<Void> deleteDrawing(Integer id){
		MaterialDrawingInfo materialDrawingInfo = new MaterialDrawingInfo();
		materialDrawingInfo.setMaterialId(id);
		List<MaterialDrawingInfo> mdiList = materialDrawingService.getListBy(materialDrawingInfo);
		if (mdiList.size()!=0){
			Integer iId = mdiList.get(0).getId();
			materialDrawingService.deleteBy(iId);
		}
		MaterialBomInfo mbi = materialBomService.getModel(id);
		mbi.setDrawingCode(null);
		materialBomService.updateByPrimaryKeySelective(mbi);
		return ResultUtils.success();
	}

	/**
	 * 查询图纸列表
	 * @param materialDrawingInfo
	 * @return
	 */
	@RequestMapping("/materialDrawingList")
	public Result<List<MaterialDrawingInfo>> materialDrawingList(MaterialDrawingInfo materialDrawingInfo){
		materialDrawingInfo.setStatus(STATUS_DEFAULT);
		//根据物料id去物料-图纸中间表查询数据
		List<MaterialDrawingInfo> list = materialDrawingService.getListBy(materialDrawingInfo);
		if (list.size()!=0){
			for (int i=0;i<list.size();i++){
				//根据中间表数据中的图纸id去图纸表查询数据，然后放到list里面
				Integer drawingId = list.get(i).getDrawingId();
				list.get(i).setProductDrawingInfo(productDrawingService.getModel(drawingId));

			}
		}
		return ResultUtils.success(list);
	}
}
