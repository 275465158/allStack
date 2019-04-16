/**
 * 
 */
package com.ecms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecms.bean.BomTaskInfo;
import com.ecms.dal.entity.BomTaskEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.BomTaskManager;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description
 */
@Service
public class BomTaskService extends BaseService<BomTaskEntity, BomTaskInfo> {

	@Resource
	private BomTaskManager manager;
	/* (non-Javadoc)
	 * @see com.ecms.service.BaseService#getManager()
	 */
	@Override
	protected BaseManager<BomTaskEntity> getManager() {
		return manager;
	}

}
