/**
 * 
 */
package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.BomTaskDetailEntity;
import com.ecms.dal.mapper.BomTaskDetailMapper;

/**
 * @author alan.wu
 * 2018年8月16日
 * @Description
 */
@Component
public class BomTaskDetailManager extends BaseManager<BomTaskDetailEntity> {

	@Resource
	private BomTaskDetailMapper mapper;
	/* (non-Javadoc)
	 * @see com.ecms.manager.BaseManager#getMapper()
	 */
	@Override
	protected BasicMapper<BomTaskDetailEntity> getMapper() {
		return mapper;
	}

}
