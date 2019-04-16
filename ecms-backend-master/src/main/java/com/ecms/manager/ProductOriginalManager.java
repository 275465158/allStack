package com.ecms.manager;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.ProductOriginalEntity;
import com.ecms.dal.mapper.ProductOriginalMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProductOriginalManager extends BaseManager<ProductOriginalEntity> {
	@Resource
	private ProductOriginalMapper productOriginalMapper ;
	@Override
	protected BasicMapper<ProductOriginalEntity> getMapper() {
		// TODO Auto-generated method stub
		return productOriginalMapper;
	}
}
