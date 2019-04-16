package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialProcessEntity;
import com.ecms.service.*;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;

import static com.ecms.common.BusinessConstants.*;

@RestController
@RequestMapping("/materialProcess")
public class MaterialProcessController extends BaseController<MaterialProcessEntity, MaterialProcessInfo> {

	@Resource
	private MaterialProcessService materialProcessService;

	@Resource
	private ProductionLogDetailService productionLogDetailService;

	@Resource
	private ProductionPlanService productionPlanService;

	@Resource
	private MaterialRelationService materialRelationService;

	@Resource
	private ProductionIssueDetailService productionIssueDetailService;

	@Resource
	private ProductionIssueService productionIssueService;

	@Resource
	private ProduceMaterialReturnLogService produceMaterialReturnLogService;

	@Resource
	private ProductionCheckLogService productionCheckLogService;

	@Override
	protected BaseService<MaterialProcessEntity, MaterialProcessInfo> getBaseService() {
		return materialProcessService;
	}

	@RequestMapping("/detail")
	public Result<List<MaterialProcessInfo>> detail(Integer id){
		MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
		materialProcessInfo.setMaterialId(id);
		List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);
		return ResultUtils.success(list);
	}

	@RequestMapping("/getProcessDetail")
	public Result<List<MaterialProcessInfo>> getProcessDetail(Integer id,String productionNumber,Integer planId){
 		MaterialProcessInfo materialProcessInfo = new MaterialProcessInfo();
		materialProcessInfo.setMaterialId(id);
		materialProcessInfo.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		materialProcessInfo.setStatus(STATUS_DEFAULT);
  		List<MaterialProcessInfo> list = materialProcessService.getListBy(materialProcessInfo);

		ProductionPlanInfo ppInfo = productionPlanService.getModel(planId);

		MaterialRelationInfo materialRelationInfo = new MaterialRelationInfo();
		materialRelationInfo.setMaterialParentId(id);
		materialRelationInfo.setStatus(STATUS_DEFAULT);
		Double produceQty = 0.00;
		//根据父id查询中间表中的子id
		List<MaterialRelationInfo> mriList = materialRelationService.getListBy(materialRelationInfo);
		DecimalFormat df = new DecimalFormat("###.00");
		Double doubleProduceQty = 0.00;
		if (mriList.size()!=0) {
			for(MaterialRelationInfo mri : mriList){
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
				mri.setIssueQty(issueQty-returnedQty);
				mri.setReturnedQty(returnedQty);
				mri.setStayIssueQty(ppInfo.getMaterialNum()*mri.getQuantity()-issueQty+returnedQty);
				String produceQtyStr = df.format((issueQty-returnedQty-usedQty)/mri.getQuantity());
				mri.setProduceQty(Float.parseFloat(produceQtyStr));
			}

			OptionalDouble maxProduceQty = mriList.stream().mapToDouble(MaterialRelationInfo::getProduceQty).min();
			produceQty = maxProduceQty.getAsDouble();

			if(produceQty > 0){
				String produceQtyStr = df.format(produceQty);
				doubleProduceQty = Double.valueOf(produceQtyStr);
			}
		}

		Float completedQty = 0f;
		Float processCompletedQty = 0f;
		for(int i=0;i<list.size();i++){
  			String objectStr = list.get(i).getJsonParam();
 			JSONObject jsonObject = JSONObject.fromObject(objectStr);
			processCompletedQty = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionNumber,list.get(i).getId(),null);
			if(processCompletedQty == null){
				processCompletedQty = 0f;
			}
			if(i==0){
				ProductionPlanInfo ppi = productionPlanService.getModel(planId);
				jsonObject.put("投料数",doubleProduceQty);
			}else {
				if(PROCESS_NEED_CHECK.equals(list.get(i-1).getCheckStatus())){
					completedQty = productionCheckLogService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionNumber,list.get(i-1).getId());
					if(completedQty == null){
						completedQty = 0f;
					}
					Float completedQty1 = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionNumber,list.get(i).getId(),null);
					if(completedQty1 == null){
						completedQty1 = 0f;
					}
					jsonObject.put("投料数",completedQty-completedQty1);
				}else{
					completedQty = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionNumber,list.get(i-1).getId(),null);
					if(completedQty == null){
						completedQty = 0f;
					}

					Float completedQty1 = productionLogDetailService.getCompleteQty(LoginFilter.getCurrentUser().getCompanyId(),productionNumber,list.get(i).getId(),null);
					if(completedQty1 == null){
						completedQty1 = 0f;
					}
					jsonObject.put("投料数",completedQty-completedQty1);
				}
 			}
			ProductionLogDetailInfo pldi = new ProductionLogDetailInfo();
			pldi.setProcessId(list.get(i).getId());
			pldi.setProductionNumber(productionNumber);
			pldi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
			pldi.setOperatId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
			//查询正在生产的记录
			pldi.setProductStatus(STAY_PRODUTION);
 			List<ProductionLogDetailInfo> pldiList = productionLogDetailService.getListBy(pldi);
			if(pldiList.size() != 0){
				list.get(i).setStartDate(pldiList.get(0).getStartDate());
				jsonObject.put("操作",COMPLETE_PRODUCE);
			}else{
				jsonObject.put("操作",START_PRODUCT);
			}
			jsonObject.put("生产完成数",processCompletedQty);
			jsonObject.put("完成检状态",list.get(i).getCheckStatus());
			jsonObject.put("id",list.get(i).getId());

			list.get(i).setJsonParam(jsonObject.toString());
		}
		return ResultUtils.success(list);
	}

	@RequestMapping("/deleteProcess")
	public Result<Void> deleteProcess(Integer id){
		materialProcessService.deleteBy(id);
		return ResultUtils.success();
	}
}
