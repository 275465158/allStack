/**
 * 
 */
package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielProduceDetailEntity;
import com.ecms.dal.mapper.MaterielProduceDetailMapper;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
@Component
public class MaterielProduceDetailManager extends BaseManager<MaterielProduceDetailEntity> {

	@Resource
	private MaterielProduceDetailMapper mapper;
	/* (non-Javadoc)
	 * @see com.ecms.manager.BaseManager#getMapper()
	 */
	@Override
	protected BasicMapper<MaterielProduceDetailEntity> getMapper() {
		return mapper;
	}

}
