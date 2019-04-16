package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialRelationEntity;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;

import static com.ecms.common.BusinessConstants.STATUS_DEFAULT;

@RestController
@RequestMapping("materialRelation")
public class MaterialRelationController extends BaseController<MaterialRelationEntity, MaterialRelationInfo> {

	@Resource
	private MaterialRelationService materialRelationService;

	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private ProductionIssueDetailService productionIssueDetailService;

	@Resource
	private ProductionIssueService productionIssueService;

	@Resource
	private ProduceMaterialReturnLogService produceMaterialReturnLogService;

	@Resource
	private ProductTypeService productTypeService;
	
	@Override
	protected BaseService<MaterialRelationEntity, MaterialRelationInfo> getBaseService() {
		return materialRelationService;
	}

	/**
	 * 查询物料->物料
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<List<MaterialRelationInfo>> detail(Integer id,String productionNumber){
		MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
		materialRelationInfo.setMaterialParentId(id);
		materialRelationInfo.setStatus(STATUS_DEFAULT);
		materialRelationInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		//根据父id查询中间表中的子id
		List<MaterialRelationInfo> list = materialRelationService.getListBy(materialRelationInfo);
		if (list.size()!=0) {
			Float issueQty = 0f;
			Float returnIssueQty = 0f;
			for (int i = 0; i < list.size(); i++) {
				//根据子id去物料表查询数据
				list.get(i).setMaterialBomInfo(materialBomService.getModel(list.get(i).getMaterialChildId()));
				issueQty = productionIssueDetailService.getIssueQty(list.get(i).getMaterialChildId(),productionNumber,LoginFilter.getCurrentUser().getCompanyId());
				if(issueQty == null){
					issueQty = 0f;
				}
				returnIssueQty = produceMaterialReturnLogService.getReturnedQty(list.get(i).getMaterialChildId(), productionNumber, LoginFilter.getCurrentUser().getCompanyId());

				float scale = issueQty-returnIssueQty;
				DecimalFormat fnum = new DecimalFormat("##0.00");
				String dd = fnum.format(scale);
				list.get(i).setIssueQty(Float.parseFloat(dd));
				ProductTypeInfo productTypeInfo = new ProductTypeInfo();
				productTypeInfo.setNumber(materialBomService.getModel(list.get(i).getMaterialChildId()).getNumber());
				productTypeInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				productTypeInfo.setStatus(STATUS_DEFAULT);
				List<ProductTypeInfo> ptiList = productTypeService.getListBy(productTypeInfo);
				if(ptiList.size() != 0){
					list.get(i).setNumberType(ptiList.get(0).getType());
				}
				list.get(i).setMaterialBomParamValueStr(getParamValue(list.get(i).getMaterialChildId()));
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 生产计划查询物料清单->物料
	 * @param id
	 * @return
	 * @by Richie
	 */
	@RequestMapping("/materialList")
	public Result<List<MaterialRelationInfo>> materialList(Integer id,String productionNumber,Float materialNum){
		MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
		materialRelationInfo.setMaterialParentId(id);
		materialRelationInfo.setStatus(STATUS_DEFAULT);
		//根据父id查询中间表中的子id
		List<MaterialRelationInfo> list = materialRelationService.getListBy(materialRelationInfo);
		if (list.size()!=0) {
			for(MaterialRelationInfo mri : list){
				mri.setMaterialBomInfo(materialBomService.getModel(mri.getMaterialChildId()));
				mri.setMaterialBomParamValueStr(getParamValue(mri.getMaterialChildId()));
				Float issueQty = productionIssueDetailService.getIssueQty(mri.getMaterialChildId(),productionNumber,LoginFilter.getCurrentUser().getCompanyId());
				Float returnedQty = produceMaterialReturnLogService.getReturnedQty(mri.getMaterialChildId(),productionNumber,LoginFilter.getCurrentUser().getCompanyId());
				ProductionIssueInfo productionIssueInfo = new ProductionIssueInfo();
				productionIssueInfo.setProductionNumber(productionNumber);
				productionIssueInfo.setMaterialId(mri.getMaterialChildId());
				List<ProductionIssueInfo> piiList = productionIssueService.getListBy(productionIssueInfo);
				Float usedQty = 0f;
				if(piiList.size() != 0){
					usedQty = piiList.get(0).getUsedQty();
				}

				if(issueQty == null){
					issueQty = 0f;
				}
				if(returnedQty == null){
					returnedQty = 0f;
				}
				DecimalFormat df = new DecimalFormat("###.00");
				String issueQtyStr = df.format(issueQty-returnedQty);
				mri.setIssueQty(Float.parseFloat(issueQtyStr));

				mri.setReturnedQty(returnedQty);

				String stayIssueQtyStr = df.format(materialNum*mri.getQuantity()-issueQty+returnedQty);
				mri.setStayIssueQty(Float.parseFloat(stayIssueQtyStr));

				String receiveNum = df.format(materialNum*mri.getQuantity());
				mri.setReceiveNum(Float.parseFloat(receiveNum));

				String produceQtyStr = df.format((issueQty-returnedQty-usedQty)/mri.getQuantity());
				mri.setProduceQty(Float.parseFloat(produceQtyStr));
				mri.setUsedQty(usedQty);
			}
		}
		return ResultUtils.success(list);
	}

