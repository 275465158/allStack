package com.ecms.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.ServletException;

import com.ecms.bean.*;
import com.ecms.common.Pager;
import com.ecms.service.*;
import com.ecms.utils.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecms.common.Result;
import com.ecms.dal.entity.MaterialBomEntity;
import com.ecms.web.filter.LoginFilter;

@RestController
@RequestMapping("material")
public class MaterialBomController extends BaseController<MaterialBomEntity, MaterialBomInfo> {

	@Resource
	private MaterialBomService materialBomService;
	@Resource
	private MaterialParameterService paramService;
	@Resource
	private MaterialProcessingService processingService;
	@Resource
	private MaterialPartService partService;
	@Resource
	private MaterialBomParamNameService materialBomParamNameService;
	@Resource
	private MaterialBomParamSpecificationService materialBomParamSpecificationService;

	@Override
	protected BaseService<MaterialBomEntity, MaterialBomInfo> getBaseService() {
		return materialBomService;
	}

	@RequestMapping("info")
	public Result<MaterialBomModal> info(@RequestParam(value="materialId",required = false, defaultValue="0")int materialId){
		MaterialBomModal modal = new MaterialBomModal();
		if(materialId>0) {
			modal = materialBomService.getMaterialBomModal(materialId);
		}
		return ResultUtils.success(modal);
	}
	@RequestMapping("search")
	public Result<List<MaterialBomModal>> search(MaterialBomInfo info){
		List<MaterialBomModal> searchResult = new ArrayList<MaterialBomModal>();
		List<MaterialBomInfo> listInfo = materialBomService.getListBy(info);
		listInfo.stream().forEach(material->{
			MaterialBomModal modal = materialBomService.getMaterialBomModal(material.getId());
			searchResult.add(modal);

		});
		return ResultUtils.success(searchResult);
	}

	@RequestMapping("params")
	public Result<List<MaterialParameterInfo>> getAllParamList(){
		return ResultUtils.success(paramService.getListIsVisible());
	}
	
	@RequestMapping("paramNames")
	public Result<List<MaterialParameterInfo>> getListParameterName(){
		return ResultUtils.success(paramService.getListParameterName(LoginFilter.getCurrentUser().getCompanyId()));
	}
	
