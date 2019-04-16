package com.ecms.manager;

import javax.annotation.Resource;

import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Component;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.PickingEntity;
import com.ecms.dal.mapper.PickingMapper;

import java.util.List;

@Component
public class PickingManager extends BaseManager<PickingEntity> {

	@Resource
	private PickingMapper mapper;
	@Override
	protected BasicMapper<PickingEntity> getMapper() {
		return mapper;
	}

	public List<PickingEntity> getPickList(int pageNum, int pageSize, PickingEntity pickingEntity) {
		PageMethod.startPage(pageNum, pageSize);
		System.err.println(mapper.select(pickingEntity));
		return mapper.select(pickingEntity);
	}

}
