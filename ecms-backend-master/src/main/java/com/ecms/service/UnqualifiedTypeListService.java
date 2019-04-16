package com.ecms.service;

import com.ecms.bean.UnqualifiedTypeListInfo;
import com.ecms.dal.entity.UnqualifiedTypeListEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.UnqualifiedTypeListManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UnqualifiedTypeListService extends BaseService<UnqualifiedTypeListEntity, UnqualifiedTypeListInfo> {

	@Resource
	private UnqualifiedTypeListManager unqualifiedTypeListManager;
	@Override
	protected BaseManager<UnqualifiedTypeListEntity> getManager() {
		return unqualifiedTypeListManager;
	}

}