	@RequestMapping("save")
	public Result<Void> save(MaterialBomModal modal){
		MaterialBomModal mEntity = ExchangeUtils.exchangeObject(materialBomService.saveOrUpdate(modal), MaterialBomModal.class);
		paramService.clearBy(mEntity.getId());
		for(int i=0; i< modal.getMaterialParameters().size(); i++) {
			MaterialParameterInfo paramInfo = modal.getMaterialParameters().get(i);
			paramInfo.setMaterialId(mEntity.getId());
			paramService.saveOrUpdate(paramInfo);
		}
		modal.getMaterialProcessing().stream().forEach(processing->{
			processing.setMaterialId(mEntity.getId());
			processingService.saveOrUpdate(processing);
		});
		modal.getMaterialParts().stream().forEach(part->{
			part.setParentId(mEntity.getId());
			partService.saveOrUpdate(part);
		});
		return ResultUtils.success();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException{
		MaterialParameterEditor paramsEditor = new MaterialParameterEditor();
		binder.registerCustomEditor(Collection.class, "materialParameters", paramsEditor);
		binder.registerCustomEditor(Collection.class, "materialProcessing", new CollectionEditorSupport(MaterialProcessingInfo.class));
		binder.registerCustomEditor(Collection.class, "materialParts", new CollectionEditorSupport(MaterialPartInfo.class));
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "updated", new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping("getMaterialBomList")
	public Result<Pager<MaterialBomInfo>> listByLike(int pageNum, int pageSize, MaterialBomInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		Pager<MaterialBomInfo> pager = getBaseService().getModelListByLike(pageNum, pageSize, info, false);
		List<MaterialBomInfo> mbiList = pager.getList();
		if(mbiList.size() != 0){
			String paramValue;
			for(MaterialBomInfo mbi : mbiList){
				Integer id = mbi.getId();
				Map<String,Object> totalNumList = materialBomService.getTotalNumList(id);
				mbi.setOnhandTotal(Float.parseFloat(totalNumList.get("onhandTotal").toString()));
				mbi.setSafeStockTotal(Float.parseFloat(totalNumList.get("safeStockTotal").toString()));
				mbi.setRequiredByOrdersTotal(Float.parseFloat(totalNumList.get("requiredByOrdersTotal").toString()));
				paramValue = getParamValue(id);
				mbi.setParamValue(paramValue);
			}

		}
		return ResultUtils.success(pager);
	}

	@RequestMapping("getAddMaterialBomList")
	public Result<Pager<MaterialBomInfo>> getAddMaterialBomList(int pageNum, int pageSize, MaterialBomInfo info) {
		if(pageNum == 1){
			pageNum = 0;
		}else{
			pageNum = pageNum*pageSize-pageSize;
		}
		ExchangeUtils.convertBlank2Null(info);
		List<MaterialBomInfo> mbiList = materialBomService.getAddMaterialBomList(pageNum,pageSize,info.getMaterialCode(),info.getMaterialName(),info.getAuthor(),info.getRepertoryId(),LoginFilter.getCurrentUser().getCompanyId(),info.getFactoryMaterialCode(),info.getType(),info.getMaterialBomParamValueStr(),info.getDrawingCode(),info.getSource());
		List<MaterialBomInfo> mbiList1 = materialBomService.getAddMaterialBomList1(info.getMaterialCode(),info.getMaterialName(),info.getAuthor(),info.getRepertoryId(),LoginFilter.getCurrentUser().getCompanyId(),info.getFactoryMaterialCode(),info.getType(),info.getMaterialBomParamValueStr(),info.getDrawingCode(),info.getSource());
		info.setRepertoryId(null);
		Pager<MaterialBomInfo> pager = materialBomService.getModelListByLike(pageNum,pageSize,info,false);
		pager.setList(mbiList);
		pager.setPageNum(pageNum);
		int sizeTotal = mbiList1.size();
		pager.setTotal(sizeTotal);
		if(pager.getPageNum() == 0){
			pager.setPageNum(1);
		}
		if(sizeTotal <= pageSize){
			pager.setPages(1);
		}else{
			int pages = mbiList1.size()%pageSize==0?mbiList1.size()/pageSize:mbiList1.size()/pageSize+1;
			pager.setPages(pages);
		}
		return ResultUtils.success(pager);
	}

	@RequestMapping("addRepertoryMaterial")
	public Result<Void> addRepertoryMaterial(MaterialBomInfo info){
		materialBomService.addRepertoryMaterial(info);
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
		//根据子id去查部件-参数规格表
		MaterialBomParamSpecificationInfo materialBomParamSpecificationInfo = new MaterialBomParamSpecificationInfo();
		materialBomParamSpecificationInfo.setMaterialId(id);
		materialBomParamSpecificationInfo.setStatus(1);
		List<MaterialBomParamSpecificationInfo> mbpsiList =  materialBomParamSpecificationService.getListBy(materialBomParamSpecificationInfo);
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
		String materialBomParamSpecificationValueStr = "";
		if (mbpsiList.size()!=0){
			for (int k = 0;k < mbpsiList.size();k++){
				if (k<mbpsiList.size()-1){
					materialBomParamSpecificationValueStr += mbpsiList.get(k).getMaterialParamSpecificationValue() + ",";
				}else {
					materialBomParamSpecificationValueStr += mbpsiList.get(k).getMaterialParamSpecificationValue();
				}
			}
		}
		if (materialBomParamNameValueStr != "" && materialBomParamSpecificationValueStr != ""){
			return (materialBomParamNameValueStr+","+materialBomParamSpecificationValueStr);
		}else if (materialBomParamNameValueStr != "" && materialBomParamSpecificationValueStr == ""){
			return (materialBomParamNameValueStr);
		}else if (materialBomParamNameValueStr == "" && materialBomParamSpecificationValueStr != ""){
			return (materialBomParamSpecificationValueStr);
		}else {
			return (null);
		}
	}

	/**
	 * 直接通过审批
	 * @param materialId 物料列表
	 * @return
	 */
	@RequestMapping("auditingPass")
	public Result<Void> auditingPass(Integer materialId){
		materialBomService.auditingPass(materialId);
		return ResultUtils.success();
	}
}
