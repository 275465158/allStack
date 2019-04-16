package com.ecms.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.excel.MaterialTemplate;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.service.*;
import com.ecms.utils.ExcelImportUtil;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.ResultUtils;
import com.ecms.web.filter.LoginFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static com.ecms.common.BusinessConstants.*;

/**
 * @author zhengli
 * 2018/9/27
 */
@RestController
@RequestMapping("materialInfo")
public class MaterialController extends BaseController<MaterialBomEntity,MaterialBomInfo> {
	protected String workflowKey = WORKFLOW_MATERIAL_BOM;
	private String[] stepFlag = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	@Resource
	private MaterialBomService materialBomService;

	@Resource
	private MaterialRelationService materialRelationService;

	@Resource
	private MaterialProcessService materialProcessService;

	@Resource
	private MaterialBomParamNameService materialBomParamNameService;

	@Resource
	private ProcessService processService;

	@Resource
	private SupplierMaterialService service;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private MaterialRepertoryService materialRepertoryService;

	@Resource
	private ActivitiWorkflowService tasksService;

	@Resource
	private SupplierMaterialService supplierMaterialService;

	@Override
	protected BaseService<MaterialBomEntity,MaterialBomInfo> getBaseService() {
		return materialBomService;
	}

	/**
	 * 模板下载
	 * **/
	@Override
	protected List<MaterialTemplate> getTemplateForDownload() {
		List<MaterialTemplate> list = new ArrayList<>();
		MaterialTemplate materialTemplate = new MaterialTemplate();
		list.add(materialTemplate);
		return list;
	}
	/**
	 * 导出
	 * **/
	@Override
	protected List<MaterialTemplate> getListForDownload() {
		return materialBomService.getExportList();
	}

	@Override
	protected void uploadData(MultipartFile file) throws IOException {
		List<Object> list = ExcelImportUtil.importExcel(file.getInputStream(), MaterialTemplate.class);
		//System.out.println(file.getInputStream().read());
		materialBomService.upload(list);
	}

