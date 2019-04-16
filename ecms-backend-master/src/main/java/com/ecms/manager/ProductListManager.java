package com.ecms.manager;

import com.ecms.bean.ProductListInfo;
import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductListEntity;
import com.ecms.dal.mapper.ProductListMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductListManager extends BaseManager<ProductListEntity> {

	@Resource
	private ProductListMapper mapper;
	@Override
	protected BasicMapper<ProductListEntity> getMapper() {
		return mapper;
	}



}
