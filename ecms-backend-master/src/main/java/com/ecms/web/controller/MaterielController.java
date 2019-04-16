package com.ecms.web.controller;

import com.ecms.bean.MaterialBomInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.RepertoryInfo;
import com.ecms.common.Pager;
import com.ecms.common.Result;
import com.ecms.dal.entity.MaterielEntity;
import com.ecms.service.BaseService;
import com.ecms.service.DeliveryDetailService;
import com.ecms.service.MaterielService;
import com.ecms.service.PickingDetailService;
import com.ecms.service.RepertoryService;
import com.ecms.service.WarehousingEntryDetailService;
import com.ecms.utils.ExchangeUtils;
import com.ecms.utils.InfoEditorSupport;
import com.ecms.utils.ResultUtils;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("materiel")
public class MaterielController extends BaseController<MaterielEntity, MaterielInfo> {

	@Resource
	private MaterielService materielService;

	@Resource
	private RepertoryService repertoryService;

	@Resource
	private DeliveryDetailService deliveryDetailService;

	@Resource
	private PickingDetailService pickingDetailService;

	@Resource
	private WarehousingEntryDetailService entryDetailService;

	@Override
	protected BaseService<MaterielEntity, MaterielInfo> getBaseService() {
		return materielService;
	}

	@RequestMapping("info")
	public Result<MaterielInfo> info(Integer id) {
		return ResultUtils.success(materielService.info(id));
	}

	/**
	 * 组装明细列表
	 * @return
	 */
	@RequestMapping("detailList")
	public Result<Pager> detailList(Integer repertoryId) {
		List list = new ArrayList();
		//添加发货明细
		deliveryDetailService.getListByRepertoryId(repertoryId).stream().forEach(deliveryDetail->{
			list.add(deliveryDetail);
		});
		//添加出料明细
		pickingDetailService.getListByRepertoryId(repertoryId).stream().forEach(pickingDetail->{
			list.add(pickingDetail);
		});
		//添加入库明细
		entryDetailService.getListByRepertoryId(repertoryId).stream().forEach(entryDetail->{
			list.add(entryDetail);
		});
		Pager pager = new Pager(1, DEFAULT_PAGE_SIZE, list.size(), list);
		return ResultUtils.success(pager);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {
		InfoEditorSupport infoEditor = new InfoEditorSupport(MaterialBomInfo.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		SimpleDateFormat shortdateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "created", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "updated", new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(Date.class, "materielStockFrom", new CustomDateEditor(shortdateFormat, true));
		binder.registerCustomEditor(MaterialBomInfo.class, "materialBom", infoEditor);
	}

	@RequestMapping("list")
	public Result<Pager<MaterielInfo>> list(int pageNum, MaterielInfo info) {
		ExchangeUtils.convertBlank2Null(info);
		Pager<MaterielInfo> pager = materielService.getModelList(pageNum, DEFAULT_PAGE_SIZE, info);
		return ResultUtils.success(pager);
	}

	@RequestMapping("getMaterialGroupByRepertory")
	public Result<List<Map<String,Object>>> getMaterialGroupByRepertory(MaterielInfo materielInfo){
		List<Map<String,Object>> materielList = materielService.getMaterialGroupByRepertory(materielInfo.getMaterielCode());
		if(materielList.size() != 0){
			Integer repertoryId;
			for(int i=0;i<materielList.size();i++){
				if(materielList.get(i).get("repertoryId") != null && !"".equals(materielList.get(i).get("repertoryId"))){
					repertoryId = Integer.parseInt(materielList.get(i).get("repertoryId").toString());
					RepertoryInfo repertoryInfo = repertoryService.getModel(repertoryId);
					Map<String,Object> res = new HashMap<>();
					materielList.get(i).put("repertoryName",repertoryInfo.getRepertoryName());
				}
			}
		}
		return ResultUtils.success(materielList);
	}
}