	/** 不要随便改接口
	 * @param pageNum
	 * @param pageSize
	 * @param materialBomInfo
	 * @return
	 * @author pratice
	 * @date 2018年11月1日
	 */
	@RequestMapping("/searchMaterialAndProductList")
	public Result<Pager<MaterialBomInfo>> searchMaterialAndProductList(int pageNum,int pageSize, MaterialBomInfo materialBomInfo,Integer supplierid) {
		ExchangeUtils.convertBlank2Null(materialBomInfo);
		Pager pager=materialBomService.getModelListByLike(pageNum,pageSize,new MaterialBomInfo(),false);
		if(pageNum == 1){
			pageNum = 0;
		}else{
			pageNum = pageNum*pageSize-pageSize;
		}
		List<MaterialBomInfo> list = materialBomService.getSupplierBomList(pageNum,pageSize,materialBomInfo.getMaterialCode(),materialBomInfo.getMaterialName(),supplierid,LoginFilter.getCurrentUser().getCompanyId(),materialBomInfo.getFactoryMaterialCode(),materialBomInfo.getType(),materialBomInfo.getMaterialBomParamValueStr(),materialBomInfo.getOriginalMaterial(),materialBomInfo.getDrawingCode(),materialBomInfo.getSource());
		List<MaterialBomInfo> list1 = materialBomService.getSupplierBomList1(materialBomInfo.getMaterialCode(),materialBomInfo.getMaterialName(),supplierid,LoginFilter.getCurrentUser().getCompanyId(),materialBomInfo.getFactoryMaterialCode(),materialBomInfo.getType(),materialBomInfo.getMaterialBomParamValueStr(),materialBomInfo.getOriginalMaterial(),materialBomInfo.getDrawingCode(),materialBomInfo.getSource());
		if (list.size()!=0){
			for (int i=0;i<list.size();i++){
				Integer materialId = list.get(i).getId();
				String paramStr = getParamValue(materialId);
				list.get(i).setMaterialBomParamValueStr(paramStr);
			}
		}
		pager.setList(list);
		int sizeTotal = list1.size();
		pager.setTotal(sizeTotal);
		if(pager.getPageNum() == 0){
			pager.setPageNum(1);
		}
		if(sizeTotal <= pageSize){
			pager.setPages(1);
		}else{
			int pages = list1.size()%pageSize==0?list1.size()/pageSize:list1.size()/pageSize+1;
			pager.setPages(pages);
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 一键添加供应商物料
	 * @param materialBomInfo 物料bom
	 * @param supplierid 供应商ID
	 * @return
	 */
	@RequestMapping("/autoAddMaterialList")
	public Result<Void> autoAddMaterialList(MaterialBomInfo materialBomInfo,Integer supplierid) {
		ExchangeUtils.convertBlank2Null(materialBomInfo);
		List<MaterialBomInfo> mbiList = materialBomService.getSupplierBomList1(materialBomInfo.getMaterialCode(),materialBomInfo.getMaterialName(),supplierid,LoginFilter.getCurrentUser().getCompanyId(),materialBomInfo.getFactoryMaterialCode(),materialBomInfo.getType(),materialBomInfo.getMaterialBomParamValueStr(),materialBomInfo.getOriginalMaterial(),materialBomInfo.getDrawingCode(),materialBomInfo.getSource());
		if(mbiList.size() != 0){
			for(MaterialBomInfo mbi : mbiList){
				SupplierMaterialInfo smi = new SupplierMaterialInfo();
				smi.setMaterialId(mbi.getId());
				smi.setMinQty(0f);
				smi.setPrice(0f);
				smi.setPurchaseDays("0");
				smi.setPurchaseMethod("0");
				smi.setSupplierId(supplierid);
				smi.setWeight(0.0);
				smi.setMaterialCode(mbi.getMaterialCode());
				smi.setMaterialName(mbi.getMaterialName());
				smi.setOriginalMaterial(mbi.getOriginalMaterial());
				smi.setMaterialBomParamValueStr(mbi.getMaterialBomParamValueStr());
				smi.setType(mbi.getType());
				smi.setFactoryMaterialCode(mbi.getFactoryMaterialCode());
				smi.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				supplierMaterialService.insertBy(smi);
			}
		}
		return ResultUtils.success();
	}

	/** 订单新增产品   不要随便改接口
	 * @param pageNum
	 * @param pageSize
	 * @param materialBomInfo
	 * @return
	 * @author pratice
	 * @date 2018年11月1日
	 */
	@RequestMapping("/searchAll")
	public Result<Pager<MaterialBomInfo>> searchAll(int pageNum,int pageSize, MaterialBomInfo materialBomInfo,Integer supplierid) {
//		materialBomInfo.setWeight(Double.valueOf(null));
		ExchangeUtils.convertBlank2Null(materialBomInfo);
		materialBomInfo.setStatus(STATUS_DEFAULT);
		materialBomInfo.setFlag(null);
//		materialBomInfo.setSource("外购");
		List<SupplierMaterialInfo> existlist = service.getBySupplierId(supplierid);

		Pager<MaterialBomInfo> pager = materialBomService.getModelListByLike(pageNum, pageSize, materialBomInfo, false);
		List<MaterialBomInfo> sublist=new ArrayList<>();
		if (pager.getList()!=null){
			for (int i=0;i<pager.getList().size();i++){
				pager.getList().get(i).setMaterialBomParamValueStr(getParamValue(pager.getList().get(i).getId()));
			}
		}else {
			throw new CommonBusinessException("没有数据，请添加");
		}

		if (existlist==null){
			return ResultUtils.success(pager);
		}else {
			for (int i = 0; i < existlist.size(); i++) {
				Integer materialId = existlist.get(i).getMaterialId();

				for (int j = 0; j < pager.getList().size(); j++) {

					if (materialId.equals(pager.getList().get(j).getId())) {
						sublist.add(materialBomService.getModel(materialId));
						//记录重复的物料
					}
				}
			}
			pager.getList().removeAll(sublist);
			return ResultUtils.success(pager);
		}


	}

	/**
	 * @param pageNum
	 * @param pageSize
	 * @param materialBomInfo
	 * @return
	 * @author pratice
	 * @date 2018年11月1日
	 */
	@RequestMapping("/searchMaterialAndProductList_Insert")
	public Result<Pager<MaterialBomInfo>> searchMaterialAndProductList_Insert(int pageNum, int pageSize,MaterialBomInfo materialBomInfo) {
		ExchangeUtils.convertBlank2Null(materialBomInfo);
		materialBomInfo.setStatus(1);
		materialBomInfo.setFlag(null);
		materialBomInfo.setSource("外购");
		Pager<MaterialBomInfo> pager = materialBomService.getModelListByLike(pageNum, pageSize, materialBomInfo, false);
		for (int j = 0; j < pager.getList().size(); j++) {
			pager.getList().get(j).setMaterialBomParamValueStr(getParamValue(pager.getList().get(j).getId()));
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 部件/产品列表查询
	 * @param pageNum
	 * @param materialBomInfo
	 * @return
	 */
	@RequestMapping("/searchMaterialList")
	public Result<Pager<MaterialBomInfo>> searchMaterialList(int pageNum,int pageSize, MaterialBomInfo materialBomInfo) {
		ExchangeUtils.convertBlank2Null(materialBomInfo);
		materialBomInfo.setStatus(STATUS_DEFAULT);
		String orderByStr = "id desc,auditing_status asc";
		Pager<MaterialBomInfo> pager = materialBomService.getModelListByLikeOrder(pageNum,pageSize, materialBomInfo,orderByStr,false);
		if (pager.getList().size()!=0){
			for (int i = 0;i < pager.getList().size();i++){
				MaterialRepertoryInfo mri = new MaterialRepertoryInfo();
				mri.setMaterialId(pager.getList().get(i).getId());
				mri.setStatus(STATUS_DEFAULT);
				mri.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
				List<MaterialRepertoryInfo> mriList = materialRepertoryService.getListBy(mri);
				if (mriList.size()!=0){
					pager.getList().get(i).setInventoryNum(mriList.get(0).getInventoryQty());
				}
			}
		}
		return ResultUtils.success(pager);
	}

	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public Result<MaterialBomInfo> detail(Integer id){
		MaterialBomInfo materialBomInfo = materialBomService.getModel(id);
		ProductTypeInfo pti = new ProductTypeInfo();
		pti.setNumber(materialBomInfo.getNumber());
		pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		List<ProductTypeInfo> list = productTypeService.getListBy(pti);
		if (list.size()!=0){
			materialBomInfo.setType(list.get(0).getType());
		}
		if (materialBomInfo.getProcessId() != null){
			Integer processId = materialBomInfo.getProcessId();
			ProcessInfo processInfo = processService.getModel(processId);
			if (processInfo != null){
				materialBomInfo.setProcessName(processInfo.getProcessName());
			}
		}
		return ResultUtils.success(materialBomInfo);
	}
	/**
	 * 物料分解数据组装
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/expandView")
	public Result<List<MaterialBomExpandInfo>> expandView(Integer id) {

		List<MaterialBomExpandInfo> expandList = new ArrayList<MaterialBomExpandInfo>();
		MaterialBomInfo bomInfo = materialBomService.getModel(id);
		ProductTypeInfo pti = new ProductTypeInfo();
		pti.setNumber(bomInfo.getNumber());
		pti.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		pti.setStatus(STATUS_DEFAULT);
		List<ProductTypeInfo> list = productTypeService.getListBy(pti);
		if (list.size() != 0) {
			bomInfo.setType(list.get(0).getType());
		}

		MaterialProcessInfo searchProcessInfo = new MaterialProcessInfo();
		searchProcessInfo.setMaterialId(id);
		searchProcessInfo.setStatus(1);
		List<MaterialProcessInfo> processInfos = materialProcessService.getListBy(searchProcessInfo);
		//processInfos.sort(Comparator.comparing(MaterialProcessInfo::getId));
		Collections.sort(processInfos, new OrderKeyComparator());
		if(processInfos.size()>0) {
			for (int i = 0; i <= processInfos.size()-1; i++) {
				MaterialBomExpandInfo bomExpandInfo = new MaterialBomExpandInfo();
				bomExpandInfo.setBomInfo(bomInfo);
				JSONObject jsonParam = JSONObject.parseObject(processInfos.get(i).getJsonParam());
				bomExpandInfo.setMaterialProcessNum(bomInfo.getMaterialCode() + "-" + jsonParam.getString("工艺序号"));
				bomExpandInfo.setQty(1f);
				bomExpandInfo.setStepId("1");
				bomExpandInfo.setProcessInfo(processInfos.get(i));
				expandList.add(bomExpandInfo);
			}
		}else{
			MaterialBomExpandInfo bomExpandInfo = new MaterialBomExpandInfo();
			bomExpandInfo.setBomInfo(bomInfo);
			bomExpandInfo.setMaterialProcessNum(bomInfo.getMaterialCode());
			bomExpandInfo.setQty(1f);
			bomExpandInfo.setStepId("1");
			expandList.add(bomExpandInfo);
		}
		getExpandRelation(expandList, bomInfo, "1", 1);
		return ResultUtils.success(expandList);
	}

	/***
	 * 获取相关联物料展开信息
	 * @param expandList
	 * @param parentBomInfo
	 * @param stepId
	 */
	private void getExpandRelation(List<MaterialBomExpandInfo> expandList, MaterialBomInfo parentBomInfo, String stepId, float relationQty){
		MaterialRelationInfo searchRelation = new MaterialRelationInfo();
		searchRelation.setMaterialParentId(parentBomInfo.getId());
		searchRelation.setStatus(1);
		List<MaterialRelationInfo> relationInfos = materialRelationService.getListBy(searchRelation);
		if(relationInfos.size()>0){
			for(int i=0; i<relationInfos.size(); i++){
				float needQty = 0l;
				MaterialBomInfo bomInfo = materialBomService.getModel(relationInfos.get(i).getMaterialChildId());
				Float maxSupplierPrice = 0f;
				Float maxRepertoryPrice = 0f;
				Float maxPrice = 0f;
				Integer companyId = LoginFilter.getCurrentUser().getCompanyId();
				if (bomInfo.getSource().equals("外购")){
					Integer materialId = bomInfo.getId();
					//获取供应商里面这个物料的最大价格
					maxSupplierPrice = materialBomService.getMaxPriceToSupplier(materialId,companyId);
					//获取仓库里面这个物料的最大价格
					maxRepertoryPrice = materialBomService.getMaxPriceToRepertory(materialId,companyId);
					if (maxSupplierPrice>=maxRepertoryPrice){
						maxPrice = maxSupplierPrice;
					}else {
						maxPrice = maxRepertoryPrice;
					}
				}
				bomInfo.setMaxPrice(maxPrice);
				MaterialProcessInfo searchProcessInfo = new MaterialProcessInfo();
				searchProcessInfo.setMaterialId(bomInfo.getId());
				searchProcessInfo.setStatus(1);
				List<MaterialProcessInfo> processInfos = materialProcessService.getListBy(searchProcessInfo);
				Collections.sort(processInfos, new OrderKeyComparator());
				if(processInfos.size()>0) {
					for (int j = 0; j <= processInfos.size()-1; j++) {
						MaterialBomExpandInfo bomExpandInfo = new MaterialBomExpandInfo();
						bomExpandInfo.setBomInfo(bomInfo);
						JSONObject jsonParam = JSONObject.parseObject(processInfos.get(j).getJsonParam());
						bomExpandInfo.setMaterialProcessNum(bomInfo.getMaterialCode() + "-" +jsonParam.getString("工艺序号"));
						if(relationQty>0) {
							needQty = relationQty * relationInfos.get(i).getQuantity();
						}else {
							needQty = relationInfos.get(i).getQuantity();
						}
						//needQty = (float)(Math.round(needQty*100))/100;
						bomExpandInfo.setQty(needQty);
						bomExpandInfo.setStepId(stepId+"-"+(i+1));
						bomExpandInfo.setProcessInfo(processInfos.get(j));
						expandList.add(bomExpandInfo);
					}
				}else{
					MaterialBomExpandInfo bomExpandInfo = new MaterialBomExpandInfo();
					bomExpandInfo.setBomInfo(bomInfo);
					bomExpandInfo.setMaterialProcessNum(bomInfo.getMaterialCode());
					if(relationQty>0) {
						needQty = relationQty * relationInfos.get(i).getQuantity();
					}else {
						needQty = relationInfos.get(i).getQuantity();
					}
					//needQty = (float)(Math.round(needQty*100))/100;
					bomExpandInfo.setQty(needQty);
					bomExpandInfo.setStepId(stepId+"-"+(i+1));
					expandList.add(bomExpandInfo);
				}
				getExpandRelation(expandList, bomInfo, stepId+"-"+(i+1), needQty);
			}
		}
	}
	/**
	 * 根据id查询采购单中物料的详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/purchaseOrdersMaterial")
	public Result<MaterialBomInfo> purchaseOrdersMaterial(Integer id){
		MaterialBomInfo materialBomInfo = materialBomService.getModel(id);
		materialBomInfo.setMaterialBomParamValueStr(getParamValue(id));
		if (materialBomInfo.getProcessId() != null){
			Integer processId = materialBomInfo.getProcessId();
			ProcessInfo processInfo = processService.getModel(processId);
			if (processInfo != null){
				materialBomInfo.setProcessName(processInfo.getProcessName());
			}
		}
		return ResultUtils.success(materialBomInfo);
	}

	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping("/searchList")
	public Result<List<MaterialBomInfo>> searchList(){
		MaterialBomInfo materialBomInfo = new MaterialBomInfo();
		materialBomInfo.setFlag(STATUS_INVALID);
		List<MaterialBomInfo> list = materialBomService.getListBy(materialBomInfo);
		if (list.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				//根据部件id获取部件-参数值表中的list
				MaterialBomParamNameInfo materialBomParamNameInfo = new MaterialBomParamNameInfo();
				materialBomParamNameInfo.setMaterialId(list.get(i).getId());
				materialBomParamNameInfo.setStatus(1);
				List<MaterialBomParamNameInfo> mbpniList = materialBomParamNameService.getListBy(materialBomParamNameInfo);
				//申明要拼接的字符串
				String materialBomParamNameValueStr = "";
				if (mbpniList.size() != 0){
					for (int j = 0;j < mbpniList.size();j++){
						if (j<mbpniList.size()-1){
							materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue() + ",";
						}else {
							materialBomParamNameValueStr += mbpniList.get(j).getMaterialParamNameValue();
						}
					}
					list.get(i).setMaterialBomParamValueStr(materialBomParamNameValueStr);
				}
			}
		}
		return ResultUtils.success(list);

	}

	/**
	 * 新增部件/产品
	 * @param materialBomInfo
	 * @return
	 */
	@RequestMapping("/saveMaterial")
	public Result<Void> saveMaterial(MaterialBomInfo materialBomInfo){

		return materialBomService.isRepeat(materialBomInfo);
	}

	/**
	 * 修改产品/部件
	 * @param materialBomInfo
	 * @return
	 */
	@RequestMapping("/updateMaterial")
	public Result<Void> updateMaterial(MaterialBomInfo materialBomInfo){
		materialBomService.updateMaterial(materialBomInfo);
		return ResultUtils.success();
	}

	/**
	 * 物料合并
	 * @param info
	 * @return
	 */
	@RequestMapping("materialMerge")
	public Result<Void> materialMerge(MaterialBomInfo info){
		materialBomService.materialMerge(info);
		return ResultUtils.success();
	}


	/**
	 * 启动流程
	 * @param info
	 * @return
	 */
	@RequestMapping("startTask")
	public Result<MaterialBomInfo> startTask(MaterialBomInfo info){
		ExchangeUtils.convertBlank2Null(info);
		info.setEditable(false);
		info.setAuditingStatus(1);
		materialBomService.updateByPrimaryKeySelective(info);
		info = materialBomService.getModel(info.getId());

		Map<String, Object> var = new HashMap<>();
		//手动插入流程参数
		var.put("title","物料评审");
		var.put("materialName", info.getMaterialName());
		var.put("materialCode", info.getMaterialCode());
		tasksService.startWorkflow(this.workflowKey,String.valueOf(info.getId()),var);
		return ResultUtils.success(info);
	}

	/**
	 * 任务执行-同意
	 * @param info
	 * @param taskId
	 * @return
	 */
	@RequestMapping("completeTask")
	public Result<MaterialBomInfo> completeTask(MaterialBomInfo info,String taskId, String taskComment){
		ExchangeUtils.convertBlank2Null(info);
		Map<String, Object> taskVars = new HashMap<>();
		if(StringUtils.isNotEmpty(taskComment)){
			taskVars.put("taskComment", taskComment);
		}
		tasksService.compileTask(taskId,taskVars);
		return ResultUtils.success(info);
	}

	/**
	 * 任务执行-不同意
	 * @param info
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("rejectTask")
	public Result<MaterialBomInfo> rejectTask(MaterialBomInfo info,String taskId, String taskComment) throws Exception {
		ExchangeUtils.convertBlank2Null(info);
		info.setEditable(true);
		info.setAuditingStatus(AUDITING_NO_PASS);
		materialBomService.updateByPrimaryKeySelective(info);
		Map<String, Object> taskVars = new HashMap<>();
		taskVars.put("rejectFlag",AUDITING_NO_PASS);
		if(StringUtils.isNotEmpty(taskComment)){
			taskVars.put("taskComment", taskComment);
		}
		tasksService.endProcess(taskId, taskVars);
		return ResultUtils.success(info);
	}

	/**
	 * 停用
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("blockUp")
	public Result<Void> blockUp(Integer id) throws Exception {
		MaterialBomInfo mbi = new MaterialBomInfo();
		mbi.setId(id);
		mbi.setAuditingStatus(BLOCK_UP);
		materialBomService.updateByPrimaryKeySelective(mbi);
		return ResultUtils.success();
	}

	/**
	 * 启用
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("startUsing")
	public Result<Void> startUsing(Integer id) throws Exception {
		MaterialBomInfo mbi = new MaterialBomInfo();
		mbi.setId(id);
		mbi.setAuditingStatus(STAY_AUDITING);
		materialBomService.updateByPrimaryKeySelective(mbi);
		return ResultUtils.success();
	}

	/**
	 * 物料升级
	 * @param materialBomInfo
	 * @return
	 */
	@RequestMapping("/materialUpgraded")
	public Result<Void> materialUpgraded(MaterialBomInfo materialBomInfo){
		materialBomService.materialUpgraded(materialBomInfo);
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

	static class OrderKeyComparator implements Comparator {
		public int compare(Object object1, Object object2) {// 实现接口中的方法
			MaterialProcessInfo p1 = (MaterialProcessInfo) object1; // 强制转换
			MaterialProcessInfo p2 = (MaterialProcessInfo) object2;
			JSONObject param1 = JSONObject.parseObject(p1.getJsonParam());
			JSONObject param2 = JSONObject.parseObject(p2.getJsonParam());
			int rtn = 1;
			if(param1!=null&&param2!=null){
				if(param1.getString("工艺序号")!=null&&param2.getString("工艺序号")!=null){
					rtn = param2.getString("工艺序号").compareTo(param1.getString("工艺序号"));
				}
			}
			return rtn;
		}
	}

}

