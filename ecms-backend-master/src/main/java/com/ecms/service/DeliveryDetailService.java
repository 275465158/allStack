package com.ecms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.DeliveryDetailInfo;
import com.ecms.bean.DeliveryInfo;
import com.ecms.bean.InventoryDetail;
import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.DeliveryDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.DeliveryDetailManager;
import com.ecms.manager.MaterielInventoryManager;
import com.ecms.manager.MaterielManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

@Service
public class DeliveryDetailService extends BaseService<DeliveryDetailEntity, DeliveryDetailInfo> {

	@Resource
	private DeliveryDetailManager manager;
	@Resource
	private DeliveryService deliveryService;
	@Resource
	private MaterielInventoryManager inventoryManager;
	@Resource
	private MaterielManager materielManager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterialParameterService paramService;

	@Override
	protected BaseManager<DeliveryDetailEntity> getManager() {
		return manager;
	}

	/**
	 * @author alan.wu
	 * 重写列表页
	 */
	@Override
	public Pager<DeliveryDetailInfo> getModelList(int pageNum, int pageSize, DeliveryDetailInfo info){
		DeliveryDetailEntity entity = ExchangeUtils.exchangeObject(info, DeliveryDetailEntity.class);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<DeliveryDetailEntity> list = manager.getListBy(pageNum, pageSize, entity);
		List<DeliveryDetailInfo> modalList = ExchangeUtils.exchangeList(list, DeliveryDetailInfo.class);
		modalList.stream().forEach(modal ->{
			MaterielInventoryInfo invInfo = ExchangeUtils.exchangeObject(inventoryManager.getEntityById(modal.getMaterielId()), MaterielInventoryInfo.class);
			MaterielInfo materiel = ExchangeUtils.exchangeObject(materielManager.getEntityById(invInfo.getMaterielId()), MaterielInfo.class);
			MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()), MaterialBomModal.class);
			//嵌入相关物料属性
			List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(materiel.getMaterialId());
			bomModal.setMaterialParameters(listParams);
			//数据结构转换
			Map<String, String> params = new HashMap<String, String>();
			listParams.stream().forEach(param -> {
				params.put(param.getParameterName(), param.getParameterValue());
			});
			bomModal.setMaterialParameter(params);
			materiel.setMaterialBom(bomModal);
			invInfo.setMateriel(materiel);
			modal.setMaterielInventory(invInfo);
		});
		Pager<DeliveryDetailInfo> pager = new Pager<DeliveryDetailInfo>(pageNum, pageSize, modalList.size(), modalList);
		return pager;
	}
	
	/**
	 * 组装数据并按仓库id过滤
	 * 漏洞：如果原始记录不存在的话组装部会返回没有记录的意外错误
	 * @param repertoryId
	 * @return
	 */
	public List<InventoryDetail> getListByRepertoryId(Integer repertoryId){
		List<DeliveryDetailInfo> list = getValidList();
		List<InventoryDetail> resultList = new ArrayList<InventoryDetail>();
		list.stream().forEach(deliveryDetail->{
			//相关数据
			deliveryDetail.setDelivery(deliveryService.getModel(deliveryDetail.getDeliveryId()));
			
			//关键数据
			MaterielInventoryInfo materielInventory = ExchangeUtils.exchangeObject(inventoryManager.getEntityById(deliveryDetail.getMaterielId()), MaterielInventoryInfo.class);
			materielInventory.setMateriel(ExchangeUtils.exchangeObject(materielManager.getEntityById(materielInventory.getMaterielId()), MaterielInfo.class));
			deliveryDetail.setMaterielInventory(materielInventory);
		});
		list.stream().filter(deliveryDetail->{
			boolean result = false;
			if(deliveryDetail.getMaterielInventory().getMateriel().getRepertoryId()==repertoryId) {
				result = true;
			}
			return result;
		}).forEach(deliveryDetail->{
			InventoryDetail detail = new InventoryDetail();
			detail.setDetailClass(DeliveryDetailInfo.class);
			detail.setDetail(deliveryDetail);
			detail.setBriefClass(DeliveryInfo.class);
			detail.setBrief(deliveryDetail.getDelivery());
			detail.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(deliveryDetail.getMaterielInventory().getMateriel().getMaterialId()), MaterialBomModal.class));
			MaterielInventoryInfo inventoryDetail = deliveryDetail.getMaterielInventory();
			MaterielInfo materiel = inventoryDetail.getMateriel();
			materiel.setMaterialBom(ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()), MaterialBomModal.class));
			inventoryDetail.setMateriel(materiel);
			detail.setInventoryDetail(inventoryDetail);
			resultList.add(detail);
		});
		return resultList;
	}
}
