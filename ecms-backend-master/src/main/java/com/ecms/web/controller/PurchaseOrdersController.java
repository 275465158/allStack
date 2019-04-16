package com.ecms.web.controller;

import com.ecms.bean.*;
import com.ecms.common.BusinessConstants;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.dal.entity.PurchaseOrdersEntity;
import com.ecms.dal.entity.SupplierEntity;
import com.ecms.service.*;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("purchaseOrders")
public class PurchaseOrdersController extends BaseController<PurchaseOrdersEntity, PurchaseOrdersInfo> {
	protected String workflowKey = BusinessConstants.WORKFLOW_PURCHASE_ORDER;
	@Resource
	private PurchaseOrdersService purchaseOrdersService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private MaterialBomParamSpecificationService materialBomParamSpecificationService;

	@Resource
	private SupplierService supplierService;

	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private PurchaseOrdersListService purchaseOrdersListService;

	@Resource
	private RepertoryService repertoryService;

	@Resource
	private DeliveryAddressService deliveryAddressService;

	@Resource
	private ActivitiWorkflowService tasksService;

	@Resource
	private PurchaseOrdersStayDetailService purchaseOrdersStayDetailService;

	@Override
	protected BaseService<PurchaseOrdersEntity, PurchaseOrdersInfo> getBaseService() {
		return purchaseOrdersService;
	}

