package com.ecms.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.InventoryDetail;
import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.bean.WarehousingEntryInfo;
import com.ecms.dal.entity.WarehousingEntryDetailEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.WarehousingEntryDetailManager;
import com.ecms.manager.WarehousingEntryManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class WarehousingEntryDetailService
		extends BaseService<WarehousingEntryDetailEntity, WarehousingEntryDetailInfo> {

	@Resource
	private WarehousingEntryDetailManager manager;
	@Resource
	private WarehousingEntryManager entryManager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterielInventoryService inventoryService;
	@Resource
	private MaterielService materielService;
	@Override
	protected BaseManager<WarehousingEntryDetailEntity> getManager() {
		return manager;
	}

	public List<WarehousingEntryDetailInfo> listByEntryId(Integer entryId){
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("entryId",entryId);
		return getByCondition();
	}
	
	public WarehousingEntryDetailInfo getModel(Integer id) {
		WarehousingEntryDetailEntity entity = manager.getEntityById(id);
		if (null == entity) {
			throw new CommonBusinessException("记录不存在");
		}
		WarehousingEntryDetailInfo info = ExchangeUtils.exchangeObject(entity, WarehousingEntryDetailInfo.class);
		info.setEntryInfo(ExchangeUtils.exchangeObject(entryManager.getEntityById(info.getEntryId()), WarehousingEntryInfo.class));
		return info;
	}
	
	public List<InventoryDetail> getListByRepertoryId(Integer repertoryId){
		List<WarehousingEntryDetailInfo> list = getValidList();
		List<InventoryDetail> resultList = new ArrayList<InventoryDetail>();
		list.stream().forEach(entryDetail->{
			//相关数据
			entryDetail.setEntryInfo(ExchangeUtils.exchangeObject(entryManager.getEntityById(entryDetail.getEntryId()), WarehousingEntryInfo.class));
			
			//关键数据
			
		});
		list.stream().filter(entryDetail->{
			boolean result = false;
			if(entryDetail.getEntryInfo().getRepertoryId()==repertoryId) {
				result = true;
			}
			return result;
		}).forEach(entryDetail->{
			InventoryDetail detail = new InventoryDetail();
			detail.setDetailClass(WarehousingEntryDetailInfo.class);
			detail.setDetail(entryDetail);
			detail.setBriefClass(WarehousingEntryInfo.class);
			detail.setBrief(entryDetail.getEntryInfo());
			detail.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(entryDetail.getEntryInfo().getMaterialId()), MaterialBomModal.class));
			MaterielInventoryInfo inventoryDetail = inventoryService.getModelByEntryId(entryDetail.getId());
			MaterielInfo materiel = materielService.getModel(inventoryDetail.getMaterielId());
			materiel.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()), MaterialBomModal.class));
			inventoryDetail.setMateriel(materiel);
			detail.setInventoryDetail(inventoryDetail);
			resultList.add(detail);
		});
		return resultList;
	}
}
