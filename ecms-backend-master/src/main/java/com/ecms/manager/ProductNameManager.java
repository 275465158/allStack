package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductNameEntity;
import com.ecms.dal.mapper.ProductNameMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductNameManager extends BaseManager<ProductNameEntity> {
	@Resource
	private ProductNameMapper productNameMapper ;
	@Override
	protected BasicMapper<ProductNameEntity> getMapper() {
		// TODO Auto-generated method stub
		return productNameMapper;
	}
}
