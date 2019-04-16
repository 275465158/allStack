/**
 * 
 */
package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.BomTaskDetailInfo;
import com.ecms.dal.entity.BomTaskDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.BomTaskDetailManager;

/**
 * @author alan.wu
 * 2018年8月16日
 * @Description
 */
@Service
public class BomTaskDetailService extends BaseService<BomTaskDetailEntity, BomTaskDetailInfo> {

	@Resource
	private BomTaskDetailManager manager;
	/* (non-Javadoc)
	 * @see com.ecms.service.BaseService#getManager()
	 */
	@Override
	protected BaseManager<BomTaskDetailEntity> getManager() {
		return manager;
	}

}
