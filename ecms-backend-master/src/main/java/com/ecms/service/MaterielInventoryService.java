package com.ecms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialBomModal;
import com.ecms.bean.MaterialParameterInfo;
import com.ecms.bean.MaterielInfo;
import com.ecms.bean.MaterielInventoryInfo;
import com.ecms.bean.SupplierInfo;
import com.ecms.bean.WarehousingEntryDetailInfo;
import com.ecms.common.Pager;
import com.ecms.dal.entity.BaseCompanyAndStatusEntity;
import com.ecms.dal.entity.MaterielInventoryEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialBomManager;
import com.ecms.manager.MaterielInventoryManager;
import com.ecms.manager.MaterielManager;
import com.ecms.manager.SupplierManager;
import com.ecms.manager.WarehousingEntryDetailManager;
import com.ecms.utils.ExchangeUtils;
import com.ecms.web.filter.LoginFilter;

import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class MaterielInventoryService extends BaseService<MaterielInventoryEntity, MaterielInventoryInfo> {

	@Resource
	private MaterielInventoryManager manager;
	@Resource
	private MaterielManager materielManager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private MaterialParameterService paramService;
	@Resource
	private SupplierManager supplierManager;
	@Resource
	private WarehousingEntryDetailManager entryDetailManager;
	@Override
	protected BaseManager<MaterielInventoryEntity> getManager() {
		return manager;
	}

	/**
	 * 重写分页列表
	 */
	public Pager<MaterielInventoryInfo> getModelList(int pageNum, int pageSize, MaterielInventoryInfo info){
		MaterielInventoryEntity entity = ExchangeUtils.exchangeObject(info, MaterielInventoryEntity.class);
		if (entity instanceof BaseCompanyAndStatusEntity) {
			entity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
		}
		List<MaterielInventoryEntity> list = manager.getListBy(pageNum, pageSize, entity);
		long total = manager.getTotal(entity);
		List<MaterielInventoryInfo> modalList = ExchangeUtils.exchangeList(list, MaterielInventoryInfo.class);
		modalList.stream().forEach(modal->{
			MaterielInfo materiel = ExchangeUtils.exchangeObject(materielManager.getEntityById(modal.getMaterielId()), MaterielInfo.class);
			MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()),
					MaterialBomModal.class);
			//嵌入属性
			List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(materiel.getMaterialId());
			bomModal.setMaterialParameters(listParams);
			Map<String, String> params = new HashMap<String, String>();
			listParams.stream().forEach(param -> {
				params.put(param.getParameterName(), param.getParameterValue());
			});
			bomModal.setMaterialParameter(params);
			materiel.setMaterialBom(bomModal);
			modal.setMateriel(materiel);
			//嵌入供应商
			SupplierInfo supplier = ExchangeUtils.exchangeObject(supplierManager.getEntityById(modal.getSupplierId()), SupplierInfo.class);
			modal.setSupplier(supplier);
			//嵌入入库明细
			WarehousingEntryDetailInfo entryInfo = ExchangeUtils.exchangeObject(entryDetailManager.getEntityById(modal.getEntryId()), WarehousingEntryDetailInfo.class);
			modal.setEntryDetail(entryInfo);
		});
		Pager<MaterielInventoryInfo> pager = new Pager<MaterielInventoryInfo>(pageNum, pageSize, total, modalList);
		return pager;
	}
	
	/**
	 * 搜索库存
	 * @param pageNum
	 * @param pageSize
	 * @param materialName
	 * @param materialCode
	 * @return
	 */
	public Pager<MaterielInventoryInfo> getModelList(int pageNum, int pageSize, String materialName, String materialCode){
		MaterielInventoryEntity entity = new MaterielInventoryEntity();
		List<MaterielInventoryEntity> list = manager.getListBy(pageNum, pageSize, entity);
		List<MaterielInventoryInfo> modalList = ExchangeUtils.exchangeList(list, MaterielInventoryInfo.class);
		List<MaterielInventoryInfo> resultList = new ArrayList<MaterielInventoryInfo>();
		modalList.stream().forEach(modal->{
			MaterielInfo materiel = ExchangeUtils.exchangeObject(materielManager.getEntityById(modal.getMaterielId()), MaterielInfo.class);
			MaterialBomModal bomModal = ExchangeUtils.exchangeObject(bomService.getModel(materiel.getMaterialId()),
					MaterialBomModal.class);
			//嵌入属性
			List<MaterialParameterInfo> listParams = paramService.getListByMaterialId(materiel.getMaterialId());
			bomModal.setMaterialParameters(listParams);
			Map<String, String> params = new HashMap<String, String>();
			listParams.stream().forEach(param -> {
				params.put(param.getParameterName(), param.getParameterValue());
			});
			bomModal.setMaterialParameter(params);
			materiel.setMaterialBom(bomModal);
			modal.setMateriel(materiel);
			//嵌入供应商
			SupplierInfo supplier = ExchangeUtils.exchangeObject(supplierManager.getEntityById(modal.getSupplierId()), SupplierInfo.class);
			modal.setSupplier(supplier);
			//嵌入入库单明细
			WarehousingEntryDetailInfo entryInfo = ExchangeUtils.exchangeObject(entryDetailManager.getEntityById(modal.getEntryId()), WarehousingEntryDetailInfo.class);
			modal.setEntryDetail(entryInfo);
			
		});
		//过滤结果
		modalList.stream().filter(modal->{
			boolean nameResult = false,codeResult = false,qtyResult = false;
			if(null!=materialName&&!("".equals(materialName))) {
				nameResult = modal.getMateriel().getMaterialBom().getMaterialName().equals(materialName);
			}
			if(null!=materialCode&&!("".equals(materialCode))) {
				codeResult = modal.getMateriel().getMaterialBom().getMaterialCode().equals(materialCode);
			}
			qtyResult = modal.getQty()>0;
			return (nameResult||codeResult)&&qtyResult;
		}).forEach(modal->{
			resultList.add(modal);
		});
		Pager<MaterielInventoryInfo> pager = new Pager<MaterielInventoryInfo>(pageNum, pageSize, resultList.size(), resultList);
		return pager;
	}
	
	public MaterielInventoryInfo getModelByEntryId(Integer entryId) {
		Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("entryId", entryId);
		List<MaterielInventoryInfo> resultInfo = getByCondition();
		if(resultInfo.size()==0) {
			throw new CommonBusinessException("记录不存在");
		}
		return resultInfo.get(0);
	}
}
