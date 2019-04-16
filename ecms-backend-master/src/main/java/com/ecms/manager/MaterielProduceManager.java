/**
 * 
 */
package com.ecms.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.MaterielProduceEntity;
import com.ecms.dal.mapper.MaterielProduceMapper;

/**
 * @author alan.wu
 * 2018年8月26日
 * @Description
 */
@Component
public class MaterielProduceManager extends BaseManager<MaterielProduceEntity> {

	@Resource
	private MaterielProduceMapper mapper;
	/* (non-Javadoc)
	 * @see com.ecms.manager.BaseManager#getMapper()
	 */
	@Override
	protected BasicMapper<MaterielProduceEntity> getMapper() {
		return mapper;
	}

}
