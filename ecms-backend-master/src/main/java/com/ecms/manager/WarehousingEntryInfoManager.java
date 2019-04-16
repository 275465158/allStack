package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.WarehousingEntryInfoEntity;
import com.ecms.dal.mapper.WarehousingEntryInfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WarehousingEntryInfoManager extends BaseManager<WarehousingEntryInfoEntity> {

	@Resource
	private WarehousingEntryInfoMapper mapper;
	@Override
	protected BasicMapper<WarehousingEntryInfoEntity> getMapper() {
		return mapper;
	}

}
