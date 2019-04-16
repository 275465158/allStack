/**
 * 
 */
package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.BomTaskEntity;
import com.ecms.dal.mapper.BomTaskMapper;

/**
 * @author alan.wu
 * 2018年8月13日
 * @Description
 */
@Component
public class BomTaskManager extends BaseManager<BomTaskEntity> {

	@Resource
	private BomTaskMapper mapper;
	/* (non-Javadoc)
	 * @see com.ecms.manager.BaseManager#getMapper()
	 */
	@Override
	protected BasicMapper<BomTaskEntity> getMapper() {
		return mapper;
	}

}
