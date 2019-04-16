package com.ecms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.PurchaseQueueInfo;
import com.ecms.dal.entity.PurchaseQueueEntity;
import com.ecms.exceptions.CommonBusinessException;
import com.ecms.manager.BaseManager;
import com.ecms.manager.PurchaseQueueManager;

import tk.mybatis.mapper.entity.Example;

@Service
public class PurchaseQueueService extends BaseService<PurchaseQueueEntity, PurchaseQueueInfo> {

	
	@Resource
	private PurchaseQueueManager manager;
	@Override
	protected BaseManager<PurchaseQueueEntity> getManager() {
		return manager;
	}
	
	//按物料id查询
	public List<PurchaseQueueInfo> listByMaterialId(Integer materialId){
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		return super.getByCondition();
	}
	
	
	public PurchaseQueueInfo getModalByMaterialId(Integer materialId) {
		Example.Criteria criteria = getDefaultCriteria();
		criteria.andEqualTo("materialId", materialId);
		List<PurchaseQueueInfo> listResult = getByCondition();
		if(listResult.size()==0) {
			throw new CommonBusinessException("记录不存在");
		}
		return listResult.get(0);
	}
}
