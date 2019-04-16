package com.ecms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.MaterielEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

import tk.mybatis.mapper.entity.Example;

@Service
public class MaterielService extends BaseService<MaterielEntity, MaterielInfo> {

	@Resource
	private MaterielManager materielManager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterialParameterService paramService;
	@Resource
	private MaterielInventoryService invService;

	@Override
	protected BaseManager<MaterielEntity> getManager() {
		return materielManager;
	}
	
	//按物料id获取产品
	public MaterielInfo getByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		List<MaterielInfo> list = getByCondition();
		MaterielInfo info = null;
		if (list.size() > 0) {
			info = list.get(0);
			//绑定库存数量
			float onhand = 0.0f;
			MaterielInventoryInfo searchInfo = new MaterielInventoryInfo();
			searchInfo.setMaterielId(info.getId());
			List<MaterielInventoryInfo> listDetail = invService.getListBy(searchInfo);
			onhand = listDetail.stream().map(MaterielInventoryInfo::getQty).reduce(0.0f,Float::sum);
			info.setOnhand(onhand);
			info.setInventoryList(listDetail);
		}
		return info;
	}

	/**
	 * 重写，嵌入实体
	 */
	@Override
	public Pager<MaterielInfo> getModelList(int pageNum, int pageSize, MaterielInfo info) {
		MaterielEntity entity = ExchangeUtils.exchangeObject(info, eClass);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<MaterielEntity> list = materielManager.getListBy(pageNum, pageSize, entity);
		long total = materielManager.getTotal(entity);
		List<MaterielInfo> modalList = ExchangeUtils.exchangeList(list, MaterielInfo.class);
		modalList.stream().forEach(modal -> {
			//绑定MaterialBom
			MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(modal.getMaterialId()),
					MaterialBomModal.class);
			//bomModal.setMaterialParts(bomService.listMaterialPart(modal.getMaterialId()));
			List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(modal.getMaterialId());
			bomModal.setMaterialParameters(listParams);
			Map<String, String> params = new HashMap<String, String>();
			listParams.stream().forEach(param -> {
				params.put(param.getParameterName(), param.getParameterValue());
			});
			bomModal.setMaterialParameter(params);
			modal.setMaterialBom(bomModal);
			
			/*//绑定库存数量
			float onhand = 0.0f;
			MaterielInventoryInfo searchInfo = new MaterielInventoryInfo();
			searchInfo.setMaterielId(modal.getId());
			List<MaterielInventoryInfo> listDetail = invService.getListBy(searchInfo);
			onhand = listDetail.stream().map(MaterielInventoryInfo::getQty).reduce(0.0f,Float::sum);
			modal.setOnhand(onhand);*/
		});

		Pager<MaterielInfo> pager = new Pager<MaterielInfo>(pageNum, pageSize, total, modalList);
		return pager;
	}
	
	//获取实体
	public MaterielInfo info(Integer id) {
		MaterielInfo modal = getModel(id);
		MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(modal.getMaterialId()),
				MaterialBomModal.class);
		//bomModal.setMaterialParts(bomService.listMaterialPart(modal.getMaterialId()));
		List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(modal.getMaterialId());
		bomModal.setMaterialParameters(listParams);
		Map<String, String> params = new HashMap<String, String>();
		listParams.stream().forEach(param -> {
			params.put(param.getParameterName(), param.getParameterValue());
		});
		bomModal.setMaterialParameter(params);
		modal.setMaterialBom(bomModal);
		
		//绑定库存数量
		float onhand = 0.0f;
		MaterielInventoryInfo searchInfo = new MaterielInventoryInfo();
		searchInfo.setMaterielId(modal.getId());
		List<MaterielInventoryInfo> listDetail = invService.getListBy(searchInfo);
		onhand = listDetail.stream().map(MaterielInventoryInfo::getQty).reduce(0.0f,Float::sum);
		modal.setOnhand(onhand);
		return modal;
	}
	public List<Map<String,Object>> getMaterialGroupByRepertory(String materielCode){
		List<Map<String,Object>> materielList = materielManager.getMaterialGroupByRepertory(materielCode);
		return materielList;
	}
}
