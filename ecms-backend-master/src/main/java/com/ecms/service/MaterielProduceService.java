/**
 * 
 */
package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.MaterielProduceInfo;
import com.ecms.dal.entity.MaterielProduceEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.MaterielProduceManager;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
@Service
public class MaterielProduceService extends BaseService<MaterielProduceEntity, MaterielProduceInfo> {

	@Resource
	private MaterielProduceManager manager;
	/* (non-Javadoc)
	 * @see com.ecms.service.BaseService#getManager()
	 */
	@Override
	protected BaseManager<MaterielProduceEntity> getManager() {
		return manager;
	}

}
