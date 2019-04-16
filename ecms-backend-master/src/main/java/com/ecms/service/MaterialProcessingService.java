package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterialProcessingInfo;
import com.ecms.dal.entity.MaterialProcessingEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterialProcessingManager;

import tk.mybatis.mapper.entity.Example;

@Service
public class MaterialProcessingService extends BaseService<MaterialProcessingEntity, MaterialProcessingInfo> {

	@Resource
	private MaterialProcessingManager manager;
	@Override
	protected BaseManager<MaterialProcessingEntity> getManager() {
		return manager;
	}
	
	/**
	 * @Description 按物料id获取制程列表
	 * @param materialId
	 * @return
	 */
	public List<MaterialProcessingInfo> getListByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		return getByCondition();
	}

}
