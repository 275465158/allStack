package com.ecms.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ecms.bean.SupplierMaterialInfo;
import com.ecms.dal.entity.SupplierMaterialEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.SupplierMaterialManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class SupplierMaterialService extends BaseService<SupplierMaterialEntity, SupplierMaterialInfo> {

	@Resource
	private SupplierMaterialManager manager;
	@Resource
	private MaterialBomService bomService;
	@Resource
	private SupplierService supplierService;

	@Override
	protected BaseManager<SupplierMaterialEntity> getManager() {
		return manager;
	}
	
	public SupplierMaterialInfo getModel(Integer id) {
		SupplierMaterialEntity entity = manager.getEntityById(id);
		if (null == entity) {
			throw new CommonBusinessException("记录不存在");
		}
		SupplierMaterialInfo info = ExchangeUtils.exchangeObject(entity, SupplierMaterialInfo.class);
		info.setMaterialBom(bomService.getMaterialBomModal(info.getMaterialId()));
		return info;
	}
	/**
	 * @param supplierId
	 * @author pratice
	 * @date 2018年10月31日
	 */
	public void deleteBySupplierId(Integer supplierId) {
		manager.deleteBySupplierId(supplierId);
	}
	
	/**
	 * @param supplierId
	 * @return
	 * @author pratice
	 * @date 2018年10月29日
	 */
	public List<SupplierMaterialInfo> getBySupplierId(Integer supplierId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("supplierId", supplierId);
		List<SupplierMaterialInfo> list = getByCondition();
		list.stream().forEach(materialInfo -> {
//			materialInfo.setMaterialBom(bomService.getMaterialBomModal(materialInfo.getMaterialId()));
			materialInfo.setSupplier(supplierService.getModel(materialInfo.getSupplierId()));
		});
		return list;
	}

	public List<SupplierMaterialInfo> getByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		List<SupplierMaterialInfo> list = getByCondition();
		list.stream().forEach(materialInfo -> {
			materialInfo.setMaterialBom(bomService.getMaterialBomModal(materialInfo.getMaterialId()));
			materialInfo.setSupplier(supplierService.getModel(materialInfo.getSupplierId()));
		});
		return list;
	}
	
	public SupplierMaterialInfo getBySupplierMaterial(Integer materialId, Integer supplierId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		criteria.andEqualTo("supplierId", supplierId);
		List<SupplierMaterialInfo> list = getByCondition();
		return list.get(0);
	}

}