	/**
	 * 生产计划查询物料清单->物料
	 * @param id
	 * @return
	 * @by Richie
	 */
	@RequestMapping("/getMaxProduceQty")
	public Result<Double> getMaxProduceQty(Integer id,String productionNumber,Float materialNum){
		MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
		materialRelationInfo.setMaterialParentId(id);
		materialRelationInfo.setStatus(STATUS_DEFAULT);
		Double produceQty = 0.00;
		//根据父id查询中间表中的子id
		List<MaterialRelationInfo> list = materialRelationService.getListBy(materialRelationInfo);
		DecimalFormat df = new DecimalFormat("###.00");
		Double doubleProduceQty = 0.00;
		if (list.size()!=0) {
			for(MaterialRelationInfo mri : list){
				Float issueQty = productionIssueDetailService.getIssueQty(mri.getMaterialChildId(),productionNumber,LoginFilter.getCurrentUser().getCompanyId());
				Float returnedQty = produceMaterialReturnLogService.getReturnedQty(mri.getMaterialChildId(),productionNumber,LoginFilter.getCurrentUser().getCompanyId());
				if(issueQty == null){
					issueQty = 0f;
				}
				if(returnedQty == null){
					returnedQty = 0f;
				}
				mri.setIssueQty(issueQty-returnedQty);
				mri.setReturnedQty(returnedQty);
				mri.setStayIssueQty(materialNum*mri.getQuantity()-issueQty+returnedQty);
				String produceQtyStr = df.format(issueQty/mri.getQuantity());
				mri.setProduceQty(Float.parseFloat(produceQtyStr));
			}

			OptionalDouble maxProduceQty = list.stream().mapToDouble(MaterialRelationInfo::getProduceQty).min();
			produceQty = maxProduceQty.getAsDouble();

			if(produceQty != 0){
				String produceQtyStr = df.format(produceQty);
				doubleProduceQty = Double.valueOf(produceQtyStr);
			}
		}
		return ResultUtils.success(doubleProduceQty);
	}

	/**
	 * 删除中间表数据
	 * @param id
	 * @param childMaterialId
	 * @return
	 */
	@RequestMapping("/deleteMaterial")
	public Result<Void> deleteMaterial(Integer id,Integer childMaterialId){
		MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
		materialRelationInfo.setMaterialParentId(id);
		materialRelationInfo.setMaterialChildId(childMaterialId);
		materialRelationInfo.setStatus(1);
		List<MaterialRelationInfo> mriList = materialRelationService.getListBy(materialRelationInfo);
		if (mriList.size()!=0){
			Integer iId = mriList.get(0).getId();
			materialRelationService.deleteBy(iId);
		}
		return ResultUtils.success();
	}

	/**
	 * 获取拼接的参数值
	 * @param id
	 * @return
	 */
	public String getParamValue(Integer id){
		//根据子id去查部件-参数表
		MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
		materialBomParamNameInfo.setMaterialId(id);
		materialBomParamNameInfo.setStatus(1);
		List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
		String materialBomParamNameValueStr = "";
		if (mbpniList.size()!=0){
			for (int j = 0;j < mbpniList.size(); j++){
				if (j<mbpniList.size()-1) {
					materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
				}else {
					materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
				}
			}
		}
		if (materialBomParamNameValueStr != ""){
			return (materialBomParamNameValueStr);
		}else {
			return ("");
		}
	}

}
