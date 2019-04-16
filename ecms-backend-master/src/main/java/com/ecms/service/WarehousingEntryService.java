package com.ecms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.bean.WarehousingEntryMaterialModal;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.WarehousingEntryEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.WarehousingEntryManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

@Service
public class WarehousingEntryService extends BaseService<WarehousingEntryEntity, WarehousingEntryMaterialModal> {

	@Resource
	private WarehousingEntryManager manager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterialParameterService paramService;
	@Resource
	private SupplierService supplierService;
	@Resource
	private PurchaseOrderService purchaseOrderService;
	@Resource
	private WarehousingEntryDetailService detailService;
	@Override
	protected BaseManager<WarehousingEntryEntity> getManager() {
		return manager;
	}

	//重写分页列表
	@Override
	public Pager<WarehousingEntryMaterialModal> getModelList(int pageNum, int pageSize, WarehousingEntryMaterialModal info){
		WarehousingEntryEntity entity = ExchangeUtils.exchangeObject(info, eClass);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<WarehousingEntryEntity> list = manager.getListBy(pageNum, pageSize, entity);
		long total = manager.getTotal(entity);
		List<WarehousingEntryMaterialModal> modalList = ExchangeUtils.exchangeList(list, WarehousingEntryMaterialModal.class);
		modalList.stream().forEach(modal ->{
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
			modal.setSupplier(supplierService.getModel(modal.getSupplierId()));
			//绑定汇总数据 arrivedTotal：到货总数，unqualifiedTotal：不合格总数，entryTotal：待入库总数
			float arrivedTotal = 0, unqualifiedTotal = 0, entryTotal = 0;
			List<WarehousingEntryDetailInfo> listDetail = detailService.listByEntryId(modal.getId());
			arrivedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getArrivedQty).reduce(0.0f, Float::sum);
			unqualifiedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getUnqualifiedQty).reduce(0.0f, Float::sum);
			entryTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getEntryQty).reduce(0.0f, Float::sum);
			modal.setArrivedTotalQty(arrivedTotal);
			modal.setUnqualifiedTotalQty(unqualifiedTotal);
			modal.setEntryTotalQty(entryTotal);
		});
		Pager<WarehousingEntryMaterialModal> pager = new Pager<WarehousingEntryMaterialModal>(pageNum, pageSize, total, modalList);
		return pager;
	}
	
	@Override
	public List<WarehousingEntryMaterialModal> getListBy(WarehousingEntryMaterialModal info){
		WarehousingEntryEntity entity = ExchangeUtils.exchangeObject(info, eClass);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<WarehousingEntryMaterialModal> modalList = ExchangeUtils.exchangeList(manager.getListBy(entity), WarehousingEntryMaterialModal.class);
		modalList.stream().forEach(modal ->{
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
			modal.setSupplier(supplierService.getModel(modal.getSupplierId()));
			//绑定汇总数据
			float arrivedTotal = 0, unqualifiedTotal = 0, entryTotal = 0;
			List<WarehousingEntryDetailInfo> listDetail = detailService.listByEntryId(modal.getId());
			arrivedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getArrivedQty).reduce(0.0f, Float::sum);
			unqualifiedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getUnqualifiedQty).reduce(0.0f, Float::sum);
			entryTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getEntryQty).reduce(0.0f, Float::sum);
			modal.setArrivedTotalQty(arrivedTotal);
			modal.setUnqualifiedTotalQty(unqualifiedTotal);
			modal.setEntryTotalQty(entryTotal);
		});
		return modalList;
	}

	public WarehousingEntryMaterialModal info(Integer id) {
		WarehousingEntryMaterialModal entryModal = getModel(id);
		entryModal.setSupplier(supplierService.getModel(entryModal.getSupplierId()));
		entryModal.setMaterialBom(bomService.getModel(entryModal.getMaterialId()));
		entryModal.setPurchaseOrderInfo(purchaseOrderService.getModel(entryModal.getPurchaseOrderId()));
		float arrivedTotal = 0, unqualifiedTotal = 0, entryTotal = 0;
		List<WarehousingEntryDetailInfo> listDetail = detailService.listByEntryId(id);
		arrivedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getArrivedQty).reduce(0.0f, Float::sum);
		unqualifiedTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getUnqualifiedQty).reduce(0.0f, Float::sum);
		entryTotal = listDetail.stream().map(WarehousingEntryDetailInfo::getEntryQty).reduce(0.0f, Float::sum);
		entryModal.setArrivedTotalQty(arrivedTotal);
		entryModal.setUnqualifiedTotalQty(unqualifiedTotal);
		entryModal.setEntryTotalQty(entryTotal);
		return entryModal;
	}
}
