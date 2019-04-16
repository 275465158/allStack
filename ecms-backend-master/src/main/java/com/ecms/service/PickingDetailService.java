package com.ecms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.InventoryDetail;
import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.PickingDetailInfo;
import com.ecms.bean.PickingInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.PickingDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielInventoryManager;
import com.ecms.manager.MaterielManager;
import com.ecms.manager.PickingDetailManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

@Service
public class PickingDetailService extends BaseService<PickingDetailEntity, PickingDetailInfo> {

	@Resource
	private PickingDetailManager manager;
	@Resource
	private PickingService pickingService;
	@Resource
	private MaterielInventoryManager inventoryManager;
	@Resource
	private MaterielManager materielManager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterialParameterService paramService;
	@Override
	protected BaseManager<PickingDetailEntity> getManager() {
		return manager;
	}

	//重写分页，嵌入相关实体
	@Override
	public Pager<PickingDetailInfo> getModelList(int pageNum, int pageSize, PickingDetailInfo info){
		PickingDetailEntity entity = ExchangeUtils.exchangeObject(info, PickingDetailEntity.class);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<PickingDetailEntity> list = manager.getListBy(pageNum, pageSize, entity);
		List<PickingDetailInfo> modalList = ExchangeUtils.exchangeList(list, PickingDetailInfo.class);
		modalList.stream().forEach(modal ->{
			MaterielInventoryInfo invInfo = ExchangeUtils.exchangeObject(inventoryManager.getEntityById(modal.getMaterielId()), MaterielInventoryInfo.class);
			MaterielInfo materiel = ExchangeUtils.exchangeObject(materielManager.getEntityById(invInfo.getMaterielId()), MaterielInfo.class);
			MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()), MaterialBomModal.class);
			//bomModal.setMaterialParts(bomService.listMaterialPart(materiel.getMaterialId()));
			List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(materiel.getMaterialId());
			bomModal.setMaterialParameters(listParams);
			Map<String, String> params = new HashMap<String, String>();
			listParams.stream().forEach(param -> {
				params.put(param.getParameterName(), param.getParameterValue());
			});
			bomModal.setMaterialParameter(params);
			materiel.setMaterialBom(bomModal);
			invInfo.setMateriel(materiel);
			modal.setMaterielInventory(invInfo);
		});
		Pager<PickingDetailInfo> pager = new Pager<PickingDetailInfo>(pageNum, pageSize, modalList.size(), modalList);
		return pager;
	}
	
	public List<InventoryDetail> getListByRepertoryId(Integer repertoryId){
		List<PickingDetailInfo> list = getValidList();
		List<InventoryDetail> resultList = new ArrayList<InventoryDetail>();
		list.stream().forEach(pickingDetail->{
			//相关数据
			pickingDetail.setPickingList(pickingService.getModel(pickingDetail.getPickingId()));
			
			//关键数据
			MaterielInventoryInfo materielInventory = ExchangeUtils.exchangeObject(inventoryManager.getEntityById(pickingDetail.getMaterielId()), MaterielInventoryInfo.class);
			materielInventory.setMateriel(ExchangeUtils.exchangeObject(materielManager.getEntityById(materielInventory.getMaterielId()), MaterielInfo.class));
			pickingDetail.setMaterielInventory(materielInventory);
		});
		list.stream().filter(pickingDetail->{
			boolean result = false;
			if(pickingDetail.getMaterielInventory().getMateriel().getRepertoryId()==repertoryId) {
				result = true;
			}
			return result;
		}).forEach(pickingDetail->{
			InventoryDetail detail = new InventoryDetail();
			detail.setDetailClass(PickingDetailInfo.class);
			detail.setDetail(pickingDetail);
			detail.setBriefClass(PickingInfo.class);
			detail.setBrief(pickingDetail.getPickingList());
			detail.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(pickingDetail.getMaterielInventory().getMateriel().getMaterialId()), MaterialBomModal.class));
			MaterielInventoryInfo inventoryDetail = pickingDetail.getMaterielInventory();
			MaterielInfo materiel = inventoryDetail.getMateriel();
			materiel.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()), MaterialBomModal.class));
			inventoryDetail.setMateriel(materiel);
			detail.setInventoryDetail(inventoryDetail);
			resultList.add(detail);
		});
		return resultList;
	}
}
