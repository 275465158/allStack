package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialParameterInfo;
import com.ecms.dal.entity.MaterialParameterEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialParameterManager;
import com.ecms.utils.ExchangeUtils;

import tk.mybatis.mapper.entity.Example;

@Service
public class MaterialParameterService extends BaseService<MaterialParameterEntity, MaterialParameterInfo> {

	@Resource
	private MaterialParameterManager manager;

	@Override
	protected BaseManager<MaterialParameterEntity> getManager() {
		return manager;
	}

	/**
	 * @Description 按照物料id获取属性列表
	 * @param materialId
	 * @return
	 */
	public List<MaterialParameterInfo> getListByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		return getByCondition();
	}
	
	/**
	 * @Description 获得全部可见的属性列表
	 * @return
	 */
	public List<MaterialParameterInfo> getListIsVisible(){
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("isVisible", 1);
		return getByCondition();
	}
	
	/**
	 * 按照公司id获取属性列表
	 * @param companyId
	 * @return
	 */
	public List<MaterialParameterInfo> getListParameterName(Integer companyId){
		return ExchangeUtils.exchangeList(manager.getListParameterName(companyId), MaterialParameterInfo.class);
	}

	/**
	 * 保存前置的清空
	 * @param materialId
	 */
	public void clearBy(Integer materialId) {
		List<MaterialParameterInfo> list = getListByMaterialId(materialId);
		for(int i=0; i<list.size(); i++) {
			this.deleteBy(list.get(i).getId());
		}
	}
}