	/**
	 * 保存采购单
	 * @param purchaseOrdersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/savePurchaseOrders")
	public Result<Void> savePurchaseOrders(PurchaseOrdersInfo purchaseOrdersInfo) throws ParseException{
		Integer purchaseNumber = purchaseOrdersService.getNextNumber();
		purchaseOrdersService.saveStayOrders(purchaseOrdersInfo,purchaseNumber);
		return ResultUtils.success();
	}

	/****
	 * 保存手工采购单
	 * @param purchaseOrdersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/saveHandworkOrders")
	public Result<Void> saveHandworkOrders(PurchaseOrdersInfo purchaseOrdersInfo) throws ParseException{
		Integer purchaseNumber = purchaseOrdersService.getNextNumber();
		purchaseOrdersService.saveHandworkOrders(purchaseOrdersInfo,purchaseNumber);
		return ResultUtils.success();
	}

	/****
	 * 查询采购单
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseOrdersInfo
	 * @return
	 */
	@RequestMapping("/purchaseOrdersPager")
	public Result<Pager<PurchaseOrdersInfo>> purchaseOrdersPager(int pageNum,int pageSize,PurchaseOrdersInfo purchaseOrdersInfo){
		ExchangeUtils.convertBlank2Null(purchaseOrdersInfo);
		if (purchaseOrdersInfo.getSupplierName()!=null){
			SupplierInfo si = new SupplierInfo();
			si.setSupplierName(purchaseOrdersInfo.getSupplierName());
			List<SupplierInfo> list = supplierService.getListBy(si);
			if (list.size()!=0){
				purchaseOrdersInfo.setSupplierId(list.get(0).getId());
			}
			purchaseOrdersInfo.setSupplierName(null);
		}
		Pager<PurchaseOrdersInfo> pager = purchaseOrdersService.getModelListByLike(pageNum,pageSize, purchaseOrdersInfo, false);
		if (pager.getList()!=null){
			for (int i=0;i<pager.getList().size();i++){
				if (pager.getList().get(i).getSupplierId()!=null){
					Integer supplierId = pager.getList().get(i).getSupplierId();
					SupplierInfo supplierInfo = supplierService.getModel(supplierId);
					pager.getList().get(i).setSupplierName(supplierInfo.getSupplierName());
				}
				PurchaseOrdersListInfo poli = new PurchaseOrdersListInfo();
				poli.setPurchaseOrdersId(pager.getList().get(i).getId());
				poli.setVerificatStatus(0);
				List<PurchaseOrdersListInfo> poliList = purchaseOrdersListService.getListBy(poli);
				if (poliList.size()!=0){
					pager.getList().get(i).setVerificatStatus(0);
				}else {
					pager.getList().get(i).setVerificatStatus(1);
				}

			}
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 获取待入库待检测列表（根据前端传过来的inboundStatus判断，1为待检测，2为待入库）
	 * @param pageNum
	 * @param pageSize
	 * @param purchaseOrdersInfo
	 * @return
	 */
	@RequestMapping("/getStayInboundPage")
	public Result<Pager<PurchaseOrdersInfo>> getStayInboundPage(int pageNum,int pageSize,PurchaseOrdersInfo purchaseOrdersInfo){
		ExchangeUtils.convertBlank2Null(purchaseOrdersInfo);
		Pager<PurchaseOrdersInfo> pager = purchaseOrdersService.getModelListByLike(pageNum,pageSize, purchaseOrdersInfo, false);
		List<PurchaseOrdersInfo> purchaseOrdersInfoList = pager.getList();
		if(purchaseOrdersInfoList.size() != 0 ){
			for(PurchaseOrdersInfo poi : purchaseOrdersInfoList){
				Integer materialId = poi.getMaterialId();
				String materialParamValue = getParamValue(materialId);
				MaterialBomEntity mbe = materialBomService.getEntity(materialId);
				poi.setOriginalMaterial(mbe.getOriginalMaterial());
				poi.setParamValue(materialParamValue);
				Integer supplierId = poi.getSupplierId();
				SupplierEntity se = supplierService.getEntity(supplierId);
				if (se.getId()!=null){
					poi.setSupplierName(se.getSupplierName());
				}
				poi.setEmployeeId(Integer.parseInt(LoginFilter.getCurrentUser().getEmployeId()));
			}
		}
		return ResultUtils.success(pager);
	}

	/****
	 * 根据采购单id查询详情，明细的详情在明细controller里面单独取
	 * @param id
	 * @return
	 */
	@RequestMapping("/searchById")
	public Result<PurchaseOrdersInfo> searchById(Integer id){
		PurchaseOrdersInfo purchaseOrdersInfo = purchaseOrdersService.getModel(id);
		Integer supplierId = purchaseOrdersInfo.getSupplierId();
		SupplierInfo si = supplierService.getModel(supplierId);
		purchaseOrdersInfo.setSupplierName(si.getSupplierName());
		Integer repertoryId = purchaseOrdersInfo.getRepertoryId();
		RepertoryInfo ri = repertoryService.getModel(repertoryId);
		purchaseOrdersInfo.setRepertoryName(ri.getRepertoryName());
		Integer addressId = purchaseOrdersInfo.getDeliveryAddressId();
		if (addressId!=null){
			purchaseOrdersInfo.setDeliveryAddressInfo(deliveryAddressService.getModel(addressId));
		}
		return ResultUtils.success(purchaseOrdersInfo);
	}


	@RequestMapping("startTask")
	public Result<PurchaseOrdersInfo> startTask(PurchaseOrdersInfo info){
		ExchangeUtils.convertBlank2Null(info);
		String bussinessKey = String.valueOf(info.getId());
		PurchaseOrdersInfo poi = purchaseOrdersService.getModel(info.getId());
		//启动流程时更新审批状态
		poi.setReviewStatus(BusinessConstants.IN_AUDITING);
		purchaseOrdersService.updateByPrimaryKeySelective(poi);
		//载入流程数据
		Integer supplierId = poi.getSupplierId();
		SupplierInfo si = supplierService.getModel(supplierId);
		poi.setSupplierName(si.getSupplierName());
		Map<String, Object> var = new HashMap<>();

		//手动插入流程参数，以后改成动态
		var.put("title","采购流程");
		var.put("purchaseCode", poi.getPurchaseCode());
		var.put("supplierName", poi.getSupplierName());
		tasksService.startWorkflow(this.workflowKey, bussinessKey,var);
		return ResultUtils.success(info);
	}
	/**
	 * 完成任务 - 审批同意
	 * @param info
	 * @param taskId
	 * @param taskComment
	 * @return
	 */
	@RequestMapping("completeTask")
	public Result<PurchaseOrdersInfo> completeTask(PurchaseOrdersInfo info , String taskId, String taskComment){
		ExchangeUtils.convertBlank2Null(info);
		Map<String, Object> vars = new HashMap<>();
		PurchaseOrdersInfo poi = purchaseOrdersService.getModel(info.getId());
		//同意任务状态为审批中
		poi.setReviewStatus(BusinessConstants.IN_AUDITING);
		purchaseOrdersService.updateByPrimaryKeySelective(poi);
		if(StringUtils.isNotEmpty(taskComment)) {
			vars.put("taskComment", taskComment);
		}
		vars.put("taskFlag","taskCompleted");
		tasksService.compileTask(taskId, vars);
		return ResultUtils.success(info);
	}

	/**
	 * 完成任务 - 审批不同意
	 * @param info
	 * @param taskId
	 * @param taskComment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rejectTask")
	public Result<PurchaseOrdersInfo> rejectTask(PurchaseOrdersInfo info ,String taskId, String taskComment) throws Exception {
		ExchangeUtils.convertBlank2Null(info);
		Map<String, Object> vars = new HashMap<>();
		if(StringUtils.isNotEmpty(taskComment)) {
			vars.put("taskComment", taskComment);
		}
		vars.put("taskFlag","taskRejected");
		//终止任务
		tasksService.endProcess(taskId,vars);
		//将采购记录返回给待采购
		purchaseOrdersStayDetailService.turnback(info.getId());

		return ResultUtils.success(info);
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
